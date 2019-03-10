package uk.ac.ucl;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        Model model = new Model();
        model.readFile("/Users/Lance 1 2/IdeaProjects/cwBag/patient_data/patient_data/patients100.csv");
//        System.out.println(model.getAllJSON());
        String jsonForm = model.getAllJSON();
        List<Patient> patients = model.getPatientListFromJSON(jsonForm);
        for(Patient curPatient : patients) {
            System.out.println(curPatient.getId() + " " + curPatient.getZip());
        }
    }
}
