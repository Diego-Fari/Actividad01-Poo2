package models;

public class Guest {
    private static int counter = 0;
    private int id;
    private String name;
    private String email;
    private String phone;

    public Guest(String name, String email, String phone) {
        this.id = ++counter;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}