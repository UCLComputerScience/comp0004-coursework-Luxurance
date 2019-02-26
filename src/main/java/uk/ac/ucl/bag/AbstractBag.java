package uk.ac.ucl.bag;

/**
 * This class implements methods common to all concrete bag implementations
 * but does not represent a complete bag implementation.<br />
 *
 * New bag objects are created using a BagFactory, which can be configured in the application
 * setup to select which bag implementation is to be used.
 */
import java.util.Comparator;
import java.util.Iterator;

public abstract class AbstractBag<T> implements Bag<T>,Comparator<T>
{
  public Bag<T> createMergedAllOccurrences(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this)
    {
      result.addWithOccurrences(value, this.countOf(value));
    }
    for (T value : b)
    {
      result.addWithOccurrences(value, b.countOf(value));
    }
    return result;
  }

  public Bag<T> createMergedAllUnique(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this)
    {
      if (!result.contains(value)) result.add(value);
    }
    for (T value : b)
    {
      if (!result.contains(value)) result.add(value);
    }
    return result;
  }

    @Override
    public String toString() {
        String result = "";
        result = result + "[";
        boolean first = true;
        for(T value : this){
            if(!first){
                result = result + ", ";
            }
            first = false;
            result += value;
            result += ": ";
            result += this.countOf(value);
        }
        result += "]";
        return result;
    }

    public void removeAllCopies() {
        for(T value : this){
            while(countOf(value) != 1){
                remove(value);
                System.out.println(countOf(value));
                System.out.println(value);
            }
        }
    }

    public Bag<T> subtract(Bag<T> bag) throws BagException {
        try {
            BagFactory<T> factory = BagFactory.getInstance();
            String classString = bag.getClass().toString();
            ClassHandler classHandler = new ClassHandler();
            String className = classHandler.getClassString(classString);
            factory.setBagClass(className);
            Bag<T> newBag = factory.getBag();
            for (T value : this) {
                newBag.addWithOccurrences(value, countOf(value));
            }
            for (T value : newBag) {
                if (bag.contains(value)) {
                    for (int i = 0; i < bag.countOf(value); i++) {
                        newBag.remove(value);
                        if (!newBag.contains(value)) {
                            break;
                        }
                    }
                }
            }
            return newBag;
        }
        catch(ClassCastException a){
            return this; //subtract a bag with contents of different type will give the origin bag
        }
    }

    @Override
    public int compare(T o1, T o2) {
      if(o1.equals(o2)){
          return 0;
      }
      return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Bag){
            for(T value : this){
                if(!((Bag) obj).contains(value)){
                    return false;
                }
                if(((Bag) obj).countOf(value) != this.countOf(value)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
