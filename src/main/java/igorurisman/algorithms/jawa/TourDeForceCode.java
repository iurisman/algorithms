package igorurisman.algorithms.jawa;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class TourDeForceCode {

  static interface Foo {}

  static class Bar implements Foo {}

  public static void main(String[] args) {
    var list1 = new ArrayList<Foo>();
    var list2 = new ArrayList<Foo>();
    list1.add(new Bar());

    System.out.println("Done.");
  }

  public static <T> List<? extends T> concat(List<? extends T>... lists) {
    return Stream.of(lists).flatMap(List::stream).toList();
  }

}

