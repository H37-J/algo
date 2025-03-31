package hjk.algo.utils.datastructures;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Bag<E> implements Iterable<E> {

    private Node<E> first;
    private int size;

    private static class Node<E> {
        private E element;
        private Node<E> next;
    }

    public Bag() {
        first = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        Node<E> newNode = new Node<E>();
        newNode.element = element;
        newNode.next = first;
        first = newNode;
        size++;
    }

    public boolean contains(E element) {
        for (E value : this) {
            if (value.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator()  {
        return new ListIterator<>(first);
    }

    private static class ListIterator<E> implements Iterator<E> {
        private Node<E> current;

        ListIterator(Node<E> first) {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }


        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the bag.");
            }
            E element = current.element;
            current = current.next;
            return element;
        }
     }
}
