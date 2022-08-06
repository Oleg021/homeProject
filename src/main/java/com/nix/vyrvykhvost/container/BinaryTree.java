package com.nix.vyrvykhvost.container;

import com.nix.vyrvykhvost.model.Product;


import java.util.Stack;

public class BinaryTree<T extends Product> {
    private final ComporatorProduct<T> productComparator = new ComporatorProduct<>();
    private Node<T> root;


    private Node addRecursive(Node node, T value) {
        if (node == null) {
            return new Node<T>(value);
        }
        if (productComparator.compare((T) node.product, value) < 0) {
            node.left = addRecursive(node.left, value);
        } else if (productComparator.compare((T) node.product, value) > 0) {
            node.right = addRecursive(node.right, value);
        } else {
            return node;
        }

        return node;
    }

    public void printTree(String mode) {
        Stack<Node<T>> globalStack = new Stack<Node<T>>();


        globalStack.push(root);
        int gaps = 32;
        boolean isRowEmpty = false;

        while (isRowEmpty == false) {
            Stack<Node<T>> localStack = new Stack<Node<T>>();
            isRowEmpty = true;
            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) {
                Node temp = globalStack.pop();
                if (temp != null) {
                    switch (mode) {
                        case "PRICE" -> System.out.print(Math.ceil(temp.product.getPrice()));
                        case "TITLE" -> System.out.print(temp.product.getTitle());
                        case "COUNT" -> System.out.print(temp.product.getCount());
                        case "TYPE" -> System.out.print(temp.product.getProductType().name());
                        default -> throw new IllegalStateException("Unknown print mode: " + mode);
                    }
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if (temp.left != null ||
                            temp.right != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("__");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
            System.out.println();
        }

    }

    public double countLeftBranch() {
        if (root.left == null) {
            return 0;
        }
        return sum(root.left);
    }

    public double countRightBranch() {
        if (root.right == null) {
            return 0;
        }
        return sum(root.right);
    }

    private double sum(Node<T> root) {
        if (root == null)
            return 0;
        return (root.product.getPrice() + sum(root.left) +
                sum(root.right));
    }


    public void add(T value) {
        root = addRecursive(root, value);
    }

    private class Node<T extends Product> {
        public T product;
        public Node<T> left;
        public Node<T> right;

        Node(T product) {
            this.product = product;
            right = null;
            left = null;
        }

    }
}
