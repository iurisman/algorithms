package igorurisman.algorithms;

public sealed interface Tree<C> permits Node, Leaf {

    public C content();

    public static void main(String[] args) {
        var tree = new Leaf<String>("a");
        System.out.println(tree);
    }
}
