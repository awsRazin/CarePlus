package hospital.model;

public class Patient {
    private final String id, name, gender, bloodGroup, phone, address;
    private final int age;
    public Patient(String id, String name, int age, String gender, String bloodGroup, String phone, String address) { this.id=id; this.name=name; this.age=age; this.gender=gender; this.bloodGroup=bloodGroup; this.phone=phone; this.address=address; }
    public String getId(){return id;} public String getName(){return name;}
    public String[] toCsv(){return new String[]{id,name,String.valueOf(age),gender,bloodGroup,phone,address};}
    public static Patient from(String[] r){return new Patient(r[0],r[1],Integer.parseInt(r[2]),r[3],r[4],r[5],r[6]);}
    public String toString(){return id+" | "+name+" | Age: "+age+" | "+gender+" | "+bloodGroup+" | "+phone+" | "+address;}
}
