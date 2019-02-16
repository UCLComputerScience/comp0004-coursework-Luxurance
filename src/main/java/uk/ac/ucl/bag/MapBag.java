package uk.ac.ucl.bag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MapBag<T extends Comparable> extends AbstractBag<T> {
    private int maxSize;

    private HashMap<T,Integer> contents;

    public MapBag() throws BagException{
        this(MAX_SIZE);
    }

    public MapBag(int maxSize) throws BagException {
        if (maxSize > MAX_SIZE){
            throw new BagException("Attempting to create a Bag with size greater than maximum");
        }
        if (maxSize < 1){
            throw new BagException("Attempting to create a Bag with size less than 1");
        }
        this.maxSize = maxSize;
        contents = new HashMap<>();
    }

    @Override
    public void add(T value) throws BagException {
        if(contents.containsKey(value)){
            contents.put(value,contents.get(value)+1);
            return;
        }
        if(contents.size() < maxSize){
            contents.put(value,1);
        }
        else {
            throw new BagException("Bag is full");
        }
    }

    public void addWithOccurrences(T value, int occurrences) throws BagException{
        for(int i = 0; i < occurrences; i++){
            add(value);
        }
    }

    public boolean contains(T value){
        return contents.containsKey(value);
    }

    public int countOf(T value){
        if(contents.containsKey(value)) return contents.get(value);
        else return 0;
    }

    public void remove(T value){
        //? remove non existing element
        if(contents.containsKey(value)){
            contents.put(value,contents.get(value)-1);
        }
        if(contents.get(value) == 0){
            contents.remove(value);
        }
    }

    public boolean isEmpty(){
        return contents.isEmpty();
    }

    public int size(){
        return contents.size();
    }

    private class ArrayBagUniqueIterator implements Iterator<T>{
        private Set<T> keys = contents.keySet();
        private Iterator<T> iterator = keys.iterator();
        public boolean hasNext(){
            return iterator.hasNext();
        }
        public T next(){
            return iterator.next();
        }
        public Iterator<T> getIterator(){
            return iterator;
        }
    }

    public Iterator<T> iterator(){
        return new ArrayBagUniqueIterator();
    }

    private class ArrayBagIterator implements Iterator<T>{
        private Set<T> keys = contents.keySet();
        private Iterator<T> iterator = keys.iterator();
        private int count = 0;
        private T current;
        public boolean hasNext(){
            if(iterator.hasNext()){
                return true;
            }
            else{
                return count < contents.get(current);
            }
        }
        public T next(){
            if(current == null){
                current = iterator.next();
                count++;
                return current;
            }
            else{
                if(count < contents.get(current)){
                    count++;
                    return current;
                }
                else{
                    current = iterator.next();
                    count = 1;
                    return current;
                }
            }
        }
    }

    @Override
    public Iterator<T> allOccurrencesIterator() {
        return new ArrayBagIterator();
    }
}
