package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Year;
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

    private int getAge(Patient patient){
        int curYear = Year.now().getValue();
        return curYear - Integer.valueOf(patient.getBirthdate().substring(0,4));
    }

    public void readCSVFile(String path) throws IOException,IllegalAccessException {
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

    public List<String> getPatientInfo(Patient patient) throws IllegalAccessException{
        List<String> result = new ArrayList<>();
        Field[] fields = patient.getClass().getDeclaredFields();
        for(Field curField : fields){
            curField.setAccessible(true);
            String fieldName = curField.getName();
            String fieldContent = curField.get(patient).toString();
            if(fieldName.equals("first")||fieldName.equals("last")||fieldName.equals("maiden")){
                fieldContent = fieldContent.replaceAll("[0-9]","");
            }
            result.add(fieldName+" : "+fieldContent);
        }
        return result;
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

    public List<String> getPatientNamesList(List<Patient> patientList){
        List<String> nameList = new ArrayList<>();
        for(Patient curPatient : patientList){
            nameList.add((curPatient.getFirst()+" "+curPatient.getLast()).replaceAll("[0-9]",""));
        }
        return nameList;
    }

    public List<String> getPatientNamesList(){
        return getPatientNamesList(patientList);
    }

    public List<Patient> searchPatient(String keyWord){
        List<Patient> matchedPatients = new ArrayList<>();
        for(Patient curPatient : patientList){
            if(curPatient.getFirst().contains(keyWord) || curPatient.getLast().contains(keyWord) ||
                    curPatient.getId().contains(keyWord) ||
                    (curPatient.getFirst().replaceAll("[0-9]","")+" "+curPatient.getLast()).contains(keyWord)){
                matchedPatients.add(curPatient);
            }
        }
        return matchedPatients;
    }

    public int getMeanAge(List<Patient> patientList){
        int patientNum = patientList.size();
        int ageSum = 0;
        for(Patient curPatient: patientList){
            if(!curPatient.getDeathdate().equals("")){
                patientNum--;
                continue;
            }
            ageSum += getAge(curPatient);
        }
        if(ageSum == 0){
            return 0;
        }
        return ageSum/patientNum;
    }

    public int getYoungest(List<Patient> patientList){
        int minAge = 999; // sufficiently large age that is impossible
        for(Patient curPatient : patientList){
            if(!curPatient.getDeathdate().equals("")){
                continue;
            }
            if(getAge(curPatient) < minAge){
                minAge = getAge(curPatient);
            }
        }
        if(minAge == 999){
            return 0;
        }
        return minAge;
    }

    public int getEldest(List<Patient> patientList){
        int maxAge = -1; // sufficiently small age that is impossible
        for(Patient curPatient : patientList){
            if(!curPatient.getDeathdate().equals("")){
                continue;
            }
            if(getAge(curPatient) > maxAge){
                maxAge = getAge(curPatient);
            }
        }
        if(maxAge == -1){
            return 0;
        }
        return maxAge;
    }


}
