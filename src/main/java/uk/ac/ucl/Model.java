package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Patient> patientList;

    private FileReader reader;

    private FileWriter writer;

    private ReadCSV readCSV;

    private WriteCSV writeCSV;

    private JSONFormatter jsonFormatter;

    public Model(){
        this.patientList = null;
        this.reader = null;
        this.writer = null;
        this.readCSV = new ReadCSV();
        this.writeCSV = new WriteCSV();
        this.jsonFormatter = new JSONFormatter();
    }

    public void readCSVFile(String path) throws IOException {
        reader = new FileReader(path);
        patientList = readCSV.loadPatient(reader);
        reader.close();
    }

    public void writeCSVFile(String path, List<Patient> patientList) throws IOException, IllegalAccessException{
        writeCSV.setPatientList(patientList);
        writeCSV.savePatient(new FileWriter(path));
    }

    public void writeJSONFile(String path) throws IOException, IllegalAccessException{
        writer = new FileWriter(path); //set to true to append
        String jsonForm = getAllJSON();
        for(char c : jsonForm.toCharArray()){
            writer.append(c);
        }
        writer.close();
    }

    public void readJSONFile(String path) throws IOException, IllegalAccessException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String content = bufferedReader.readLine();
        String jsonForm = "";
        while(content!=null){
            jsonForm += content + "\n";
            content = bufferedReader.readLine();
        }
        patientList = getPatientListFromJSON(jsonForm);
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



    public Patient getSinglePatientFromJSON(String jsonForm) throws IllegalAccessException{
        return jsonFormatter.jsonToPatient(jsonForm);
    }

    public List<Patient> getPatientListFromJSON(String jsonForm) throws IllegalAccessException{
        return jsonFormatter.jsonToPatientList(jsonForm);
    }

    public List<String> getPatientNamesList(){
        List<String> nameList = new ArrayList<>();
        for(Patient curPatient : patientList){
            nameList.add((curPatient.getFirst()+" "+curPatient.getLast()).replaceAll("[0-9]",""));
        }
        return nameList;
    }


}
