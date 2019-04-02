package uk.ac.ucl.servlets;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import uk.ac.ucl.main.Model;
import uk.ac.ucl.main.Patient;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

//@WebServlet("/ShowPatientList")
public class ShowPatientList extends HttpServlet
{
    private Model model;

    private List<Patient> patientList = null;

    private List<String> patientInfo = null;

    private int meanAge, youngest, eldest;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        String url = request.getRequestURI();
        String[] urlEle = url.split("/");
        if(urlEle.length == 2 || patientList == null){
            initialise(request,response);
        }
        else {
            switch (urlEle[2]) {
                case "letter":
                    sortByLetter(request, response);
                    break;
                case "name":
                    showInfo(request, response);
                    break;
                case "search":
                    mutipleSearch(request, response);
                    break;
                case "statistics":
                    if (urlEle.length == 3) {
                        showStats(request, response);
                    }
                    if (urlEle[urlEle.length - 1].equals("getNumber")) {
                        getCount(request,response);
                    }
                    request.setAttribute("meanAge",meanAge);
                    request.setAttribute("youngest",youngest);
                    request.setAttribute("eldest",eldest);
                    toStatsPage(request,response);
                    return;
                case "uploadFile":
                    uploadFile(request,response);
                    break;
            }
        }

        request.setAttribute("patientList",patientList);

        request.setAttribute("patientInfo",patientInfo);

        // Then forward to JSP.
        toDisplayList(request,response);
    }


    private void toDisplayList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/displayList.jsp");
        dispatch.forward(request, response);
    }

    private void toStatsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/showStats.jsp");
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
        if(!request.getParameter("name").equals("")) {
            this.patientList = model.searchByName(patientList,request.getParameter("name"));
        }
        if(!request.getParameter("city").equals("")){
            this.patientList = model.searchByCity(patientList,request.getParameter("city"));
        }
    }

    private void showStats(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if(patientList == null){
            return;
        }
        meanAge = model.getMeanAge(patientList);
        youngest = model.getYoungest(patientList);
        eldest = model.getEldest(patientList);
    }

    private void getCount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if(request.getParameter("statsLower") == "" || request.getParameter("statsUpper") == ""){
            return;
        }
        request.setAttribute("patientCount",model.countByAgeRange(patientList,
                Integer.valueOf(request.getParameter("statsLower")),
                Integer.valueOf(request.getParameter("statsUpper"))));
    }

    private void uploadFile(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println(request.getServletContext().getContextPath());
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileList = upload.parseRequest(new ServletRequestContext(request));
            String filePath = "/Users/Lance 1 2/IdeaProjects/patient_data_web/patient_data/temp.csv";
            fileList.get(0).write(new File(filePath));
            model.readCSVFile(filePath);
            patientList = model.getPatientList();
        }
        catch (FileUploadException e1){
            e1.printStackTrace();
        }
        catch (Exception e2){
            e2.printStackTrace();
        }
    }
}