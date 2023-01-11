package igorurisman.algorithms.java.tree;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public sealed abstract class Tree<C> permits Node, Leaf {

  abstract C get();

  //public Tree<C> get(int n);


  public static <C> Tree<C> fill(int size, int maxDegree, Supplier<C> op) throws Exception {
    if (size == 1) {
      return new Leaf<C>(op.get());
    } else if (size > 1) {
      var degree = 1 + new Random().nextInt(maxDegree);
      // each child should have roughly equal size
      var childSizes = new int[degree];
      Arrays.fill(childSizes, 0);
      for (int i = 0; i < size - 1; i++) childSizes[i % degree] += 1;
      // In case we allocated more children than we need:
      degree = (int) Arrays.stream(childSizes).filter(d -> d > 0).count();
      @SuppressWarnings("unchecked")
      Tree<C>[] children = new Tree[degree];
      for (int i = 0; i < degree; i++) {
        children[i] = fill(childSizes[i], maxDegree, op);
      }
      return new Node<C>(op.get(), children);
    } else {
      throw new Exception(String.format("Size must be > 1 but was %s", size));
    }
  }
}

final class Leaf<C> extends Tree<C> {
  private final C value;
  Leaf(C value) {
    this.value = value;
  }
  @Override
  public C get() {
    return value;
  }
}

final class Node<C> extends Tree<C> {

  final C value;
  final Tree<C>[] children;

  Node(C value, Tree<C>[] children) {
    this.value = value;
    this.children = children;
  }

  private <T> T traverse(T t) {
    return null;
  }

  @Override
  public C get() {
    return this.value;
  }
}