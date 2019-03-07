package uk.ac.ucl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        Model model = new Model();
        model.readFile("/Users/Lance 1 2/IdeaProjects/cwBag/patient_data/patient_data/patients100.csv");
        System.out.println(model.getAllJSON());

    }
}
