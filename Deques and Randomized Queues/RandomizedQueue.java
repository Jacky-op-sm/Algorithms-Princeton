import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;
    private final double use_ratio = 0.25;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[4];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = item;
        size += 1;
    }

    private void resize(int n) {
        Item[] temp = (Item[]) new Object[n];
        for (int i = 0; i < size; i += 1) {
            temp[i] = items[i];
        }
        items = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(size);
        swap(items, i, size - 1);
        Item t = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        if ((double) size / items.length <= use_ratio) {
            resize(Math.max(4, items.length / 2));
        }
        return t;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(size);
        return items[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        Item[] p;
        int index;

        public ListIterator() {
            index = 0;
            p = items.clone();
            for (int i = 0; i < size; i += 1) {
                int j = StdRandom.uniform(i + 1);
                swap(p, i, j);
            }
        }

        @Override
        public boolean hasNext() {
            return index != size;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item n = p[index];
                index += 1;
                return n;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void swap(Item[] p, int i, int j) {
        Item temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }

    // unit testing (required)
    public static void main(String[] args) {

        // test 1
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.isEmpty();
        rq.size();
        rq.enqueue(26);
        rq.dequeue();
        rq.enqueue(16);
        rq.dequeue();
        rq.isEmpty();
        rq.enqueue(24);
        rq.dequeue();
        rq.size();
        rq.enqueue(20);


        //test 2

        //enqueue and dequeue;
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        for (int i = 0; i < 10; i += 1) {
            test.enqueue(i);
        }
        for (int i = 0; i < 8; i += 1) {
            System.out.print(test.dequeue() + " ");
        }

        System.out.println("");
        //iterator part
        for (int i: test) {
            System.out.print(i + " ");
        }


    }

}
