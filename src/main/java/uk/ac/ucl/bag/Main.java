package uk.ac.ucl.bag;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Example code illustrating the use of Bag objects.
 */
public class Main
{
  private BagFactory factory = BagFactory.getInstance();

  private File file = new File("/Users/Lance 1 2/IdeaProjects/cwBag/comp0004-coursework-Luxurance/src/main/java/uk/ac/ucl/bag/bagData.txt");

  private List bagList = new ArrayList<>();

  private BagLoader bagLoader = new BagLoader(file,bagList);

  private BagSaver bagSaver = new BagSaver(file,bagList);

  public <T extends Comparable> void print(Bag<T> bag)
  {
    boolean first = true;
//    System.out.println(bag.size()); //test
    System.out.print("{");
    for (T value : bag)
    {
      if (!first) { System.out.print(" , "); }
      first = false;
      System.out.print(value);
    }
    System.out.println("}");
  }

  public <T extends Comparable> void printAll(Bag<T> bag)
  {
    boolean first = true;
    System.out.print("{");
    Iterator<T> allIterator = bag.allOccurrencesIterator();
    while (allIterator.hasNext())
    {
      if (!first) { System.out.print(" , "); }
      first = false;
      System.out.print(allIterator.next());
    }
    System.out.println("}");
  }



  public void go()
  {
//    factory.setBagClass("ArrayBag");
//    factory.setBagClass("MapBag");
    factory.setBagClass("LinkedListBag");

//    factoryInt.setBagClass("MapBag");

    try
    {
      bagLoader.loadBag(factory);

      Bag<String> bag1;
      Bag<String> bag2;
      Bag<String> bag3;
      Bag<Integer> bag7;

      bag1 = factory.getBag();
      bag1.add("abc");
      bag1.add("def");
      bag1.add("hij");
      bag1.addWithOccurrences("xyz",2);
      System.out.print("bag1 all unique:             ");
      print(bag1);
      System.out.print("bag1 all:                    ");
      printAll(bag1);
      System.out.println(bag1.toString());
      bagList.add(bag1);

      bag2 = factory.getBag();
      bag2.add("def");
      bag2.add("def");
      bag2.add("def");
      bag2.add("klm");
      System.out.print("bag2 all unique:             ");
      print(bag2);
      System.out.print("bag2 all:                    ");
      printAll(bag2);
      System.out.println(bag2.toString());
      bagList.add(bag2);

      bag3 = factory.getBag();
      bag3.addWithOccurrences("xyz", 5);
      bag3.add("opq");
      bag3.addWithOccurrences("123", 3);
//      bag3.removeAllCopies();
      System.out.print("bag3 all unique:             ");
      print(bag3);
      System.out.print("bag3 all:                    ");
      printAll(bag3);
      System.out.println(bag3.toString());
      bagList.add(bag3);

      System.out.print("createMergedAllOccurrences:  ");
      Bag<String> bag4 = bag1.createMergedAllOccurrences(bag3);
//      bag4.removeAllCopies();
      printAll(bag4);
      System.out.println(bag4.toString());
      bagList.add(bag4);

      System.out.print("createMergedAllUnique:       ");
      Bag<String> bag5 = bag1.createMergedAllUnique(bag3);
      print(bag5);
      System.out.println(bag5.toString());
      bagList.add(bag5);

//      Bag<String> bag6 = bag4.subtract(bag1); //bag6 = bag4 \ bag1  **should equal bag3
//      System.out.print("bag6 all unique:             ");
//      print(bag6);
//      System.out.print("bag6 all:                    ");
//      printAll(bag6);
//      System.out.println(bag3.toString());
//      bagList.add(bag6);

      bag7 = factory.getBag();
      bag7.addWithOccurrences(123,3);
      bag7.addWithOccurrences(456,2);
      bag7.addWithOccurrences(789,1);
      System.out.print("bag7 all unique:             ");
      print(bag7);
      System.out.print("bag7 all:                    ");
      printAll(bag7);
      bagList.add(bag7);

      bagSaver.saveBag();
    }
    catch (BagException e)
    {
      System.out.println("====> Bag Exception thrown...");
    }
  }

  public static void main(String[] args)
  {
    new Main().go();
  }
}