package pfe.spring.controller;



import org.springframework.web.bind.annotation.*;
import pfe.spring.entity.Programme;
import pfe.spring.service.ProgrammeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pfe.spring.entity.Rapport;
import pfe.spring.entity.Task;

import java.util.List;

@RestController
@RequestMapping("/Programme")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProgrammeController {
    @Autowired
    ProgrammeService programmeService;



    @PostMapping("/save/{idSession}/{idControlleur}/{idSessionContribuable}")
    public Programme createProgramme(@RequestBody Programme programme, @PathVariable Long idSession, @PathVariable Long idControlleur, @PathVariable Long idSessionContribuable, @RequestParam List<String> taskGoals) {
        return programmeService.createProgramme(programme,idSession,idControlleur,idSessionContribuable, taskGoals);
    }

    @GetMapping("/getRapportByProgramme/{idProgramme}")
    public List<Rapport> getReportsByProgramId(@PathVariable Long idProgramme) {
        return programmeService.getRapportsByProgramme(idProgramme);
    }

    @GetMapping("/getProgramme")
    public List<Programme> getProgrammes(){
        return programmeService.getProgrammes();
    }


    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<Programme>> getProgrammesBySession(@PathVariable Long sessionId) {
        List<Programme> programmes = programmeService.getProgrammesBySession(sessionId);
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

    @GetMapping("/getProgrammeNumber")
    public long getSessionCount(){
        return programmeService.getProgrammeCount();
    }

    @GetMapping("/tasks/{idProgramme}")
    public List<Task> getTasksByIdProgramme(@PathVariable Long idProgramme) {
        return programmeService.getTasksByIdProgramme(idProgramme);
    }


    @GetMapping("/getProgrammesByControlleur/{username}")
    public List<Programme> getTasksByControlleur(@PathVariable String username) {
        return programmeService.getProgrammesByControlleur(username);
    }


    @GetMapping("/calculate-progress/{idProgramme}")
    public ResponseEntity<Integer> calculateOverallProgress(@PathVariable("idProgramme") Long idProgramme) {
        int overallProgress = programmeService.updateOverallProgress(idProgramme);
        return ResponseEntity.ok(overallProgress);
    }
}
