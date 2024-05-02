package pfe.spring.controller;



import pfe.spring.entity.Contribuable;
import pfe.spring.service.ContribuableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Contribuable/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContribuableController {

    @Autowired
    private ContribuableService contribuableService;



    @PostMapping("/save")
    public Contribuable save(
            @RequestBody Contribuable contribuable) {
        return contribuableService.saveContribuable(contribuable);
    }


    @PutMapping("/update/{idContribuable}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateContribuable(@PathVariable("idContribuable") Long idContribuable, @RequestBody Contribuable updatedContribuable) {
        Map<String, String> response = new HashMap<>();
        try {
            contribuableService.updateContribuable(idContribuable, updatedContribuable);
            response.put("message", "Contribuable updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error updating Contribuable");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/delete/{idContribuable}")
    @ResponseBody
    public void deleteContribuable(@PathVariable ("idContribuable") Long idContribuable){
        contribuableService.deleteContribuable(idContribuable);
    }

    @GetMapping ("/get/{idContribuable}")
    @ResponseBody
    public Contribuable getContribuable(@PathVariable ("idContribuable") Long idContribuable){
       return contribuableService.getContribuable(idContribuable);
    }


    @GetMapping ("/get")
    @ResponseBody
    public List<Contribuable> getAllContribuable(){
       return  contribuableService.getAllContribuables();
    }

    @GetMapping("/getContribuableNumber")
    public long getContribuableCount(){
        return contribuableService.getContribuableCount();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Contribuable>> searchContribuable(@RequestParam("query") String query) {
        List<Contribuable> contribuables = contribuableService.searchContribuable(query);
        return new ResponseEntity<>(contribuables, HttpStatus.OK);
    }


}


