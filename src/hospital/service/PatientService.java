package hospital.service;

import hospital.model.Patient;
import hospital.util.*;
import java.util.*;

public class PatientService {
    public static final String FILE = "data/patients.csv";
    public void register() {
        Patient patient = new Patient(IdGenerator.next(FILE,"P"), Input.text("Name: "), Input.number("Age: "), Input.text("Gender: "), Input.text("Blood group: "), Input.text("Phone: "), Input.text("Address: "));
        CsvUtil.append(FILE, patient.toCsv()); System.out.println("Patient registered. ID: " + patient.getId());
    }
    public List<Patient> all() { List<Patient> list=new ArrayList<>(); for(String[] r:CsvUtil.read(FILE)) if(r.length>=7) try{list.add(Patient.from(r));}catch(Exception ignored){} return list; }
    public Patient find(String id) { for(Patient p:all()) if(p.getId().equalsIgnoreCase(id)) return p; return null; }
    public boolean exists(String id){return find(id)!=null;}
    public void view(){ print(all()); }
    public void search(){ String term=Input.text("Patient ID or name: ").toLowerCase(); List<Patient> found=new ArrayList<>(); for(Patient p:all())if(p.getId().toLowerCase().contains(term)||p.getName().toLowerCase().contains(term))found.add(p); print(found); }
    public void update(){
        String id=Input.text("Patient ID to update: "); Patient old=find(id); if(old==null){System.out.println("Patient not found.");return;}
        Patient replacement=new Patient(old.getId(),Input.text("New name: "),Input.number("New age: "),Input.text("New gender: "),Input.text("New blood group: "),Input.text("New phone: "),Input.text("New address: "));
        List<String[]> rows=new ArrayList<>(); for(Patient p:all())rows.add(p.getId().equalsIgnoreCase(id)?replacement.toCsv():p.toCsv()); CsvUtil.writeAll(FILE,rows); System.out.println("Patient updated.");
    }
    public void delete(){String id=Input.text("Patient ID to delete: "); List<String[]> rows=new ArrayList<>(); boolean removed=false; for(Patient p:all())if(p.getId().equalsIgnoreCase(id))removed=true;else rows.add(p.toCsv()); CsvUtil.writeAll(FILE,rows);System.out.println(removed?"Patient deleted.":"Patient not found.");}
    private void print(List<Patient> list){if(list.isEmpty())System.out.println("No patient found.");else for(Patient p:list)System.out.println(p);}
}
