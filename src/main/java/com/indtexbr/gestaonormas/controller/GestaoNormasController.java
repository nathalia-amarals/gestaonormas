package com.indtexbr.gestaonormas.controller;

import com.indtexbr.gestaonormas.model.Norma;
import com.indtexbr.gestaonormas.repository.NormaRepository;
import com.indtexbr.gestaonormas.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
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
    public String criarNorma(@RequestParam ("file") MultipartFile file,
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

    @PutMapping
    public String atualizaNorma(@RequestParam ("id") Long id,
                                @RequestParam (name = "file", required = false) MultipartFile file,
                                @RequestParam (name = "name", required = false) String name,
                                @RequestParam (name = "obs", required = false) String obs) throws IOException {

        String message = "Norma não pode ser atualizada";

        Norma normaAtualizada = new Norma();
        normaAtualizada.setId(id);
        if(file != null)
        {
            normaAtualizada.setData(file.getBytes());
        }
        normaAtualizada.setName(name);
        normaAtualizada.setObs(obs);

        Optional<Norma> norma = normaRepository.findById(id);
        if(!norma.isEmpty()){
            if(normaAtualizada.getName().equals(""))
            {
                normaAtualizada.setName(norma.get().getName());
            }

            if(normaAtualizada.getData()==null)
            {
                normaAtualizada.setData(norma.get().getData());
            }

            if(normaAtualizada.getObs().equals(""))
            {
                normaAtualizada.setObs(norma.get().getObs());
            }

            normaRepository.save(normaAtualizada);
            message = "Norma Atualizada com sucesso";
        }

        return message;
    }

    @DeleteMapping("{id}")
    public String deletaNorma(@PathVariable("id")Long id){
        normaRepository.deleteById(id);
        return "ok";
    }
}
