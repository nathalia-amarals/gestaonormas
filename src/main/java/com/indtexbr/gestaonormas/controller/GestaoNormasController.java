package com.indtexbr.gestaonormas.controller;

import com.indtexbr.gestaonormas.model.Norma;
import com.indtexbr.gestaonormas.repository.NormaRepository;
import com.indtexbr.gestaonormas.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("gestaonormas")
public class GestaoNormasController {

    @Autowired
    private NormaRepository normaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    @RequestMapping(path="/norma")
    public Norma getNorma(@RequestParam (value = "id") Long id){
        Optional<Norma> opNorma = normaRepository.findById(id);

        //if(opNorma.get() != null)
            return opNorma.get(); //lança nullpointer exception?

        //return null;
    }

    @PostMapping
    //@RequestMapping(path="/cadastranorma/")
    public String createNorma( @RequestParam ("file") MultipartFile file,
                              @RequestParam ("name") String name,
                              @RequestParam ("obs") String obs) throws IOException {
        Norma norma = new Norma();
        norma.setName(name);
        norma.setObs(obs);
        norma.setData(file.getBytes());

        String message = "";

        try {
            normaRepository.save(norma);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            //return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            return message;
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            return message;
        }
    }


}
