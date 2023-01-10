package igorurisman.algorithms;

import java.util.List;

public interface Tree<C> {

  public C content();

  public C random();

  public static void main(String[] args) {
    var tree = new Leaf<String>("a");
    System.out.println(tree);
  }
}

record Leaf<C>(C content) implements Tree<C> {
  @Override
  public C random() {
    return content;
  }
}

record Node<C>(C content, List<Tree<C>> children) implements Tree<C> {
  private <T> T traverse(T t) {
    return null;
  }

  @Override
  public C random() {


    return null;
  }
}
