package org.example;


public class Main {
    public static void main(String[] args) {

        Tree binaryTree=new Tree();
        binaryTree.addNode(24);
        binaryTree.addNode(5);
        binaryTree.addNode(1);
        binaryTree.addNode(15);
        binaryTree.addNode(3);
        binaryTree.addNode(8);
        //binaryTree.addNode(2);
        //binaryTree.addNode(100);


        System.out.println(binaryTree.isExistNode(24));

        System.out.println(binaryTree.findRoot() + " - корень дерева");

        binaryTree.printTree(binaryTree.findRoot());

    }
}