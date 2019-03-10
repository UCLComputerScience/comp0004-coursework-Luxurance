package uk.ac.ucl;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Model {
    private List<Patient> patientList;

    private FileReader reader;

    private FileWriter writer;

    private ReadCSV readCSV;

    private JSONFormatter jsonFormatter;

    public Model(){
        this.patientList = null;
        this.reader = null;
        this.writer = null;
        this.readCSV = new ReadCSV();
        this.jsonFormatter = new JSONFormatter();
    }

    public void readFile(String path) throws IOException {
        reader = new FileReader(path);
        patientList = readCSV.loadPatient(reader);
        reader.close();
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

    public void writeFile(String path) throws IOException, IllegalAccessException{
        writer = new FileWriter(path,true);
        String jsonForm = getAllJSON();
        for(char c : jsonForm.toCharArray()){
            writer.append(c);
        }
        writer.close();
    }

    public Patient getSinglePatientFromJSON(String jsonForm) throws IllegalAccessException{
        return jsonFormatter.jsonToPatient(jsonForm);
    }

    public List<Patient> getPatientListFromJSON(String jsonForm) throws IllegalAccessException{
        return jsonFormatter.jsonToPatientList(jsonForm);
    }
}
