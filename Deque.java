import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque

    private int first;
    private int last;
    private int size;
    private int capacity;
    private Item[] array;

    public Deque() {
        array = (Item[]) new Object[1];
        first = 0;
        last = first;
        size = 0;
        capacity = 1;
    }

    private void resize() {
        Item[] copy;
        int i = (first + 1) % capacity;
        int oldCap = capacity;

        if (isFull()) {
            copy = (Item[]) new Object[capacity * 2];
            capacity *= 2;
        } else {
            copy = (Item[]) new Object[capacity / 2];
            capacity = capacity / 2;
        }

        for (int j = 0; j < size; j++) {
            copy[j] = array[i];
            i = (i + 1) % oldCap;
        }

        first = capacity - 1;
        last = size - 1;
        array = copy;
    }

    private boolean isFull() {
        return size == capacity;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null)
            throw new IllegalArgumentException();

        array[first--] = item;
        if (first == -1)
            first = capacity - 1;

        size++;

        if (isFull())
            resize();
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null)
            throw new IllegalArgumentException();

        last = (last + 1) % capacity;
        array[last] = item;
        size++;

        if (isFull())
            resize();
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        first = (first + 1) % capacity;
        Item item = array[first];
        size--;

        if (size < capacity / 2)
            resize();
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = array[last--];
        size--;

        if (size < capacity / 2)
            resize();

        return item;
    }

    // return an iterator over items in order from front to back

    private class DequeIterator implements Iterator<Item> {

        int current;
        boolean next;

        public DequeIterator() {
            this.current = (first + 1) % capacity;
            next = true;
        }

        public boolean hasNext() {
            if (isEmpty() || !next)
                return false;
            return true;
        }

        public Item next() {
            if (!next)
                throw new NoSuchElementException();

            if (current == last)
                next = false;

            Item item = array[current];
            current = (current + 1) % capacity;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {

        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);

        //Iterator<Integer> itr = d.iterator();


    }


}
