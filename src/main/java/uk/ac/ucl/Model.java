package uk.ac.ucl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Model {
    private List<Patient> patientList;

    private FileReader reader;

    private ReadCSV readCSV;

    private JSONFormatter jsonFormatter;

    public Model(){
        this.patientList = null;
        this.reader = null;
        this.readCSV = new ReadCSV();
        this.jsonFormatter = new JSONFormatter();
    }

    public void readFile(String path) throws IOException {
        reader = new FileReader(path);
        patientList = readCSV.loadPatient(reader);
    }

    public List<Patient> getPatientList(){
        return this.patientList;
    }

    public String getSingleJSON(Patient patient) throws IllegalAccessException{
        return jsonFormatter.singleToJSON(patient);
    }

    public String getAllJSON() throws IllegalAccessException{
        return jsonFormatter.listToJSON(this.patientList);
    }
}
