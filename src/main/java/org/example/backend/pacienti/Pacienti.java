package org.example.backend.pacienti;



import jakarta.persistence.*;
import org.example.backend.consultatii.Consultatii;
import org.example.backend.programari_viitoare.Programari_Viitoare;
import org.example.backend.rezultate.Rezultate;


import java.util.List;

@Entity
public class Pacienti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pacient;


    private String Nume;

    private String Prenume;

    private Long CNP;

    private String Email;

    private String password;

    private int varsta;

    private String telefon;

    public void setTelefon(String tlf){
        telefon=tlf;
    }

    public String getTelefon(){
        return telefon;
    }

    @OneToMany(mappedBy = "pacienti")
    private List<Consultatii> consultatiiList;


    @OneToMany(mappedBy = "pacienti")
    private List<Programari_Viitoare> programariViitoareList;

    @OneToMany(mappedBy = "pacienti")
    private List<Rezultate> rezultateList;

    public Pacienti get(){return this;}

    public int getVarsta(){
        return varsta;
    }

    public void setVarsta(int vr){
        varsta=vr;
    }


    public Long getId_Pacient() {
        return id_pacient;
    }

    public void setId_Pacient(Long id_Pacienti) {
        id_pacient = id_Pacienti;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getPrenume() {
        return Prenume;
    }

    public void setPrenume(String prenume) {
        Prenume = prenume;
    }

    public Long getCNP() {
        return CNP;
    }

    public void setCNP(Long CNP) {
        this.CNP = CNP;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

