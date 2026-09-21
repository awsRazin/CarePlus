package hospital.service;

import hospital.util.*;
import java.util.*;

public class PharmacyService extends TableService {
    public PharmacyService(){super("data/medicines.csv","M","Medicine","Name","Price","Quantity");}
    /** Returns the medicine row (ID, name, price, quantity), or null when the ID is unknown. */
    public String[] findMedicine(String id){
        for(String[] row:rows()) if(row.length>=4&&row[0].equalsIgnoreCase(id)) return row;
        return null;
    }
    public void dispense(){String id=Input.text("Medicine ID: ");int sold=Input.number("Quantity to dispense: ");List<String[]>updated=new ArrayList<>();boolean done=false;for(String[]r:rows()){if(r.length>=4&&r[0].equalsIgnoreCase(id)){try{int stock=Integer.parseInt(r[3]);if(sold<=0||sold>stock){System.out.println("Insufficient stock.");return;}r[3]=String.valueOf(stock-sold);done=true;}catch(NumberFormatException e){System.out.println("Invalid stock data.");return;}}updated.add(r);}if(done)CsvUtil.writeAll(file,updated);System.out.println(done?"Medicine dispensed.":"Medicine not found.");}
    public void updateStock(){String id=Input.text("Medicine ID: ");int quantity=Input.number("New quantity: ");List<String[]>updated=new ArrayList<>();boolean done=false;for(String[]r:rows()){if(r.length>=4&&r[0].equalsIgnoreCase(id)){r[3]=String.valueOf(quantity);done=true;}updated.add(r);}if(done)CsvUtil.writeAll(file,updated);System.out.println(done?"Stock updated.":"Medicine not found.");}
}
