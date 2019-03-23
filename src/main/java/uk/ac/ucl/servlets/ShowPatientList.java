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
        try {
            // Code to use the model to process something would go here.
            String url = request.getRequestURI();
            System.out.println(url);
            String[] urlEle = url.split("/");
            System.out.println(urlEle.length);
            if(urlEle.length == 2){
                initialise(request,response);
            }

            String letter = request.getParameter("letterSelected");
            if(letter != null) {
                patientList = model.searchByStartLetter(letter);
            }

            Patient patient = model.searchById(request.getParameter("patientSelected"));
            if(patient != null) {
                patientInfo = model.getPatientInfo(patient);
            }

            if(request.getParameter("lastName") != null) {
                patientList = model.searchPatient(request.getParameter("lastName"));
            }

            request.setAttribute("patientList",patientList);

            request.setAttribute("patientInfo",patientInfo);

            // Then forward to JSP.
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/displayList.jsp");
            dispatch.forward(request, response);
        }
        catch (IllegalAccessException e){ }
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
}