# Tour de Force of Modern Java for Scala Programmers

#### Covers Java 17
By Igor Urisman, January 14, 2023.

* Tradeoff desision by language designers in favor of backward compatibility...

## 1 General Comments
### 1.1 Grammar
Modern Java has limited type inference. In particular in most variable declarations the type can be inferred:
```java
var films = new LinkedList<String>();
```
is equivalent to
```java
LinkedList<String> films = new LinkedList<String>();
```

As well, just like in Scala, the type parameter list (but, unlike Scala, not the angle brackets) on the right of 
the assignment may be dropped,if the value's type is explicitly declared on the left:

```java
List<String> list = new LinkedList<>();
```

The keyword `var` has the same meaning as in Scala. There is no `val` keyword in Java, but the qualifier `final`
can be used to declare an immutable value:
```java
final var films = new LinkedList<String>();
```

### 1.2 Inheritance
Like Scala, Java implements single inheritance mechanism, whereby a class can inherit from one class and
implement any number of interfaces:
```java
class Foo extends Bar implements Baz, Qux { ... }
```
Only methods can be overridden. A field defined in a subclass hides a likely-named field in the superclass as
a matter of scope, not inheritance.

Interfaces are partial equivalents of Scala traits with the following limitations:
* Java interfaces are not stackable: they are always selfless and their order in a type declaration is not significant.
* They may only contain abstract methods. Fields defined in the interface must be concrete.
* Concrete methods in interfaces are known as default implementations and must be declared with the `default`
qualifer. 

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
```

There are no lazy fields in Java, all fields must be initialized at class creation time either with a static
expression or by a constructor and can always be declared final.
```java
class Foo {
  final int myAge;
  Foo() { myAge = 100; }
}
```
### 1.2 Type Parameters
In Java, types which take parameters 

Java does not support higher-kinded types (and, consequently, no type classes):
```java
interface Foo<X<?>> {} // Syntax error
```

## 2 Java Streams and Lambda Expressions
Starting with Java 8, it is possible to use a syntax resembling functional literal in place
of a method parameter:

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

#### ?.1.3 Maps
Since the introduction of lambdas (See section ...) in Java 8, some new "higher order" methods are available on maps:

```java
```

Since Java 9, map types also support factory methods for generating immutable co
```java
    Map<String, LocalDate> birthdayMap = Map.of("Alfred Aho", LocalDate.of(1941, Month.August, 9));
```
will instantiate an immutable ("unmodifiable" in Java speak) implementation of the interface `java.util.Map`.