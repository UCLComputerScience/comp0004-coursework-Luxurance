package uk.ac.ucl.bag;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

public class BagLoader<T extends Comparable> {

    private File file;

    private List<Bag<T>> bagList;

    public BagLoader(File file){
        this.file = file;
    }

    public void loadBag(BagFactory factory) throws BagException{
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while(line != null){
                Bag newBag = factory.getBag();
                StringTokenizer tokenizer = new StringTokenizer(line);
                while(tokenizer.hasMoreTokens()){
                    T value = tokenizer.nextToken().;
                }
            }
        }
        catch(IOException a){
            System.out.println("Loading bag fails");
            System.out.println("Check the data file");
            System.exit(1);
        }
    }
}
