package org.example.backend.rezultate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.backend.pacienti.Pacienti;

import java.time.LocalDateTime;

@Entity
public class Rezultate {
    @Id
    private Long Id_Rezultate;
    private LocalDateTime Data_Ora;


    @ManyToOne
    @JoinColumn(name="id_pacient", nullable=false)
    private Pacienti pacienti;

    private String descriere;

    private String Rezultate_PDF;

    public void setDescriere(String desc){
        descriere=desc;
    }

    public String getDescriere(){
        return descriere;
    }

    public Long getId_Rezultate() {
        return Id_Rezultate;
    }

    public void setId_Rezultate(Long id_Rezultate) {
        Id_Rezultate = id_Rezultate;
    }

    public LocalDateTime getData_Ora() {
        return Data_Ora;
    }

    public void setData_Ora(LocalDateTime data_Ora) {
        Data_Ora = data_Ora;
    }

    public Pacienti getPacienti() {
        return pacienti;
    }

    public void setPacienti(Pacienti pacienti) {
        this.pacienti = pacienti;
    }

    public String getRezultate_PDF() {
        return Rezultate_PDF;
    }

    public void setRezultate_PDF(String rezultate_PDF) {
        Rezultate_PDF = rezultate_PDF;
    }

}
