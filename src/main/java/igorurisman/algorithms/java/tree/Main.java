package igorurisman.algorithms.java.tree;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    var l = new Leaf<String>("a");
    var n = new Node<String>("n", List.of(l));
    System.out.println(n);
  }

}
