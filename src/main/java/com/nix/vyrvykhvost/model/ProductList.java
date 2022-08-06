package com.nix.vyrvykhvost.model;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

public class ProductList<T extends Product> implements Iterable<T> {
    private final List<Integer> versions;
    private ProductNode<T> first;
    private ProductNode<T> last;
    private int size;

    public ProductList() {
        versions = new ArrayList<>();
        size = 0;
    }

    public void addOnHead(T product, int version, LocalDate date) {
        final ProductNode<T> firstNode = first;
        final ProductNode<T> newNode = new ProductNode<>(product, version, date, firstNode, null);
        first = newNode;
        if (first == null) {
            last = newNode;
        } else {
            firstNode.previousNode = newNode;
        }
        size++;
        versions.add(newNode.version);
    }

    public T findByVersion(int version) {
        if (isVersionTrue(version)) {
            for (ProductNode<T> temp = first; temp != null; temp = temp.nextNode) {
                if (temp.version == version) {
                    return temp.product;
                }
            }
        }
        throw new NoSuchElementException("Product with this version not found");
    }

    public String deleteByVersion(int version) {
        if (isVersionTrue(version)) {
            for (ProductNode<T> temp = first; temp != null; temp = temp.nextNode) {
                if (temp.version == version) {
                    delete(temp);
                    return "Product has removed";
                }
            }
        }
        return "Product with this version not found";
    }

    public LocalDate getFirstDateVersion() {
        return last.date;
    }

    public LocalDate getLastDateVersion() {
        return first.date;
    }

    public String setByVersion(int version, T product) {
        if (isVersionTrue(version)) {
            for (ProductNode<T> temp = first; temp != null; temp = temp.nextNode) {
                if (temp.version == version) {
                    temp.product = product;
                    return "Product has set";
                }
            }
        }
        return "Product with this version not found";
    }

    public int getCountOfVersion() {
        return versions.size();
    }


    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private ProductNode<T> nextElement = first;

            @Override
            public boolean hasNext() {
                return nextElement != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T element = nextElement.product;
                nextElement = nextElement.nextNode;
                return element;
            }
        };
    }

    public boolean isVersionTrue(int version) {
        return versions.contains(version);
    }

    private T delete(ProductNode<T> node) {
        final T product = node.product;
        final ProductNode<T> next = node.nextNode;
        final ProductNode<T> previous = node.previousNode;

        if (previous == null) {
            first = next;
        } else {
            previous.nextNode = next;
            node.previousNode = null;
        }
        if (next == null) {
            last = previous;
        } else {
            next.previousNode = previous;
            node.nextNode = null;
        }
        node.product = null;
        versions.remove(node.version);
        size--;
        return product;
    }



    private class ProductNode<T> {
        int version;
        LocalDate date;
        T product;
        ProductNode<T> nextNode;
        ProductNode<T> previousNode;

        public ProductNode(T product, int version, LocalDate date, ProductNode<T> nextNode, ProductNode<T> previousNode) {
            this.product = product;
            this.version = version;
            this.date = date;
            this.nextNode = nextNode;
            this.previousNode = previousNode;
        }

    }
}
