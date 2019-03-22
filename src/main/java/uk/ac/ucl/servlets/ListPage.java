package uk.ac.ucl.servlets;

import uk.ac.ucl.main.Model;
import uk.ac.ucl.main.Patient;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/list_page")
public class ListPage extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        try {
            // Code to use the model to process something would go here.
            Model model = new Model();
            model.readCSVFile("/Users/Lance 1 2/IdeaProjects/patient_data_web/patient_data/patients100.csv");
            request.setAttribute("patientList", model.getPatientList());

            Patient patient = model.searchById(request.getParameter("patientSelected"));
            if(patient != null) {
                request.setAttribute("patientInfo", model.getPatientInfo(patient));
            }

            String letter = request.getParameter("letterSelected");
            if(letter != null) {
                request.setAttribute("patientList", model.searchByStartLetter(letter));
            }

            // Then forward to JSP.
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/displayList.jsp");
            dispatch.forward(request, response);
        }
        catch (IllegalAccessException e){ }
    }
}