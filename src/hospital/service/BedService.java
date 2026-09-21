package hospital.service;

import hospital.util.*;
import java.util.*;

public class BedService extends TableService {
    private final PatientService patients;
    public BedService(PatientService p){super("data/beds.csv","B","Bed","Ward","Bed number","Status","Patient ID");patients=p;}
    public void assign(){String id=Input.text("Bed ID: "),patient=Input.text("Patient ID: ");if(!patients.exists(patient)){System.out.println("Patient not found.");return;}List<String[]>updated=new ArrayList<>();boolean done=false;for(String[]r:rows()){if(r.length>=5&&r[0].equalsIgnoreCase(id)){if(!"Available".equalsIgnoreCase(r[3])){System.out.println("Bed is not available.");return;}r[3]="Occupied";r[4]=patient;done=true;}updated.add(r);}if(done)CsvUtil.writeAll(file,updated);System.out.println(done?"Bed assigned.":"Bed not found.");}
    public void release(){String id=Input.text("Bed ID: ");List<String[]>updated=new ArrayList<>();boolean done=false;for(String[]r:rows()){if(r.length>=5&&r[0].equalsIgnoreCase(id)){r[3]="Available";r[4]="-";done=true;}updated.add(r);}if(done)CsvUtil.writeAll(file,updated);System.out.println(done?"Bed released.":"Bed not found.");}
    public void available(){List<String[]>found=new ArrayList<>();for(String[]r:rows())if(r.length>=4&&"Available".equalsIgnoreCase(r[3]))found.add(r);print(found);}
}
