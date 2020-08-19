import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class IntNode {
        private IntNode prev;
        private Item item;
        private IntNode next;

        public IntNode() {
        }

        public IntNode(IntNode p, Item i, IntNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private IntNode sentinel;
    private IntNode last;
    private int size;

    // construct an empty deque
    public Deque() {
            sentinel = new IntNode();
            sentinel.prev = sentinel;
            sentinel.next = sentinel;
            last = sentinel;
            size = 0;
        }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        sentinel.next = new IntNode(sentinel, item, sentinel.next);
        if (sentinel.next.next == sentinel) {
            last = sentinel.next;
        }
        sentinel.next.next.prev = sentinel.next;
        size = size + 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        last.next = new IntNode(last, item, sentinel);
        last = last.next;
        sentinel.prev = last;
        size = size + 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Item item = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            if (sentinel.next == sentinel) {
                last = sentinel;
            }
            sentinel.next.prev = sentinel;
            size = size - 1;
            return item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Item item = last.item;
            last = last.prev;
            last.next = sentinel;
            sentinel.prev = last;
            size = size - 1;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        IntNode p = sentinel;
        int i = 0;

        @Override
        public boolean hasNext() {
            return i != size;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item t = p.next.item;
                i += 1;
                p = p.next;
                return t;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}