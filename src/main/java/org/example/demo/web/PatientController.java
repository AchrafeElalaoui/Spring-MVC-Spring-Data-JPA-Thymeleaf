package org.example.demo.web;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.demo.entities.Patient;
import org.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "0") int page ,
                        @RequestParam(name="size" ,defaultValue = "4") int size,
                        @RequestParam(name="keyword",defaultValue = "") String kw){

            Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(page, size));

            model.addAttribute("listPatients", pagePatients.getContent());
            model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("keyword", kw);
            return "patients";
        }
    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id,String keyword, int page){
            patientRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;

    }

    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatients(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping("/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formPatients";
        }

        patientRepository.save(patient);
        return "redirect:/admin/formPatients";
    }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(Model model, @RequestParam(name = "id")Long id){
        Patient p = patientRepository.findById(id).orElse(null);
        if(p == null){
            throw new RuntimeException("Patient introuvable");
        }
        model.addAttribute("patient", p);
        return "editPatient";
    }
    }