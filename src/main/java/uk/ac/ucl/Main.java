package uk.ac.ucl;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        FileReader in = new FileReader("/Users/Lance 1 2/IdeaProjects/cwBag/patient_data/patient_data/patients100.csv");
        ReadCSV loader = new ReadCSV(in);
        List<Patient> patientList = loader.loadPatient();
//        System.out.println(patientList.size());
//        for(Patient curPatient : patientList){
//            System.out.println(curPatient.getId());
//            System.out.println(curPatient.getZip());
//        }
//        Field[] fields = patientList.get(0).getClass().getDeclaredFields();
//        for(Field curField : fields){
//            curField.setAccessible(true);
//            System.out.print(curField.getName()+":");
//            Object value = curField.get(patientList.get(0));
//            System.out.println(value.toString());
//        }
        JSONFormatter formatter = new JSONFormatter();
        System.out.println(formatter.listToJSON(patientList.subList(1,5)));
    }
}
