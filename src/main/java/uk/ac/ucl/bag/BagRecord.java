package uk.ac.ucl.bag;

public class BagRecord<T extends Comparable> {

    private T value;

    private int occurrences;

    public BagRecord(){}

    public void setValue(T value){
        this.value = value;
    }

    public void setOccurrences(int occurrences){
        this.occurrences = occurrences;
    }

    public T getValue(){
        return value;
    }

    public int getOccurrences(){
        return occurrences;
    }
}
