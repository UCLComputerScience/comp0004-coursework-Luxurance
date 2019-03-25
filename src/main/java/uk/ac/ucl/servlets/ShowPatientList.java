package uk.ac.ucl.servlets;

import uk.ac.ucl.main.Model;
import uk.ac.ucl.main.Patient;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet("/ShowPatientList")
public class ShowPatientList extends HttpServlet
{
    private Model model;

    private List<Patient> patientList = null;

    private List<String> patientInfo = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        // Code to use the model to process something would go here.
        String url = request.getRequestURI();
        System.out.println(url);
        String[] urlEle = url.split("/");
        System.out.println(urlEle.length);
        if(urlEle.length == 2){
            initialise(request,response);
        }
        switch (urlEle[urlEle.length-1]){
            case "letter":
                sortByLetter(request,response);
                break;
            case "name":
                showInfo(request,response);
                break;
            case "search":
                mutipleSearch(request,response);
        }

        request.setAttribute("patientList",patientList);

        request.setAttribute("patientInfo",patientInfo);

        // Then forward to JSP.
        toJSP(request,response);
    }


    private void toJSP(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/displayList.jsp");
        dispatch.forward(request, response);
    }

    private void initialise(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        model = new Model();
        model.readCSVFile("/Users/Lance 1 2/IdeaProjects/patient_data_web/patient_data/patients100.csv");
        patientList = model.getPatientList();
    }

    private void sortByLetter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String letter = request.getParameter("letterSelected");
        if(letter != null) {
            patientList = model.searchByStartLetter(letter);
        }
    }

    private void showInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        try {
            Patient patient = model.searchById(request.getParameter("patientSelected"));
            if (patient != null) {
                patientInfo = model.getPatientInfo(patient);
            }
        }
        catch (IllegalAccessException e){}
    }

    private void searchByLastName(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        if(request.getParameter("lastName").equals("")) {
            patientList = model.searchPatient(request.getParameter("lastName"));
        }
    }

    private void mutipleSearch(HttpServletRequest request, HttpServletResponse response){
        patientList = model.getPatientList();
        if(request.getParameter("gender") != null){
            this.patientList = model.searchByGender(patientList,Character.toString(request.getParameter("gender").charAt(0)));
        }
        if((request.getParameter("lowerAge") != "") && (request.getParameter("upperAge") != "")){
            this.patientList = model.searchByAgeRange(patientList,Integer.valueOf(request.getParameter("upperAge")),
                    Integer.valueOf(request.getParameter("lowerAge")));
        }
        if(request.getParameter("name") != null) {
            this.patientList = model.searchByName(patientList,request.getParameter("name"));
        }
        if(request.getParameter("city") != null){
            this.patientList = model.searchByCity(patientList,request.getParameter("city"));
        }
    }
}