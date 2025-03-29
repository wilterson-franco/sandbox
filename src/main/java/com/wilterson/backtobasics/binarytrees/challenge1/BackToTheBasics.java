package com.wilterson.backtobasics.binarytrees.challenge1;

public class BackToTheBasics {

    private InputController inputController;

    public BackToTheBasics(InputController inputController) {
        this.inputController = inputController;
    }

    public static void main(String[] args) {

        InputController ic = new InputController(System.in);
        BackToTheBasics backToTheBasics = new BackToTheBasics(ic);

        Tree tree = backToTheBasics.buildTree();
        Tree.visit(tree.getRoot(), 0, (l, x) -> tree.listByLevel(new TreeLevel(l), x));
//        Tree.visit(tree.getRoot(), 0, (l, x) -> System.out.println("Level %d: %d".formatted(l, x)));

        tree.getNumbersByLevel().forEach((k, v) -> {
            String join = String.join(", ", v.stream().map(Object::toString).toList());
            System.out.printf("Level %d: %s%n", k.level(), join);
        });
    }

    private Tree buildTree() {
        Tree tree = new Tree();
        inputController.captureInput().forEach(tree::add);
        return tree;
    }
}
