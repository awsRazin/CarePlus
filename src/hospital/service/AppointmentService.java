package hospital.service;

import hospital.model.Appointment;
import hospital.util.*;
import java.util.*;

public class AppointmentService {
    private static final String FILE="data/appointments.csv"; private final PatientService patients; private final DoctorService doctors;
    public AppointmentService(PatientService patients,DoctorService doctors){this.patients=patients;this.doctors=doctors;}
    public void schedule(){String patient=Input.text("Patient ID: "),doctor=Input.text("Doctor ID: ");if(!patients.exists(patient)||!doctors.exists(doctor)){System.out.println("Patient or doctor ID does not exist.");return;}Appointment a=new Appointment(IdGenerator.next(FILE,"A"),patient,doctor,Input.text("Date (YYYY-MM-DD): "),Input.text("Time (HH:MM): "),"Scheduled");CsvUtil.append(FILE,a.toCsv());System.out.println("Appointment scheduled. ID: "+a.toCsv()[0]);}
    public List<Appointment> all(){List<Appointment>list=new ArrayList<>();for(String[]r:CsvUtil.read(FILE))if(r.length>=6)list.add(Appointment.from(r));return list;}
    public void view(){print(all());}
    public void complete(){changeStatus("Completed");} public void cancel(){changeStatus("Cancelled");}
    private void changeStatus(String status){String id=Input.text("Appointment ID: ");List<String[]>rows=new ArrayList<>();boolean found=false;for(Appointment a:all()){String[]r=a.toCsv();if(r[0].equalsIgnoreCase(id)){r[5]=status;found=true;}rows.add(r);}CsvUtil.writeAll(FILE,rows);System.out.println(found?"Status updated.":"Appointment not found.");}
    private void print(List<Appointment> list){if(list.isEmpty())System.out.println("No appointments found.");else for(Appointment a:list)System.out.println(a);}
}
