package hospital.service;

import hospital.util.*;
import java.util.*;

public class PrescriptionService extends TableService {
    private final PatientService patients;private final DoctorService doctors;private final PharmacyService pharmacy;private final MedicalRecordService records;
    public PrescriptionService(PatientService p,DoctorService d,PharmacyService ph,MedicalRecordService r){super("data/prescriptions.csv","PR","Prescription","Patient ID","Doctor ID","Date","Medicine ID","Medicine","Dosage","Instructions");patients=p;doctors=d;pharmacy=ph;records=r;}
    /** Lets a doctor create one health report and one or more prescription lines in the same consultation. */
    public void makePrescriptionAndHealthReport(){
        String patient=Input.text("Patient ID: "),doctor=Input.text("Doctor ID: ");
        if(!patients.exists(patient)||!doctors.exists(doctor)){System.out.println("Patient or doctor ID does not exist.");return;}
        String date=Input.text("Date (YYYY-MM-DD): ");
        String diagnosis=Input.text("Diagnosis: ");
        String notes=Input.text("Health report / clinical notes: ");
        int count;
        do {count=Input.number("Number of medicines: ");if(count<1)System.out.println("Enter at least one medicine.");} while(count<1);
        List<String[]> medicines=new ArrayList<>();
        while(medicines.size()<count){
            String medicineId=Input.text("Medicine ID ("+(medicines.size()+1)+" of "+count+"): ");
            String[] medicine=pharmacy.findMedicine(medicineId);
            if(medicine==null){System.out.println("Medicine not found. Use Pharmacy Management to view or search the medicine list.");continue;}
            medicines.add(new String[]{medicine[0],medicine[1],Input.text("Dosage: "),Input.text("Instructions: ")});
        }
        StringBuilder treatment=new StringBuilder();
        for(String[] medicine:medicines){if(treatment.length()>0)treatment.append("; ");treatment.append(medicine[1]).append(" (").append(medicine[2]).append(")");}
        String reportId=records.createReport(patient,doctor,date,diagnosis,treatment.toString(),notes);
        for(String[] medicine:medicines) CsvUtil.append(file,new String[]{IdGenerator.next(file,prefix),patient,doctor,date,medicine[0],medicine[1],medicine[2],medicine[3]});
        System.out.println("Prescription saved with "+medicines.size()+" medicine(s). Health report ID: "+reportId);
    }
    @Override public void add(){makePrescriptionAndHealthReport();}
}
