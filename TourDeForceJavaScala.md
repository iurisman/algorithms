# Tour de Force of Java for Scala Programmers
By Igor Urisman | January 14, 2023

* Tradeoff desision by language designers in favor of backward compatibility...

## 1 Java Streams and Lambda Expressions
Starting with Java 8, it is possible to use a syntax resembling functional literal in place
of a method parameter:

```java
var igorsBirthdate = birthdaysMap.compute("Igor", key -> Date())
```



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
Immutable collections were introduced in Java 19. They are instantiated with the static factory methods `of()` 
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
This may be a source of hard to analyse bugs because the `UnsupportedOperationException` exception thrown in
this case, depends on a particular execution history. Language designers clearly realized that this is
a problem and offered some recourse: the `` is annotated with...

```java
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