package igorurisman.algorithms.java.tree;


import java.util.List;

public sealed interface Tree<C> permits Leaf, Node {

  C get();

  //public Tree<C> get(int n);
}

record Leaf<C>(C value) implements Tree<C> {
  @Override
  public C get() {
    return value;
  }
}

record Node<C>(C value, List<Tree<C>> children) implements Tree<C> {
  private <T> T traverse(T t) {
    return null;
  }

  @Override
  public C get() {
    return value;
  }
}
