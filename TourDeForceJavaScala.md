# Tour de Force of Modern Java for Scala Programmers

#### Covers Java 17
By Igor Urisman, January 14, 2023.

## 0 Introduction
Many older Scala programmers, came to Scala after clocking years of coding in Java. This experience helped us be 
better Scala programmers —— by reusing Java standard library or understanding JVM internals. But we are a dying breed.
For younger Scala programmers, Java is not likely to be one of the languages they used before Scala. As colleges are 
moving away from Java as the primary teaching language, and as companies are slowing their investment in 
new lines of Java code, Java is entering its golden years. 

Nonetheless, if measured in the lines of live code, Java will remain the most ubiquitous language for many years to 
come, and new generations of programmers will have to learn it. Scala programmers are in a favorable position to learn 
Java quickly: they already know a lot about the JVM and are likely to have used Java's standard library. This article is
intended to help competent Scala programmers to grasp Java's key concepts easily, by introducing them by analogy with
the familiar Scala idioms.

Java is a single-paradigm object-oriented language. Even so, Java's type system deviates from a pure object model in a 
number of ways, as a consequence of certain performance trade-ofs that were reasonable for the mid-1990s. Since then,
the language has had a number of updates, like parametric types and lambda expressions, but Java's general commitment
to backward compatibility increasingly comes at the cost of constrained opportunity for keeping abreast with modern 
language design ideas, such as functional programming or advanced types.

## 1 Type System
### 1.1 Primitive Types
Java's value types exist in two parallel guises. The primitive types, like `int` or `double` are the low-level higher 
performing value types, and their object wrappers, like `Integer` and `Double`. The primitives exist beside Java's
general type hierarchy: they have no supertypes, they cannot serve as type parameters, and they can only be operated
on by operators because they have no methods. Conversely, the wrapper types have convenient supertypes, can serve as 
type parameters and come with many useful methods, like `decode()` for parsing the numeric value from a string.

The compiler offers autoboxing facility, seamlessly converting between primitives and object wrappers:
```java
var i = Integer.valueOf(4);  // Type Integer
Integer j = 5;               // Autoboxed to type Integer
println(i + j);              // Auto-unboxed to int
```
In Java, `+` is not a method on a numeric value type, but an operator applicable to numeric primitive types only.
Therefore, for the last like to work, the compiler has to auto-unbox the two instances of `Integer` to their primitive
counterparts.

### 1.2 `void`
Java has a special keyword `void` used in place of the return type in method signatures which return no value. 
In that, it is somewhat similar to Scala's `Unit`; for example, here's Java's `main` method's signature :
```java
static public void main(String[] args) { ... }
```
But that's where the analogy ends: `void` is not a proper type, in that it cannot be used to declare a variable or
as a type parameter. The problem is partially alleviated by the pseudotype `Void`, which behaves more like a type 
in that it can be used as a type parameter. It is even possible to define a method with the return type `Void`,
but there it behaves more like Scala's `Nothing` in that `Void` is uninhabited and the only way to return a `Void`
value from a method is to throw an exception.

### 1.3 Type Parameters (Generics)
In Java, parametric types are called generic types, or, simply, generics.
```java
interface Function<T,R> { ... } // A function taking one param of type T and returning type R
```

Java does not support higher-kinded types; only types that can have values can be type parameters.
```java
interface Functor<F<?>> {} // Syntax error
```

A type parameter can have an upper bound:
```java
class DelayQueue<E extends Delayed> { ... } // E must be a subtype of `Delayed`. 
```
There is no support for lower-bounded type parameters.

### 1.3 Type Variance
In Scala, a type's variance can be set either at declaration or at the point of use. For example, Scala's immutable 
list type `List[+T]` is always covariant, and the mutable type `Growable[-T]` 
is always contravariant in their element type. In contrast, Scala's array type `Array[T]` is declared invariant, 
leaving variance, if any, to be defined at the use-site. For example, the :
`java.util.Stream.map()` method:
```java
<R> Stream<T> map(Function<? super T, ? extends R> mapper);
```
What this ungainly looking signature is saying is that the `map` method takes parameter of type Function, which is any
function from the stream's current element type T (or its supertype) and returns an arbitrary new type R 
(or its subtype). Which is equivalent to Scala's declaration
```scala
trait Function1[-T1, +R] { ... }
```
Java's design choice of expressing the variance of a type not at declaration but at point of use in method signature
is problematic is not just because it 

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

## 2 Arrays

Java arrays are not objects and are manipulated with special syntax.  An array of elements of some type `T` has 
the type `T[]`. `T` can be either an object or a primitive type. Arrays can be allocated (and, optionally, initialized)
at declaration or, dynamically, at run time.
```java
int[] ints;                                      // Declared only.
LocalDate[] dates = new LocalDate[3];            // Declared and allocated.
BigDecimal[] fines = {BigDecimal.valueOf(20)};   // Declared, allocated and initialized.
ints = new int[2];                               // Allocated dynamically at runtime.
ints = new int[] {1,2};                          // Allocated and initialized dynamically at runtime.
```

