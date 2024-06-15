package org.example.backend.mesagerie;

import jakarta.persistence.Entity;
import org.example.backend.medici.MediciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.Subject;


public class EmailRequest {
    private Long medicId;
    private String subject;
    private String body;

    public void setMedicId(long id){medicId=id;}
    public void setSubject(String sub){subject=sub;}
    public void setBody(String bod){body=bod;}

    public Long getMedicId(){return medicId;}
    public String getSubject(){return subject;}
    public String getBody(){return body;}

}

