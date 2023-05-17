package igorurisman.algorithms.jawa;

import java.util.*;
import java.util.function.Function;

public class TourDeForceCode {
  static void println(Object s) {
    System.out.println(s);
  }

  interface IntFunction<R> extends Function<Integer, R> {};

  public static void main(String[] args) {
    var map = Collections.synchronizedMap(new HashMap<String,String>());
    println(map);
  }
}

