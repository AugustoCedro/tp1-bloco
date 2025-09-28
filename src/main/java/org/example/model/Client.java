package org.example.model;



public class Client {
    private Integer id;
    private String name;
    private String email;
    private static Integer sequence = 0;

    public Client(String name, String email) {
        sequence++;
        id = sequence;
        this.name = name;
        this.email = email;
    }


    public Client(int id,String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void resetSequence() {
        sequence = 0;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
