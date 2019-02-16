package uk.ac.ucl.bag;

public class LinkedListBag <T extends Comparable> extends AbstractBag<T>{

    private static class Element<E> {
        public E value;
        public int occurrences;
        public Element<E> next;

        public Element(E value, int occurrences, Element<E> next) {
            this.value = value;
            this.occurrences = occurrences;
            this.next = next;
        }
    }

    private int maxSize;

    private Element head;

    public LinkedListBag() throws BagException{
        this(MAX_SIZE);
    }

    public LinkedListBag(int maxSize) throws BagException{
        this.maxSize = maxSize;
        this.head = null;
    }
}
