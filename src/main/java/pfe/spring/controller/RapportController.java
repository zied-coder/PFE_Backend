package pfe.spring.controller;


import pfe.spring.entity.Programme;
import pfe.spring.entity.Rapport;
import pfe.spring.service.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Rapport/")
@CrossOrigin("*")
public class RapportController {

    @Autowired
    public RapportService rapportService;



    @PostMapping("/generate")
    public ResponseEntity<String> generateReport(@RequestBody Programme programme) {
        try {
            rapportService.generateReportIfNeeded(programme);
            return ResponseEntity.ok("Report generated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate report: " + e.getMessage());
        }
    }


    @PutMapping("/update/{idRapport}")
    @ResponseBody
    public void updateRapport(@PathVariable ("idRapport") Long idRapport){
        rapportService.updateRapport(idRapport);
    }


    @DeleteMapping("/delete/{idRapport}")
    @ResponseBody
    public void deleteRapport(@PathVariable ("idRapport") Long idRapport){
        rapportService.deleteRapport(idRapport);
    }

    @GetMapping ("/get/{idRapport}")
    @ResponseBody
    public void getRapport(@PathVariable ("idRapport") Long idRapport){
        rapportService.getRapport(idRapport);
    }


    @GetMapping ("/get")
    @ResponseBody
    public List<Rapport> getAllRapport(){
       return rapportService.getAllRapport();}


}
