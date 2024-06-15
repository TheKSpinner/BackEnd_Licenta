package org.example.backend.servicii;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.example.backend.programari_viitoare.Programari_Viitoare;
import org.example.backend.rezultate.Rezultate;

import java.util.List;

@Entity
public class Servicii {
    @Id
    private Long Id_Serviciu;
    private String Nume_Serviciu;
    private Double Pret;
    private String Tip_Serviciu;

    private String domeniu;

    public String getDomeniu(){return domeniu;}
    public void setDomeniu(String dom){domeniu=dom;}

    @ManyToMany(mappedBy="serviciiList")
    @JsonIgnore
    private List<Programari_Viitoare> programariViitoareList;

    public Long getId_Servicii() {
        return Id_Serviciu;
    }

    public void setId_Servicii(Long id_Servicii) {
        Id_Serviciu = id_Servicii;
    }

    public String getNume_Serviciu() {
        return Nume_Serviciu;
    }

    public void setNume_Serviciu(String nume_Serviciu) {
        Nume_Serviciu = nume_Serviciu;
    }

    public Double getPret() {
        return Pret;
    }

    public void setPret(Double pret) {
        Pret = pret;
    }

    public String getTip_Serviciu() {
        return Tip_Serviciu;
    }

    public void setTip_Serviciu(String tip_Serviciu) {
        Tip_Serviciu = tip_Serviciu;
    }

    public List<Programari_Viitoare> getProgramariViitoareList() {
        return programariViitoareList;
    }

    public void setProgramariViitoareList(List<Programari_Viitoare> programariViitoareList) {
        this.programariViitoareList = programariViitoareList;
    }

}
