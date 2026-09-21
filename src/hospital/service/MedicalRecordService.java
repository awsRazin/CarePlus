package hospital.service;

import hospital.util.*;
import java.util.*;

public class MedicalRecordService extends TableService {
    private static final String HISTORY_FILE="data/medical_record_history.csv";
    private final PatientService patients; private final DoctorService doctors;
    public MedicalRecordService(PatientService p,DoctorService d){super("data/medical_records.csv","R","Medical record","Patient ID","Doctor ID","Date","Diagnosis","Treatment","Notes");patients=p;doctors=d;}
    @Override public void add(){
        String patient=Input.text("Patient ID: "),doctor=Input.text("Doctor ID: ");
        if(!patients.exists(patient)||!doctors.exists(doctor)){System.out.println("Patient or doctor ID does not exist.");return;}
        String id=createReport(patient,doctor,Input.text("Date (YYYY-MM-DD): "),Input.text("Diagnosis: "),Input.text("Treatment: "),Input.text("Health report / notes: "));
        System.out.println("Health report saved. ID: "+id);
    }
    /** Creates the current report and its first immutable history snapshot. */
    public String createReport(String patient,String doctor,String date,String diagnosis,String treatment,String notes){
        String id=IdGenerator.next(file,prefix);
        String[] report={id,patient,doctor,date,diagnosis,treatment,notes};
        CsvUtil.append(file,report);
        saveHistory(report,1,"Created");
        return id;
    }
    public void update(){
        String id=Input.text("Health report ID to update: ");
        String[] old=findRecord(id);
        if(old==null){System.out.println("Health report not found.");return;}
        String doctor=Input.text("Doctor ID: ");
        if(!doctors.exists(doctor)){System.out.println("Doctor not found.");return;}
        String[] replacement={old[0],old[1],doctor,Input.text("Date (YYYY-MM-DD): "),Input.text("Diagnosis: "),Input.text("Treatment: "),Input.text("Health report / notes: ")};
        List<String[]> updated=new ArrayList<>();
        for(String[] row:rows()) updated.add(row.length>0&&row[0].equalsIgnoreCase(id)?replacement:row);
        CsvUtil.writeAll(file,updated);
        saveHistory(replacement,nextVersion(id),"Updated");
        System.out.println("Health report updated; the previous version remains in patient history.");
    }
    public void patientHistory(){
        String id=Input.text("Patient ID: ");
        if(!patients.exists(id)){System.out.println("Patient not found.");return;}
        System.out.println("\n--- Health Report History: "+patients.find(id).getName()+" ---");
        List<String[]> found=new ArrayList<>(); Set<String> versionedIds=new HashSet<>();
        for(String[] row:CsvUtil.read(HISTORY_FILE)) if(row.length>=10&&row[4].equalsIgnoreCase(id)){found.add(row);versionedIds.add(row[1].toLowerCase());}
        // Records created before version tracking are still visible once newer reports exist.
        for(String[] row:rows()) if(row.length>=7&&row[1].equalsIgnoreCase(id)&&!versionedIds.contains(row[0].toLowerCase())) found.add(new String[]{"",row[0],"1","Existing",row[1],row[2],row[3],row[4],row[5],row[6]});
        printHistory(found);
    }
    private String[] findRecord(String id){for(String[] row:rows())if(row.length>=7&&row[0].equalsIgnoreCase(id))return row;return null;}
    private int nextVersion(String recordId){int version=0;for(String[] row:CsvUtil.read(HISTORY_FILE))if(row.length>=3&&row[1].equalsIgnoreCase(recordId))try{version=Math.max(version,Integer.parseInt(row[2]));}catch(NumberFormatException ignored){}return version+1;}
    private void saveHistory(String[] report,int version,String action){CsvUtil.append(HISTORY_FILE,new String[]{IdGenerator.next(HISTORY_FILE,"HRH"),report[0],String.valueOf(version),action,report[1],report[2],report[3],report[4],report[5],report[6]});}
    private void printHistory(List<String[]> reports){
        if(reports.isEmpty()){System.out.println("No health report found.");return;}
        for(String[] row:reports) System.out.println("Record: "+row[1]+" | Version: "+row[2]+" ("+row[3]+") | Patient: "+row[4]+" | Doctor: "+row[5]+" | Date: "+row[6]+" | Diagnosis: "+row[7]+" | Treatment: "+row[8]+" | Notes: "+row[9]);
    }
}
