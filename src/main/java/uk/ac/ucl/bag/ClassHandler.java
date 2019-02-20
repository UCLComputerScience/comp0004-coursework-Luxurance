package uk.ac.ucl.bag;

public class ClassHandler {

    public String getClassString(String classOrigin){
        String classString = classOrigin;
        String result = "";
        for(int i = classString.length() - 1; i >= 0; i--){
            if(Character.toString(classString.charAt(i)).equals(".")){
                break;
            }
            result = classString.charAt(i) + result;
        }
        return result;
    }

    public <T extends Comparable> T convertType(String text, String type){
        if(type.equals("String")) return (T)text;
        if(type.equals("Integer")) return (T)Integer.valueOf(text);
        if(type.equals("Double")) return (T)Double.valueOf(text);
        if(type.equals("Boolean")) return (T)Boolean.valueOf(text);
        if(type.equals("Float")) return (T)Float.valueOf(text);
        if(type.equals("Long")) return (T)Long.valueOf(text);
        return null;
    }

}
