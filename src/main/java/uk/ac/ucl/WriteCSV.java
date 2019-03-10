package uk.ac.ucl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class WriteCSV {
    private List<Patient> patientList;

    private BufferedWriter bufferedWriter;

    public WriteCSV(){
        patientList = null;
        bufferedWriter = null;
    }

    public void savePatient(FileWriter writer) throws IOException, IllegalAccessException{
     bufferedWriter = new BufferedWriter(writer);
     Field[] fields = Patient.class.getDeclaredFields();
     for(Field curField : fields){
         curField.setAccessible(true);
         bufferedWriter.write(curField.getName().toUpperCase()+",");
     }
     bufferedWriter.newLine();
     for(Patient curPatient : patientList){
         for(Field curField : fields){
             curField.setAccessible(true);
             bufferedWriter.write(curField.get(curPatient).toString()+",");
         }
         bufferedWriter.newLine();
     }
     bufferedWriter.close();
    }

    public void setPatientList(List<Patient> patientList){
        this.patientList = patientList;
    }

}
