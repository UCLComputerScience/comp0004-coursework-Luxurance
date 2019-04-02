package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCSV {
    private BufferedReader lineReader;

    public ReadCSV(){
        this.lineReader = null;
    }

    public List<Patient> loadPatient(FileReader reader) throws IOException, IllegalAccessException {
        List<Patient> patientList = new ArrayList<>();
        lineReader = new BufferedReader(reader);
        String content = lineReader.readLine();
        boolean first = true;
        boolean flag = false;
        while(content != null){
            if(first) content = lineReader.readLine(); // skip the title line
            first = false;
            Patient curPatient = new Patient();
            String[] patientDetails = content.split(",");
            /* ensure the last element can be read even if it is empty */
            if(patientDetails.length == 19) {
                flag = true;
            }
            int i = 0;
            Field[] fields = curPatient.getClass().getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                field.set(curPatient,patientDetails[i]);
                i++;
                if(i == 19){
                    if(flag){
                        curPatient.setZip("");
                        break;
                    }
                }
            }
            patientList.add(curPatient);
            content = lineReader.readLine();

        }
        lineReader.close();
        return patientList;
    }
}
