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
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
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
        @GetMapping("/delete")
        public String delete(Long id,String keyword, int page){
            patientRepository.deleteById(id);
            return "redirect:/index?page="+page+"&keyword="+keyword;
        }

    @GetMapping("/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping("/save")
    public String save(Model model,Patient patient){
        patientRepository.save(patient);
        return "redirect:/index";
    }
    @GetMapping("/editPatient")
    public String editPatient(Model model, @RequestParam(name = "id")Long id){
        Patient p = patientRepository.findById(id).orElse(null);
        if(p == null){
            throw new RuntimeException("Patient introuvable");
        }
        model.addAttribute("patient", p);
        return "editPatient";
    }
    }