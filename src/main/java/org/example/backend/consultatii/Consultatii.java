package org.example.backend.consultatii;
import jakarta.persistence.*;
import org.example.backend.medici.Medici;
import org.example.backend.pacienti.Pacienti;

import java.time.LocalDateTime;
import java.util.Base64;

@Entity
public class Consultatii {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id_Consultatie;

    private String Domeniul_Medical;
    private LocalDateTime Data_Ora;
    @ManyToOne
    @JoinColumn(name = "id_medic", nullable = false)
    private Medici medici;
    @ManyToOne
    @JoinColumn(name = "id_pacient", nullable = false)
    private Pacienti pacienti;


    private String diagnosticPDF;  // Store as String
    private String prescriptiePDF;  // Store as String

    public String getDiagnosticPDF() {
        return diagnosticPDF;
    }

    public void setDiagnosticPDF(String diagnosticPDF) {
        this.diagnosticPDF = diagnosticPDF;
    }

    public String getPrescriptiePDF() {
        return prescriptiePDF;
    }

    public void setPrescriptiePDF(String prescriptiePDF) {
        this.prescriptiePDF = prescriptiePDF;
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
    public Long getId_Consultatie() {
        return Id_Consultatie;
    }

    public void setId_Consultatie(Long id_Consultatie) {
        this.Id_Consultatie = id_Consultatie;
    }

    public String getDomeniul_Medical() {
        return Domeniul_Medical;
    }

    public void setDomeniul_Medical(String domeniul_Medical) {
        this.Domeniul_Medical = domeniul_Medical;
    }

    public LocalDateTime getData_Ora() {
        return Data_Ora;
    }

    public void setData_Ora(LocalDateTime data_Ora) {
        this.Data_Ora = data_Ora;
    }





}

