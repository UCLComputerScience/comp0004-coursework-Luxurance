package uk.ac.ucl.bag;

import java.util.Iterator;

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

    private Element<T> head;

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
            Element<T> curElement = head.next;
            while(curElement.next != null){
                if(curElement.value == value){
                    curElement.occurrences++;
                    return;
                }
                curElement = curElement.next;
            }
            if(curSize < maxSize) {
                curElement.next = new Element<T>(value, 1, null);
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

    private Element<T> find(T value){
        Element<T> curElement = head;
        if(curElement != null){
            while(curElement.next != null){
                if(curElement.value == value){
                    return curElement;
                }
                curElement = curElement.next;
            }
        }
        return null;
    }

    public boolean contains(T value){
        return find(value)!=null;
    }

    public int countOf(T value){
        Element<T> foundElement = find(value);
        return foundElement.occurrences;
    }

    public void remove(T value){
        Element<T> preElement = null;
        Element<T> curElement = head;
        if(curElement != null){
            while(curElement.next != null){
                if(curElement.value == value){
                    curElement.occurrences--;
                }
                if(curElement.occurrences == 0){
                    if(curElement == head){
                        head = curElement.next;
                    }
                    else{
                        preElement.next = curElement.next;
                    }
                }
                preElement = curElement;
                curElement = curElement.next;
            }
        }
    }

    public boolean isEmpty(){
        return head==null;
    }

    public int size(){
        int count = 0;
        Element<T> curElement = head;
        if(curElement != null){
            if(curElement.next == null){
                count++;
            }
            while(curElement.next != null){
                count++;
                curElement = curElement.next;
            }
        }
        return count;
    }

    private class ArrayBagUniqueIterator implements Iterator<T>{
        private Element<T> curElement = null;
        public boolean hasNext(){
            if(curElement == null){
                return false;
            }
            if(curElement.next == null){
                return false;
            }
            return true;
        }
        public T next(){
            if(curElement == null){
                if(head == null){
                    return null;
                }
                curElement = head;
                return curElement.value;
            }
            curElement = curElement.next;
            return curElement.value;
        }
    }

    public Iterator<T> iterator(){
        return new ArrayBagUniqueIterator();
    }

    private class ArrayBagIterator implements Iterator<T>{
        private Element<T> curElement = null;
        private int count = 0;
        public boolean hasNext(){
            if(curElement == null){
                return false;
            }
            if(curElement.next == null){
                if(count == curElement.occurrences) {
                    return false;
                }
            }
            return true;
        }
        public T next(){
            if(curElement == null){
                if(head == null){
                    return null;
                }
                curElement = head;
                count = 1;
                return curElement.value;
            }
            if(count < curElement.occurrences){
                count++;
                return curElement.value;
            }
            curElement = curElement.next;
            count = 1;
            return curElement.value;
        }
    }

    @Override
    public Iterator<T> allOccurrencesIterator() {
        return new ArrayBagIterator();
    }
}
