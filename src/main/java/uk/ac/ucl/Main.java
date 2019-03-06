package uk.ac.ucl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("/Users/Lance 1 2/IdeaProjects/cwBag/patient_data/patient_data/patients100.csv");
        ReadCSV loader = new ReadCSV(in);
        List<Patient> patientList = loader.loadPatient();
        System.out.println(patientList.size());
        for(Patient curPatient : patientList){
            System.out.println(curPatient.getId());
            System.out.println(curPatient.getZip());
        }
    }
}
