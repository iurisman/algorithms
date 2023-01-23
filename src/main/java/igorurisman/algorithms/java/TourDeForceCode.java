package igorurisman.algorithms.java;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TourDeForceCode {
  static LocalDate readBirthdateFromDatabase(String name) {
    return LocalDate.of(1964, Month.JUNE, 7);
  }
  static void println(Object s) {
    System.out.println(s);
  }
  public static void main(String[] args) {

    var i = Integer.valueOf(4);  // Type Integer
    Integer j = 5;                  // Ditto
    println(i + j);                 // 9


//      .map(String::toUpperCase)
//      .collect(Collectors.toUnmodifiableList());
//    System.out.println(films);
//    System.out.println(films.getClass().getName());
//    var filmsByDirector = new HashMap<String, List<String>>() {{
//      put("Orson Wells", films);
//    }};
//    System.out.println(filmsByDirector.values().getClass().getName());


    //Map<String, List<String>> filmography = Map.of("OrsonWelles", );
    //var AlsBirthday = birthdateMap.computeIfAbsent("Al", (name) -> readBirthdateFromDatabase(name));
    //System.out.println(filmography);

  }

}

