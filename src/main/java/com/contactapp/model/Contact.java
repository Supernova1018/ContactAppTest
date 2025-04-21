package com.contactapp.model;

public class Contact {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String cedula;

    public Contact() {}

    public Contact(int id, String name, String email, String phone,String cedula) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cedula = cedula;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cedula='" + cedula + '\'' +
                '}';
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    // Getter y Setter
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

}

