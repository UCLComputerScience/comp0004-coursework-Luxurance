package uk.ac.ucl.bag;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

public class BagLoader<T extends Comparable> {

    private File file;

    private List<Bag<T>> bagList;

    private ClassHandler classHandler = new ClassHandler();

    public BagLoader(File file,List bagList){
        this.file = file;
        this.bagList = bagList;
    }

    public void loadBag(BagFactory factory) throws BagException{
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while(line != null){
                Bag newBag = factory.getBag();
                StringTokenizer tokenizer = new StringTokenizer(line);
                String classString = "";
                boolean first = true;
                while(tokenizer.hasMoreTokens()){
                    if(first) classString = tokenizer.nextToken();
                    first = false;
//                    System.out.println(tokenizer.nextToken());
                    T value = classHandler.convertType(tokenizer.nextToken(),classString);
                    int occurrences = Integer.valueOf(tokenizer.nextToken());
                    newBag.addWithOccurrences(value,occurrences);
                }
                bagList.add(newBag);
                line = in.readLine();
            }
        }
        catch(IOException a){
            System.out.println("Loading bag fails");
            System.out.println("Check the data file");
            System.exit(1);
        }
    }

//    public List getBagList(){
//        return bagList;
//    }


}
