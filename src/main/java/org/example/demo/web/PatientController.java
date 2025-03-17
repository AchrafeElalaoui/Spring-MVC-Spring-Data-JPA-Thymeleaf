package org.example.demo.web;


import lombok.AllArgsConstructor;
import org.example.demo.entities.Patient;
import org.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name="page",defaultValue = "0") int page , @RequestParam(name="size" ,defaultValue = "4") int size) {

            Page<Patient> pagepatients = patientRepository.findAll(PageRequest.of(page, size));

            model.addAttribute("listPatients", pagepatients.getContent());
            model.addAttribute("pages", new int[pagepatients.getTotalPages()]);
            model.addAttribute("currentPage", page);
            return "patients";
        }
    }