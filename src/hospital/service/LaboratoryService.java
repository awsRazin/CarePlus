package hospital.service;

import hospital.util.*;
import java.util.*;

public class LaboratoryService {
    private final TableService tests=new TableService("data/lab_tests.csv","T","Lab test","Test name","Price");
    private final TableService results=new TableService("data/lab_results.csv","L","Lab result","Patient ID","Test ID","Date","Result","Status");
    private final PatientService patients;
    public LaboratoryService(PatientService p){patients=p;}
    public void addTest(){tests.add();} public void viewTests(){tests.view();}
    public void addResult(){String patient=Input.text("Patient ID: "),test=Input.text("Test ID: ");if(!patients.exists(patient)||!hasTest(test)){System.out.println("Patient or test ID does not exist.");return;}String[]r={IdGenerator.next("data/lab_results.csv","L"),patient,test,Input.text("Date (YYYY-MM-DD): "),Input.text("Result: "),"Completed"};CsvUtil.append("data/lab_results.csv",r);System.out.println("Lab result saved. ID: "+r[0]);}
    public void viewResults(){results.view();}
    private boolean hasTest(String id){for(String[]r:tests.rows())if(r.length>0&&r[0].equalsIgnoreCase(id))return true;return false;}
}
