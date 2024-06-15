package org.example.backend.review;


import jakarta.persistence.*;
import org.example.backend.medici.Medici;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_review;

    private Long id_consultatie;

    public void setId_consultatie(Long id){id_consultatie=id;}

    public Long getId_consultatie(){return id_consultatie;}

    private int rating;
    private String comment;

    private LocalDateTime Data_Ora;

    public LocalDateTime getData_Ora() {
        return Data_Ora;
    }

    public void setData_Ora(LocalDateTime data_Ora) {
        this.Data_Ora = data_Ora;
    }

    @ManyToOne
    @JoinColumn(name = "id_medic")
    private Medici medic;

    // Getters and setters


    public Long getId() {
        return id_review;
    }

    public void setId(Long id) {
        this.id_review = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Medici getMedic() {
        return medic;
    }

    public void setMedic(Medici medic) {
        this.medic = medic;
    }
}

