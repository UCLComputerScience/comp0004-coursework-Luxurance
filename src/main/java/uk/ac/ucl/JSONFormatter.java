package uk.ac.ucl;

import java.lang.reflect.Field;
import java.util.List;

public class JSONFormatter {
    private StringBuilder builder;

    public JSONFormatter(){
        this.builder = new StringBuilder();
    }

    private boolean isLast(Object object, Object[] objects){
        return ! object.equals(objects[objects.length-1]);
    }

    private String decideComma(String jsonForm, Object object, Object[] objects){
        if(isLast(object, objects)) {
            jsonForm += ",\n";
        }
        else{
            jsonForm += "\n";
        }
        return jsonForm;
    }

    public String singleToJSON(Patient patient) throws IllegalAccessException{
        String jsonForm = "{\n";
        Field[] fields = patient.getClass().getDeclaredFields();
        for(Field curField : fields){
            curField.setAccessible(true);
            String info = curField.get(patient).toString();
            jsonForm += "  \"" + curField.getName() + "\" : \"" + info + "\"";
            jsonForm = decideComma(jsonForm, curField, fields);
        }
        return jsonForm + "}";
    }

    public String listToJSON(List<Patient> patientList) throws IllegalAccessException{
        String jsonForm = "[\n";
        for(Patient curPatient : patientList){
            jsonForm += singleToJSON(curPatient);
            jsonForm = decideComma(jsonForm, curPatient, patientList.toArray());
        }
        return jsonForm + "]\n";
    }
}
