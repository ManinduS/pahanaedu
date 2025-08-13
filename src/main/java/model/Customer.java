package model;

public class Customer {
    private int id;
    private String name, surname, phone, address1, address2, province, email;

    public Customer() {}
    public Customer(int id, String name, String surname, String phone, String address1,
                    String address2, String province, String email) {
        this.id=id; this.name=name; this.surname=surname; this.phone=phone;
        this.address1=address1; this.address2=address2; this.province=province; this.email=email;
    }
    // getters & setters
    public int getId(){return id;}              public void setId(int id){this.id=id;}
    public String getName(){return name;}       public void setName(String name){this.name=name;}
    public String getSurname(){return surname;} public void setSurname(String surname){this.surname=surname;}
    public String getPhone(){return phone;}     public void setPhone(String phone){this.phone=phone;}
    public String getAddress1(){return address1;} public void setAddress1(String a){this.address1=a;}
    public String getAddress2(){return address2;} public void setAddress2(String a){this.address2=a;}
    public String getProvince(){return province;} public void setProvince(String p){this.province=p;}
    public String getEmail(){return email;}     public void setEmail(String email){this.email=email;}
}
