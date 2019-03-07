package uk.ac.ucl;

import java.lang.reflect.Field;

public class JSONFormatter {
    public String singleToJSON(Patient patient) throws IllegalAccessException{
        String jsonForm = "";
        jsonForm += "{\n";
        Field[] fields = patient.getClass().getDeclaredFields();
        for(Field curField : fields){
            jsonForm += "  ";
            curField.setAccessible(true);
            jsonForm += "\"" + curField.getName() + "\" : ";
            String info = curField.get(patient).toString();
            jsonForm += "\"" + info + "\"";
            if(!curField.equals(fields[fields.length-1])) {
                jsonForm += ",\n";
            }
            else{
                jsonForm += "\n";
            }
        }
        jsonForm += "}\n";
        return jsonForm;
    }

}
