package hospital.service;

import hospital.util.CsvUtil;

public class ReportService {
    private final PatientService patients;private final DoctorService doctors;private final AppointmentService appointments;private final BedService beds;private final BillingService bills;private final AccountingService accounting;
    public ReportService(PatientService p,DoctorService d,AppointmentService a,BedService b,BillingService bi,AccountingService ac){patients=p;doctors=d;appointments=a;beds=b;bills=bi;accounting=ac;}
    public void show(){int occupied=0,available=0;for(String[]r:CsvUtil.read("data/beds.csv"))if(r.length>=4){if("Occupied".equalsIgnoreCase(r[3]))occupied++;if("Available".equalsIgnoreCase(r[3]))available++;}double revenue=accounting.totalRevenue(),expenses=accounting.totalExpenses();System.out.println("\n========== HOSPITAL REPORT ==========");System.out.println("Total Patients       : "+patients.all().size());System.out.println("Total Doctors        : "+doctors.all().size());System.out.println("Total Appointments   : "+appointments.all().size());System.out.println("Occupied Beds        : "+occupied);System.out.println("Available Beds       : "+available);System.out.printf("Total Revenue        : %.2f%nTotal Expenses       : %.2f%nNet Revenue          : %.2f%nOutstanding Bills    : %.2f%n",revenue,expenses,revenue-expenses,bills.totalDue());System.out.println("=====================================");}
}
