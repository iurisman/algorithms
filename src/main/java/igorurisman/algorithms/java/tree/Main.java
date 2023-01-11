package igorurisman.algorithms.java.tree;

import java.util.List;
import java.util.Random;

public class Main {
  public static void main(String[] args) throws Exception {
    var tree = Tree.fill(3, 3, () -> new Random().nextInt());
    System.out.println(tree);
  }

}

