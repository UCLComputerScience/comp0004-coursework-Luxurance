package uk.ac.ucl.bag;

/**
 * This class implements methods common to all concrete bag implementations
 * but does not represent a complete bag implementation.<br />
 *
 * New bag objects are created using a BagFactory, which can be configured in the application
 * setup to select which bag implementation is to be used.
 */
import java.util.Iterator;

public abstract class AbstractBag<T extends Comparable> implements Bag<T>
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

    public Bag<T> subtract(Bag<T> bag) throws BagException{
      BagFactory<T> factory = BagFactory.getInstance();
      String classString = bag.getClass().toString();
      String className = "";
      for(int i = classString.length() - 1; i >= 0; i--){
          if(Character.toString(classString.charAt(i)).equals(".")){
              break;
          }
          className = classString.charAt(i) + className;
      }
      factory.setBagClass(className);
      Bag<T> newBag = factory.getBag();
      for(T value : this){
          if(!bag.contains(value)){
              newBag.addWithOccurrences(value,countOf(value));
          }
      }
      return newBag;
    }
}
