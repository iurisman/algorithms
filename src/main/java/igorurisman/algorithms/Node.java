package igorurisman.algorithms;

import java.util.List;

record Node<C>(C content, List<Tree<C>> children) implements Tree<C> {}
