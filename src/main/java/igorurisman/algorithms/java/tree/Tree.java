package igorurisman.algorithms.java.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;

public sealed interface Tree<C> permits Node, Leaf {

  C get();

  //public Tree<C> get(int n);

  public static <C> Tree<C> fill(int size, int maxDegree, Callable<C> op) throws Exception {
    if (size == 1) {
      return new Leaf<C>(op.call());
    } else if (size > 1) {
      var degree = 1 + new Random().nextInt(maxDegree);
      // each child should have roughly equal size
      var childSizes = new int[degree];
      Arrays.fill(childSizes, 0);
      for (int i = 0; i < size - 1; i++) childSizes[i % degree] += 1;
      // In case we allocated more children than we need:
      degree = (int) Arrays.stream(childSizes).filter(d -> d > 0).count();
      var children = new Tree[degree];
      for (int i = 0; i < degree; i++) {
        children[i] = fill(childSizes[i], maxDegree, op);
      }
      return new Node<C>(op.call(), children);
    } else {
      throw new Exception(String.format("Size must be > 1 but was %s", size));
    }
  }
}

record Leaf<C>(C value) implements Tree<C> {
  @Override
  public C get() {
    return value;
  }
}

record Node<C>(C value, Tree<C>[] children) implements Tree<C> {
  private <T> T traverse(T t) {
    return null;
  }

  @Override
  public C get() {
    return value;
  }
}