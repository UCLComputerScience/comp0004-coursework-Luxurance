package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {
    private List<Patient> patientList;

    private BufferedReader lineReader;

    public ReadCSV(FileReader reader){
        this.patientList = new ArrayList<>();
        this.lineReader = new BufferedReader(reader);
    }

    public List<Patient> loadPatient() throws IOException {
        String content = lineReader.readLine();
        boolean first = true;
        while(content != null){
            if(first) content = lineReader.readLine(); // skip the title line
            first = false;
            Patient curPatient = new Patient();
            String[] patientDetails = content.split(",");

            curPatient.setId(patientDetails[0]);
            curPatient.setBirthdate(patientDetails[1]);
            curPatient.setDeathdate(patientDetails[2]);
            curPatient.setSsn(patientDetails[3]);
            curPatient.setDrivers(patientDetails[4]);
            curPatient.setPassport(patientDetails[5]);
            curPatient.setPrefix(patientDetails[6]);
            curPatient.setFirst(patientDetails[7]);
            curPatient.setLast(patientDetails[8]);
            curPatient.setSuffix(patientDetails[9]);
            curPatient.setMaiden(patientDetails[10]);
            curPatient.setMarital(patientDetails[11]);
            curPatient.setRace(patientDetails[12]);
            curPatient.setEthnicity(patientDetails[13]);
            curPatient.setGender(patientDetails[14]);
            curPatient.setBirthplace(patientDetails[15]);
            curPatient.setAddress(patientDetails[16]);
            curPatient.setCity(patientDetails[17]);
            curPatient.setState(patientDetails[18]);
            // ensure the last element can be read even if it is empty
            if(patientDetails.length == 20) {
                curPatient.setZip(patientDetails[19]);
            }
            else{
                curPatient.setZip("");
            }
            this.patientList.add(curPatient);
            content = lineReader.readLine();

        }
        lineReader.close();
        return this.patientList;
    }
}
