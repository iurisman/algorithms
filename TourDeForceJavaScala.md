# Tour de Force of Modern Java for Scala Programmers

#### Covers Java 17
By Igor Urisman, January 14, 2023.

* Tradeoff desision by language designers in favor of backward compatibility...

## 1 General Comments
### 1.2 Classes and Inheritance
Like Scala, Java implements the single inheritance model, whereby a class can inherit from at most one class and,
optionally, implement any number of interfaces:
```java
class Foo extends Bar implements Baz, Qux { ... }
```

Interfaces are partial equivalents of Scala traits with the following limitations:
* Java interfaces are not stackable: they are always selfless and their order in a type declaration is not significant.
* They may only contain abstract methods. Fields defined in the interface must be concrete.
* Concrete methods in interfaces are known as default implementations and must be annotatged with the `default`
qualifier. 

If more than one interface in a class declaration contains the same method (abstract or concrete), 
the implementing class must either be declared abstract or override it:

```java
interface Baz {
  String method();
}
interface Bar {
  default String method() { return "Bar"; }
}
class Foo implements Bar, Baz { }  // Compilation error
class Foo implements Bar, Baz {
  @Override String method() { return "Foo"; }  // Ok
}
```
If a field defined in a subclass has the same name as one defined in its superclass, the latter is not overridden,
hidden as a matter of syntactic scope, and cannot be annotated with `@Override`. There are no lazy fields in Java; 
all fields must be initializable at class creation time either with a static expression or by a constructor. 

Both fields (which are never abstract) and concrete methods can be declared `final`, though with different
implications:
```java
class Foo {
  final int height = 186;  // Cannot be mutated
  final void printHeight() { System.out.println("My height: " + height); } // Cannot be overridden
}
```

### 1.2 Type Parameters (Generics)
In Java, types which take parameters are called generic types, or, simply, generics. 
```java
interface Comparator<T> { ... } // Compares two values of some type T
```

Java does not support higher-kinded types; only types that can have values can be type parameters.
```java
interface Functor<F<?>> {} // Syntax error
```

A type parameter can have an upper bound:
```java
class Foo<T extends Bar> { ... } // `T` must be a subtype of `Bar`. 
```
There is no support for lower-bounded type parameters.

Java has no explicit support for variance. However, the following two facilities exist to help the programmer
work around this limitation indirectly.
* There is special syntax for declaring synthetic generic types that are co- or contra-variant replicas of some named 
generic type:
```java
Foo<? extends Number> covarFoo = new Foo<Integer>(0);   // A covariant version of Foo<T>.
Foo<? extends Number> covarFoo = new Foo<Object>(0);    // Compilation error: covariance vialation
Foo<? super Number> contravarFoo = new Foo<Integer>(0); // Compilation error: contravariance violation
Foo<? super Number> contravarFoo = new Foo<Object>(0);  // A contravariant version of Foo<T>.
```
It is particularly common to see this syntax in method signatures. For example, here's the signature of the
`java.util.Stream.filter()` method:
```java
interface Predicate<P> {
  boolean test(P p);  
}
interface Stream<T> {
  Stream<T> filter(Predicate<? super T> predicate); 
}
```
What this ungainly looking signature is saying is that a predicate over , for instance, `Number`s can be used to filter
a stream of `Integer`s, because `Number` is a superclass of `Integer`. Which is to say that the `filter()` method
expects a contravariant version of the type `Predicate`. But, clearly, there can be no users of `Predicate` that would 
need it to be covariant. Variance is inherent in the type itself and is independent of the type's users. In Scala, 
with its direct support for variance, (and if it needed the type `Predicate`) it would have  been defined as simply 
`trait Predicate[-P] {...}`.

* Arrays are implicitly covariant. `Foo[]` is automatically a subclass of `Bar[]` if (and only if) `Foo` is a subclass 
of `Bar`. Variance violations are checked at run time whenever an element of an array is updated:
```java
   Number[] integers = new Integer[2]; // Ok due to implicit covariance
   integers[0] = 0.5;  // Compiles, but throws ArrayStoreException at run time.
```

### 1.3 Static Members
Java does not have objects in Scala's sense of the word¹. Rather, static members are declared alongside
with instance members inside the class body and are annotated with the keyword `static`.
While in Scala, an object can extend a trait or even a class, static methods in Java are not subject to inheritance.
When the name of a static member clashes with that of a static member in a superclass, the latter is hidden as a matter
of syntactic context. A static member cannot be abstract or annotated with `@Override`. Interfaces can have static
values, which are available to static members of implementing classes.

