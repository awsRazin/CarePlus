package hospital.service;

import hospital.util.*;
import java.util.*;

public class BillingService extends TableService {
    private final PatientService patients;
    public BillingService(PatientService p){super("data/bills.csv","BL","Bill","Patient ID","Date","Description","Amount","Paid","Status");patients=p;}
    @Override public void add(){String patient=Input.text("Patient ID: ");if(!patients.exists(patient)){System.out.println("Patient not found.");return;}double amount=Input.amount("Amount: "),paid=Input.amount("Paid amount: ");if(paid>amount||amount<0||paid<0){System.out.println("Invalid payment values.");return;}String status=paid==0?"Unpaid":paid==amount?"Paid":"Partial";String[]r={IdGenerator.next(file,prefix),patient,Input.text("Date (YYYY-MM-DD): "),Input.text("Description: "),String.valueOf(amount),String.valueOf(paid),status};CsvUtil.append(file,r);System.out.println("Bill created. ID: "+r[0]);}
    public void receivePayment(){String id=Input.text("Bill ID: ");double payment=Input.amount("Payment amount: ");List<String[]>updated=new ArrayList<>();boolean done=false;for(String[]r:rows()){if(r.length>=7&&r[0].equalsIgnoreCase(id)){double amount=Double.parseDouble(r[4]),paid=Double.parseDouble(r[5]);if(payment<=0||paid+payment>amount){System.out.println("Payment must be positive and no more than due.");return;}paid+=payment;r[5]=String.valueOf(paid);r[6]=paid==amount?"Paid":"Partial";done=true;}updated.add(r);}if(done)CsvUtil.writeAll(file,updated);System.out.println(done?"Payment recorded.":"Bill not found.");}
    public double totalDue(){double due=0;for(String[]r:rows())if(r.length>=6)try{due+=Double.parseDouble(r[4])-Double.parseDouble(r[5]);}catch(NumberFormatException ignored){}return due;}
}
