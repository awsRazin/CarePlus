package hospital.service;

import hospital.util.*;
import java.util.*;

public class BloodBankService extends TableService {
    public BloodBankService(){super("data/blood_donors.csv","BD","Blood donor","Name","Blood group","Age","Phone","Address","Available (true/false)");}
    public void findAvailable(){String group=Input.text("Blood group: ");List<String[]>found=new ArrayList<>();for(String[]r:rows())if(r.length>=7&&r[2].equalsIgnoreCase(group)&&Boolean.parseBoolean(r[6]))found.add(r);print(found);}
}
