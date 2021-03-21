package com.taliano.file_di_testo;
public class Studente {
    private int id ;
    private String gender;
    private String firstName;
    private String lastName;
    private String nationality;
    private String img;

    public int getId() {
        return id;
    }
    public void setId(int ID) {
        this.id = ID;
    }

    public String getGender() { return gender; }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString(){
        // String id = Integer.toString(this.getId());
        return  this.getFirstName() + " - " + this.getLastName() + " - " + this.getNationality();
        //return String.format("studente[id=%d, first=%s, last=%s, nat=%s]", getId(), getFirstName(), getLastName(),  getNationality());
    }

    public Studente(){
        setGender("");
        setImg("");
    }

    public Studente(String firstName, String lastName, String gender, String nationality ){
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setNationality(nationality);
        setImg("");

        if (gender == null){
            setGender("");
        }
    }
}
