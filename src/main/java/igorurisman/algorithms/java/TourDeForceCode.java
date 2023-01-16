package igorurisman.algorithms.java;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TourDeForceCode {
  static LocalDate readBirthdateFromDatabase(String name) {
    return LocalDate.of(1964, Month.JUNE, 7);
  }
  public static void main(String[] args) {

    var films = List.of("Citizen Kane", "Touch of Evil");
    films = List.copyOf(films);
    System.out.println(films);

//    var filmsByDirector = new HashMap<String, List<String>>() {{
//      put("Orson Wells", films);
//    }};
//    System.out.println(filmsByDirector);


    //Map<String, List<String>> filmography = Map.of("OrsonWelles", );
    //var AlsBirthday = birthdateMap.computeIfAbsent("Al", (name) -> readBirthdateFromDatabase(name));
    //System.out.println(filmography);
  }

}

