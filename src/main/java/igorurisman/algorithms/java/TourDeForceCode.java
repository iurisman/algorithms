package igorurisman.algorithms.java;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class TourDeForceCode {
  static LocalDate readBirthdateFromDatabase(String name) {
    return LocalDate.of(1964, Month.JUNE, 7);
  }
  public static void main(String[] args) {

    interface Functor<F<?>> { ... }

        //    var films = List.of("Citizen Kane", "Touch of Evil").stream()
//      .filter(f -> f.contains("Kane"))
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

