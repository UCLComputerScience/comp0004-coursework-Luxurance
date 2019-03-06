package uk.ac.ucl;

public class TestFunction {
    public static void main(String[] args){
        String a = "1,2,3,4,5,,,,,6,";
        String[] b = a.split(",");
        System.out.println(b.length);
        for(String str : b){
            System.out.println(str);
        }
    }
}