¹ In Java, _object_ refers to the same concept as _instance_ in Scala: an instantiation of a concrete class.

### 1.4 Type Inference
Modern Java has limited type inference. In particular, in most local variable declarations their type can be inferred:
```java
var films = new LinkedList<String>();
```
is equivalent to
```java
LinkedList<String> films = new LinkedList<String>();
```

The keyword `var` has the same meaning as in Scala. There is no `val` keyword in Java, but the qualifier `final`
can be used to declare an immutable value. As well, just like in Scala, the type parameter list (but, unlike Scala,
not the angle brackets) on the right of the assignment may be dropped, if the value's type is explicitly
declared on the left:
```java
List<String> list = new LinkedList<>();
```

Types of concrete class members, both fields and methods, cannot be inferred.

Types of lambda parameters are inferred and are commonly omitted.
```java
List.of("The Stranger", "Citizen Kane", "Touch of Evil")
    .forEach(name -> System.out.println("Film Title: " + name));  // Type of `name` is inferred.
```

## 2 Functions
### 2.1 Functional Interfaces as Function Types
Java does not support functions as first-class values: there's no function type that can be instantiated, assigned,
or passed to a method or another function as a parameter. Nevertheless, it has made significant strides toward
enabling function-like syntax, which is known as _lambda expressions_. Such expressions provide concise syntactic
alternative for making certain class literals seem function literals. In the above example, 
`name -> System.out.println("Film Title: " + name)` has all the syntactic trappings of a function literal. It can be 
even assigned:
```java
Consumer<List> func = name -> System.out.println("Film Title: " + name);  // Type of `func` must be declared explicitly
```
now the previous expression can be rewritten as
```java
List.of("The Stranger", "Citizen Kane", "Touch of Evil").forEach(func);
```
This works because the method `forEach()` has the suitable signature:
```java
interface Iterable<T> {
  void forEach(Consumer<? super T> action); // Java's way of expressing the fact that type `Consumer` is contravariant 
}
```
The type `Consumer` is what Java refers to as _functional interface_:
```java
@FunctionalInterface                                         // 
public interface Consumer<T> {
  void accept(T t);                                          
  default Consumer<T> andThen(Consumer<? super T> after) {
    Objects.requireNonNull(after);
    return (T t) -> { accept(t); after.accept(t); };
  }
}
```
**Listing 2.1.1**

The actual value of `func` is, therefore, an instance of `Consumer` instantiated to this class literal:
```java
    Consumer<String> f = new Consumer<>() {
      @Override
      public void accept(String name) {
        System.out.println("Film Title: " + name);
      }
    };
```
That's why lambda expressions are commonly referred to as "syntactic sugar" for class literals. The best way to think
of them is as an alternative syntax for class literals of a certain type. Java compiler will accept a lambda expression 
in place of the traditional class literal, provided that it is structurally compatible with the target type, which must 
be explicitly declared. 
* The target type must be an interface with exactly one abstract method. Such interfaces are referred to as 
_functional_. The method's name is not significant.
* Parameter list in the lambda expression must have the same arity as that of the abstract method in the target
interface.
* The return type of the abstract method must be a supertype of that returned by the lambda expression.

The `Consumer` interface in Listing 2.1.1 is an example of a functional interface. Note that a functional interface
need not be annotated with `@FunctionalInterface`. However, it is a good idea to annotate custom functional interfaces
in order to signal the intent, and to prevent accidental updates, introducing new abstract members. Such an update will
cause a compilation error on the interface, and not just on the corresponding lambda expressions, which may be located
in different compilation units.

## 2.? Streams and Higher Order Transformations
Java's collection classes do not 

It is possible to use a syntax resembling function literal in place of a method parameter:

```java
var igorsBirthdate = birthdaysMap.compute("Igor", key -> Date())
```

Unlike Scala, higher order transformations like `map` or `filter` are not available directly on the concrete
collection types. Rather, they are provided by the `java.util.stream.Stream` interface, a concrete instance of
which is obtained by calling the `stream()` method inherited from the `Collection` interface and available
on all concrete colletion types. For example, to transform a list:
```java
var films = List.of("Citizen Kane", "Touch of Evil");
var filtered = films.stream().filter(f -> f.contains("Kane"));
System.out.println(filtered);  // java.util.stream.ReferencePipeline$2@372f7a8d
```
As a consequence of the fact that `filter()` was called on the stream instance and not on the original
collection, as a Scala programmer would have expected, the return type of the transformation is also a stream.
This allows chaining transformations in the style of functional programming. The downside though, is that
oing back to the original collection type an extra call to `collect()` is needed, which terminates
the stream by collecting all its elements in some fully instantiated data structure. For example, to go back to
the original immutable list:

