package uk.ac.ucl.bag;

import java.io.*;
import java.util.List;

public class BagSaver<T extends Comparable> {

    private File file;

    private List<Bag<T>> bagList;

    private ClassHandler classHandler = new ClassHandler();

    public BagSaver(File file, List bagList){
        this.file = file;
        this.bagList = bagList;
    }

    public void saveBag(){
        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            for (Bag<T> curBag : bagList) {
                boolean first = true;
                for (T value : curBag) {
                    if(first) {
                        out.write( classHandler.getClassString(value.getClass().toString())+" ");
                    }
                    first = false;
                    out.write(value.toString() + " ");
                    out.write(curBag.countOf(value) + " ");
                }
                out.write("\n");
            }
            out.close();
        }
        catch(IOException a){
            System.out.println("Saving bag fails");
        }
    }
}
