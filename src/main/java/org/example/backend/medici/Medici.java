package org.example.backend.medici;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.example.backend.consultatii.Consultatii;
import org.example.backend.programari_viitoare.Programari_Viitoare;
import org.example.backend.review.Review;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Entity
@CrossOrigin
public class Medici {
    @Id
    private Long id_medic;
    private String titlu;
    private String Nume;
    private String Prenume;
    private Long CNP;
    private String Domeniul_Medic;
    private String Descriere;
    private String Fotografie_Profil;
    private String Locatie;
    private Long Rating;
    private String Email;
    private String Password;

    private String telefon;

    @OneToMany(mappedBy = "medic", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public double getAverageRating() {
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    @OneToMany(mappedBy = "medici")
    @JsonIgnore
    private List<Consultatii> consultatiiList;

    @OneToMany(mappedBy = "medici")
    @JsonIgnore
    private List<Programari_Viitoare> programariViitoareList;

    private String adresa;



    public String getAdresa(){return adresa;}

    public void setAdresa(String adr){adresa=adr;}

    public void setTelefon(String tlf){
        telefon=tlf;
    }

    public String getTelefon(){
        return telefon;
    }

    public String getTitlu(){return titlu;}

    public void setTitlu(String ttl){titlu=ttl;}

    // Getter and Setter for id_medic
    public Long getId_medic() {
        return id_medic;
    }

    public void setId_medic(Long id_medic) {
        this.id_medic = id_medic;
    }

    // Getter and Setter for Nume
    public String getNume() {
        return Nume;
    }

    public void setNume(String Nume) {
        this.Nume = Nume;
    }

    // Getter and Setter for Prenume
    public String getPrenume() {
        return Prenume;
    }

    public void setPrenume(String Prenume) {
        this.Prenume = Prenume;
    }

    // Getter and Setter for CNP
    public Long getCNP() {
        return CNP;
    }

    public void setCNP(Long CNP) {
        this.CNP = CNP;
    }

    // Getter and Setter for Domeniul_Medic
    public String getDomeniul_Medic() {
        return Domeniul_Medic;
    }

    public void setDomeniul_Medic(String Domeniul_Medic) {
        this.Domeniul_Medic = Domeniul_Medic;
    }

    // Getter and Setter for Descriere
    public String getDescriere() {
        return Descriere;
    }

    public void setDescriere(String Descriere) {
        this.Descriere = Descriere;
    }

    // Getter and Setter for Fotografie_Profil
    public String getFotografie_Profil() {
        return Fotografie_Profil;
    }

    public void setFotografie_Profil(String Fotografie_Profil) {
        this.Fotografie_Profil = Fotografie_Profil;
    }

    // Getter and Setter for Locatie
    public String getLocatie() {
        return Locatie;
    }

    public void setLocatie(String Locatie) {
        this.Locatie = Locatie;
    }

    // Getter and Setter for Rating
    public Long getRating() {
        return Rating;
    }

    public void setRating(Long Rating) {
        this.Rating = Rating;
    }

    // Getter and Setter for Email
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    // Getter and Setter for Password
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    // Getter and Setter for consultatiiList
    public List<Consultatii> getConsultatiiList() {
        return consultatiiList;
    }

    public void setConsultatiiList(List<Consultatii> consultatiiList) {
        this.consultatiiList = consultatiiList;
    }

    // Getter and Setter for programariViitoareList
    public List<Programari_Viitoare> getProgramariViitoareList() {
        return programariViitoareList;
    }

    public void setProgramariViitoareList(List<Programari_Viitoare> programariViitoareList) {
        this.programariViitoareList = programariViitoareList;
    }
}