```java
var films = List.of("Citizen Kane", "Touch of Evil").stream()
  .filter(f -> f.contains("Kane"))
  .map(String::toUpperCase)
  .collect(Collectors.toUnmodifiableList());
System.out.println(films); // [CITIZEN KANE]
System.out.println(films.getClass().getName()); // java.util.ImmutableCollections$List12
```
Note, that the call to `collect(Collectors.toUnmodifiableList())` can be replaced with  the shortcut `toList()`,
the idiomatic way to convert a collection to an immutable list in Scala. What may come as a surprise, however,
is that the `java.util.ImmutableCollection` class is not public and that very little can be deduced about the 
two immutable list types defined therein, `List12` and `ListN`. `List12` is optimized for short lists consisting
of no more than 2 elements. Longer lists will be converted to `ListN`.

## 3 Case Classes


## ? Java Standard Library
### ?.1 Collections
Java's collections library is nowhere as consistent as that of Scala. Although both mutable and immutable collections 
are now supported, the class hierarchy hasn't fundamentally changed since the early releases that only supported 
mutable collections. This has lead to a number of anomalies, counterintuitive to a Scala programmer. 
They are highlighted in the following sections. 

* Both mutable and immutable implementations of some collection type implement the same superinterface. For example,
the mutable list `java.util.LinkedList` implements the `java.util.List` interface and so does the immutable list
returned by the `java.util.List.of()` method. 

#### ?.1.1 Mutable Collections
Instantiation of mutable collections is accomplished via constructors. It is the responsibility 
of the programmer to pick the concrete collection class, and thus a particular implementation. The nullary 
constructor creates an empty modifiable collection of the requested type: 

```java
var films = new LinkedList<String>();
films.add("Citizen Kane");
System.out.println(films);  // [Citizen Kane]
```

There's typically a non-nullary constructor, taking another collection (mutable or immutable) of a comparable type, 
which will be deep-copied  into the new mutable collection:

```java
var filmsByOrsonWelles = new LinkedList<String>(films);
System.out.println(filmsByOrsonWelles); // [Citizen Kane]
```

It is also possible to emulate constructon of mutable collections with Java's static initializers:

```java
var films = new LinkedList<String>() {{
  add("Citizen Kane");
  add("Touch of Evil");
}};
System.out.println(films); // [Citizen Kane, Touch of Evil]
```

#### ?.1.2 Immutable Collections
Immutable collections were introduced in Java 9. They are instantiated with the static factory methods `of()` 
and `copyOf()`, available on superinterfaces. The method `of()` takes 0 or more individual list elements,
while `copyOf()` takes a compatible collection.

```java
var films = List.of("Citizen Kane", "Touch of Evil");
films = List.copyOf(films);
System.out.println(films); // [Citizen Kane, Touch of Evil]
```

Note, that the JDK does not provide a distinct type hierarchy for immutable collections: the type returned
by the factory methods `of()` and `copyOf()` is also the supertype of its mutable counterpart. Thus, in the above example,
the variable `films` has the type `java.util.List` — also the supertype of a mutable list instantiated 
by `new java.util.LinkedList()`. Consequently, calling a mutator method on an immutable collection does not
cause a compilation error and is only caught at runtime:

```java
films.add("Touch of Evil") // Compiles
```
This may be a source of confounding bugs, because the `UnsupportedOperationException` exception thrown in
this case, may not be thrown by a different execution history.

```java
```

#### ?.1.4 Lists

Mutable lists are instantiated with constructors on concrete implementation classes, e.g
```java
var filmsByOrsonWelles = new LinkedList<String>(films);
```
Immutable lists are created by static factory methods `List.of()`, `List.copyOf()`.

There are no conversion methods to other collection type, e.g. Scala's `List.toSet()`. The only exception is the two
`toArray()` methods:
```java
Object[] toArray()
```
returns an array of this list's elements, preserving the order. Due to type erasure, the return type of ... 

```java
Object[] toArray()
<T> T[]toArray(T[] a)
```

#### ?.1.3 Maps
Since the introduction of lambdas (See section ...) in Java 8, some new "higher order" methods are available on maps:

```java
```

Since Java 9, map types also support factory methods for generating immutable co
```java
    Map<String, LocalDate> birthdayMap = Map.of("Alfred Aho", LocalDate.of(1941, Month.August, 9));
```
will instantiate an immutable ("unmodifiable" in Java speak) implementation of the interface `java.util.Map`.