Java's arrays predate generic types and the two are mostly incompatible. It is possible to allocate a generic array if 
the parameter type is known at compilation time:
```java
Optional<Integer>[] maybies = new Optional[10];
```
But is not possible to allocate a generic array at runtime due to type erasure on the JVM. There is no way to implement
the following method in Java without resorting to complex runtime reflection:
```java
/** Allocate an array of given length of arbitrary type T */
<T> T[] alloc(int len) { ... }  // Hard and expensive
```
Scala solved this and many other problems related to type erasure with type tags and type classes, so this is imminently
doable in Scala:
```scala
/** Allocate an array of given length of arbitrary type T */
def alloc[T](size: Int)(implicit ct: ClassTag[T]): Array[T] = new Array[T](size)
```

Java arrays are implicitly covariant. `Fish[]` is automatically a subclass of `Pet[]` if (and only if) `Fish` is a 
subclass of `Pet`. Variance violations are checked at run time whenever an element is updated:
```java
class Pet {}
class Fish extends Pet {}
class Snake extends Pet {}
Pet[] pets = new Fish[10];  // Succeeds due to Java's implicit array covariance
pets[0] = new Snake();      // Runtime ArrayStoreException
```
In contrast, Scala's compiler would not have allowed assignment on line 4 because Scala arrays are nonvariant.

## 3 Classes and Inheritance
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

### 1.3 Static Members
Java does not have objects in Scala's sense of the word¹. Rather, static members are declared alongside
with instance members inside the class body and are annotated with the keyword `static`.
While in Scala, an object can extend a trait or even a class, static methods in Java are not subject to inheritance.
When the name of a static member clashes with that of a static member in a superclass, the latter is hidden as a matter
of syntactic context. A static member cannot be abstract or annotated with `@Override`. Interfaces can have static
values, which are available to static members of implementing classes.

¹ In Java, _object_ refers to the same concept as _instance_ in Scala: an instantiation of a concrete class.

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
in order to signal the intent, and to prevent accidental updates, introducing new abstract methods. Such an update will
cause a compilation error on the interface, and not just on the corresponding lambda expressions, which may be located
in different compilation units.

### 2.2 Standard Functional Interfaces
The package `java.util.function` contains an assortment of reusable functional interfaces that fit many common use
cases, including:
| Interface | Corresponding Lambda Expression |
|-----------|---------------------------------|
|`*Consumer` | Functions returning `void` |
|`*Supplier` | Nullary functions to some return type. |
|`*Function` | Non-nullary functions to some return type. |
|`*Predicate` | Non-nullary function returning boolean. |

It is a good idea to reuse these functional interfaces rather than defining custom ones when one of these would do.

### 2.3 Streams and Higher Order Transformations
Unlike Scala, higher order transformations like `map` or `filter` are not available directly on the concrete
collection types. Rather, they are provided by the `java.util.stream.Stream` interface, a concrete instance of
which is obtained by calling the `stream()` method, available on all concrete collection types. For example, 
to transform a list:
```java
var films = List.of("Citizen Kane", "Touch of Evil").stream().filter(f -> f.contains("Kane"));
System.out.println(films);  // java.util.stream.ReferencePipeline$2@372f7a8d
```
As a consequence of the fact that `filter()` was called on the stream instance, the return type of the transformation 
is not a collection but also a stream. This enables chaining transformations in the style of functional programming, 
but requires an additional step terminating the stream by converting it to some collection or folding it to a single 
value. To accomplish either, an extra call to `collect()` which takes one of a variety of collector types, generated
by the static factory methods `java.util.stream.Collectors.*`. For example, to covert the above stream back to a list:

```java
var films = List.of("Citizen Kane", "Touch of Evil").stream().filter(f -> f.contains("Kane")).collect(Collectors.toUnmodifiableList());
System.out.println(films); // [Citizen Kane]
```
Note, that the call to `collect(Collectors.toUnmodifiableList())` can be replaced with  the shortcut `toList()`,
the idiomatic way to convert a collection to an immutable list in Scala. What may come as a surprise, however,
is that the `java.util.ImmutableCollection` class is not public and that very little can be deduced about the 
two immutable list types defined therein, `List12` and `ListN`. `List12` is optimized for short lists consisting
of no more than 2 elements. Longer lists will be converted to `ListN`.

## 3 Case Classes

### 4 Exception Handling
Java supports exceptions with syntax similar to Scala's. They are thrown with the `throw` keyword, and can be caught
with the `try` block:
```java
try {
  var i = Integer.decode(someString);
} catch (NumberFormatException ex) {      
  ...
} catch (RuntimeException ex) {
  ...
} finally {   
  ...
} 
```
The semantics are similar to Scala's, with one crucial difference: Java's exceptions can be both _checked_ and
_unchecked_. Unchecked exceptions behave like Scala exceptions. Checked exceptions must be declared in the signature
of the method that either throws them or calls another method that declares them in its `throws` clause:
```java
String foo() throws Exception {
  ...
  throw new Exception("I am a checked exception");
  ...
}
```
Checked exception may cause a lot of unnecessary boilerplate code and are generally avoided by modern style guides.
Nonetheless, there are many popular libraries that expose checked exception, necessitating handling by the client code.
Unchecked exceptions are those that inherit from `RuntimeException`; they need not be declared.

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