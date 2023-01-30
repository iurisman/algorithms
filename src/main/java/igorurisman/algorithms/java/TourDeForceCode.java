package igorurisman.algorithms.java;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class TourDeForceCode {
  static LocalDate readBirthdateFromDatabase(String name) {
    return LocalDate.of(1964, Month.JUNE, 7);
  }
  static void println(Object s) {
    System.out.println(s);
  }

  public static void main(String[] args) {

    interface Tree<T> {
      T value();
      static <U> String toString(Tree<U> tree) {
        return "";
      }
    }

    record Leaf<T>(T value) implements Tree<T> {
      @Override public String toString() {
        return Tree.toString(this);
      }
    }
    record Node<T>(T value, Tree<T> left, Tree<T> right) implements Tree<T> {}
  }
}

