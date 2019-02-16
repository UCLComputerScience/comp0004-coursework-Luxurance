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

    private int curSize;

    private Element head;

    public LinkedListBag() throws BagException{
        this(MAX_SIZE);
    }

    public LinkedListBag(int maxSize) throws BagException{
        this.maxSize = maxSize;
        this.curSize = 0;
        this.head = null;
    }

    public void add(T value) throws BagException{
        if(head == null){
            head = new Element(value, 1, null);
            curSize++;
        }
        else{
            Element curElement = head.next;
            while(curElement.next != null){
                if(curElement.value == value){
                    curElement.occurrences++;
                    return;
                }
                curElement = curElement.next;
            }
            if(curSize < maxSize) {
                curElement.next = new Element(value, 1, null);
                curSize++;
            }
            else{
                throw new BagException("Bag is full");
            }
        }
    }

    public void addWithOccurrences(T value, int occurrences) throws BagException{
        for(int i = 0; i < occurrences; i++){
            add(value);
        }
    }


}
