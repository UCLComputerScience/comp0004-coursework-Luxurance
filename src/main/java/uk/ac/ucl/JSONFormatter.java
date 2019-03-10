package uk.ac.ucl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class JSONFormatter {
    private StringBuilder builder;

    public JSONFormatter(){
        this.builder = new StringBuilder();
    }

    private boolean isLast(Object object, Object[] objects){
        return ! object.equals(objects[objects.length-1]);
    }

    private List<Field> arrayToList(Field[] fields){
        List result = new ArrayList();
        for(Field field : fields){
            result.add(field);
        }
        return result;
    }

    private Field findField(String fieldName, List<Field> fieldList){
        for(Field curField : fieldList){
            if(curField.getName().equals(fieldName)){
                return curField;
            }
        }
        return null;
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

    public Patient jsonToPatient(String jsonForm) throws IllegalAccessException{
        Patient patient = new Patient();
        String[] infoLines = jsonForm.split("\n");
        for(String line : infoLines){
            if(line.equals("{") || line.equals("}")){
                continue;
            }
            String twoElemnt = line.replace(":"," ").replace("\""," ").replace(",","");
            StringTokenizer tokenizer = new StringTokenizer(twoElemnt);
            List<Field> fields = arrayToList(patient.getClass().getDeclaredFields());
            Field field = findField(tokenizer.nextToken(),fields);
            field.setAccessible(true);
            if(tokenizer.hasMoreTokens()){
                field.set(patient,tokenizer.nextToken());
                continue;
            }
            field.set(patient,"");
        }
        return patient;
    }
}
