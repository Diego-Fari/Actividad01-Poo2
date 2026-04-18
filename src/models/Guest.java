package models;

public class Guest {
    private static int counter = 0;
    private int id;
    private String name;
    private String phone;
    private String gender;
    private String birthDate;
    private String address;
    private boolean acceptedTerms;

    public Guest(String name, String phone, String gender, String birthDate, String address, boolean acceptedTerms) {
        this.id = ++counter;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.acceptedTerms = acceptedTerms;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public boolean isAcceptedTerms() { return acceptedTerms; }

    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}