package org.example.demo;

import org.example.demo.entities.Patient;
import org.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //add patients
        patientRepository.save(new Patient(null, "Hassan",new Date(), false, 0));
        patientRepository.save(new Patient(null, "mohamed", new Date(), true, 29));
        patientRepository.save(new Patient(null, "khalid",new Date() , true, 19));


    }
}
