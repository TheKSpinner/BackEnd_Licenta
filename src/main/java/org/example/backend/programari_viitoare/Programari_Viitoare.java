package org.example.backend.programari_viitoare;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.example.backend.servicii.Servicii;
import org.example.backend.medici.Medici;
import org.example.backend.pacienti.Pacienti;

import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Programari_Viitoare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id_Programari;
    private String Domeniul_Medical;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime Date_Ora;

    //Relatiile Many to one
    @ManyToOne
    @JoinColumn(name="id_medic", nullable=false)
    private Medici medici;
    @ManyToOne
    @JoinColumn(name="id_pacient", nullable=false)
    private Pacienti pacienti;

    @ManyToMany
    @JoinTable(
            name = "programari_viitoare_has_servicii",
            joinColumns = @JoinColumn(name = "Programari_Viitoare_Id_Programari"),
            inverseJoinColumns = @JoinColumn(name = "Servicii_Id_Serviciu"))
    private List<Servicii> serviciiList;

    private int online;

    public void setOnline(boolean oln){
        if(oln==true)
            online=1;
        else{
            online=0;
        }
    }

    public int getOnline(){return online;}

    private String googlemeetlink;


    public String getGooglemeetlink() {
        return googlemeetlink;
    }

    public void setGooglemeetlink(String googleMeetLink) {
        this.googlemeetlink = googleMeetLink;
    }

    private String adresa;

    public String getAdresa(){return adresa;}

    public void setAdresa(String adr){adresa=adr;}

    public Long getId_Programari() {
        return Id_Programari;
    }

    public void setId_Programari(Long id_Programari) {
        Id_Programari = id_Programari;
    }

    public String getDomeniul_Medical() {
        return Domeniul_Medical;
    }

    public void setDomeniul_Medical(String domeniul_Medical) {
        Domeniul_Medical = domeniul_Medical;
    }

    public LocalDateTime getDate_Ora() {
        return Date_Ora;
    }

    public void setDate_Ora(LocalDateTime date_Ora) {
        Date_Ora = date_Ora;
    }

    public Medici getMedici() {
        return medici;
    }

    public void setMedici(Medici medici) {
        this.medici = medici;
    }

    public Pacienti getPacienti() {
        return pacienti;
    }

    public void setPacienti(Pacienti pacienti) {
        this.pacienti = pacienti;
    }

    public List<Servicii> getServiciiList() {
        return serviciiList;
    }

    public void setServiciiList(List<Servicii> serviciiList) {
        this.serviciiList = serviciiList;
    }
}



