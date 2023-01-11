package igorurisman.algorithms.java.tree;

import java.util.Random;
import static org.apache.commons.lang.RandomStringUtils.*;

public class Main {
  public static void main(String[] args) throws Exception {
    var tree = Tree.fill(15, 3, () -> randomAlphanumeric(1));
    System.out.println(tree);
  }
}

