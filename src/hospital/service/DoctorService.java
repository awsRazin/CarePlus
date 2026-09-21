package hospital.service;

import hospital.model.Doctor;
import hospital.util.*;
import java.util.*;

public class DoctorService {
    public static final String FILE="data/doctors.csv";
    public void add(){Doctor d=new Doctor(IdGenerator.next(FILE,"D"),Input.text("Name: "),Input.text("Specialization: "),Input.text("Phone: "));CsvUtil.append(FILE,d.toCsv());System.out.println("Doctor added. ID: "+d.getId());}
    public List<Doctor> all(){List<Doctor> list=new ArrayList<>();for(String[]r:CsvUtil.read(FILE))if(r.length>=4)list.add(Doctor.from(r));return list;}
    public Doctor find(String id){for(Doctor d:all())if(d.getId().equalsIgnoreCase(id))return d;return null;}
    public boolean exists(String id){return find(id)!=null;}
    public void view(){print(all());}
    public void search(){String term=Input.text("Doctor ID, name, or specialization: ").toLowerCase();List<Doctor>found=new ArrayList<>();for(Doctor d:all())if(d.toString().toLowerCase().contains(term))found.add(d);print(found);}
    public void update(){String id=Input.text("Doctor ID to update: ");Doctor old=find(id);if(old==null){System.out.println("Doctor not found.");return;}Doctor replacement=new Doctor(old.getId(),Input.text("New name: "),Input.text("New specialization: "),Input.text("New phone: "));List<String[]>rows=new ArrayList<>();for(Doctor d:all())rows.add(d.getId().equalsIgnoreCase(id)?replacement.toCsv():d.toCsv());CsvUtil.writeAll(FILE,rows);System.out.println("Doctor updated.");}
    public void delete(){String id=Input.text("Doctor ID to delete: ");List<String[]>rows=new ArrayList<>();boolean found=false;for(Doctor d:all())if(d.getId().equalsIgnoreCase(id))found=true;else rows.add(d.toCsv());CsvUtil.writeAll(FILE,rows);System.out.println(found?"Doctor deleted.":"Doctor not found.");}
    private void print(List<Doctor> list){if(list.isEmpty())System.out.println("No doctor found.");else for(Doctor d:list)System.out.println(d);}
}
