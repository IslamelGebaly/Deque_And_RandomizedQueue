import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty randomized queue
    private int tail;
    private int size;
    private int capacity;
    private Item[] container;

    public RandomizedQueue() {
        capacity = 1;
        size = 0;
        tail = -1;
        container = (Item[]) new Object[capacity];
    }

    private boolean isFull() {
        return size == capacity;
    }

    private void swap(int i, int j) {
        Item temp = container[i];
        container[i] = container[j];
        container[j] = temp;
    }

    private void resize() {
        Item[] copy;

        if (isFull()) {
            copy = (Item[]) new Object[capacity * 2];
            capacity *= 2;
        } else {
            copy = (Item[]) new Object[capacity / 2];
            capacity = capacity / 2;
        }

        for (int i = 0; i < size; i++) {
            copy[i] = container[i];
        }

        container = copy;
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
        if (item == null)
            throw new IllegalArgumentException();

        tail++;
        container[tail] = item;
        size++;

        if (isFull())
            resize();
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("");

        int i = StdRandom.uniformInt(0, size);
        Item item = container[i];
        swap(tail, i);
        tail--;
        size--;

        if (size < capacity / 2)
            resize();

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        int i = StdRandom.uniformInt(0, size);
        Item item = container[i];

        return item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;

        private RandomizedQueueIterator() {
            current = tail;
        }

        public boolean hasNext() {
            return !(current == -1);
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("");

            int i = StdRandom.uniformInt(0, current + 1);
            Item item = container[i];
            swap(current, i);
            current--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {

    }
}
