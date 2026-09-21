package hospital.model;

public class Doctor {
    private final String id,name,specialization,phone;
    public Doctor(String id,String name,String specialization,String phone){this.id=id;this.name=name;this.specialization=specialization;this.phone=phone;}
    public String getId(){return id;} public String getName(){return name;}
    public String[] toCsv(){return new String[]{id,name,specialization,phone};}
    public static Doctor from(String[] r){return new Doctor(r[0],r[1],r[2],r[3]);}
    public String toString(){return id+" | "+name+" | "+specialization+" | "+phone;}
}
