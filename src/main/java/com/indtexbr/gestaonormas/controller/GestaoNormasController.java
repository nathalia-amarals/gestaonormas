package com.indtexbr.gestaonormas.controller;

import com.indtexbr.gestaonormas.model.Norma;
import com.indtexbr.gestaonormas.model.Planejamento;
import com.indtexbr.gestaonormas.repository.NormaRepository;
import com.indtexbr.gestaonormas.repository.PlanejamentoRepository;
import com.indtexbr.gestaonormas.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("gestaonormas")
public class GestaoNormasController {

    @Autowired
    private NormaRepository normaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PlanejamentoRepository planejamentoRepository;

    @GetMapping("norma/{id}")
    public Norma getNorma(@PathVariable ("id") Long id){
        Optional<Norma> opNorma = normaRepository.findById(id);

        if(opNorma.get() != null)
            return opNorma.get(); //lança nullpointer exception?

        return null;
    }

    @PostMapping("norma")
    public ResponseEntity criarNorma(@RequestParam ("file") MultipartFile file,
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
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("norma")
    public ResponseEntity atualizaNorma(@RequestParam ("id") Long id,
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
        if(norma.isPresent()){
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
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(id,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("norma/{id}")
    public ResponseEntity deletaNorma(@PathVariable("id")Long id){
        Optional<Norma> normaCadastrada = normaRepository.findById(id);

        if(normaCadastrada.isPresent())
        {
            normaRepository.deleteById(id);
            return new ResponseEntity(normaCadastrada,HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("planeja/{id}")
    public ResponseEntity getPlanejamento(@PathVariable ("id") Long id){
        Optional<Planejamento> optionalPlanejamento = planejamentoRepository.findById(id);

        if(optionalPlanejamento.isPresent())
        {
            return new ResponseEntity(optionalPlanejamento.get(), HttpStatus.OK); //lança nullpointer exception?
        }

        return new ResponseEntity(id, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("planeja")
    public ResponseEntity criarPlanejamento(@RequestBody Planejamento planejamento) throws IOException {
        return new ResponseEntity(planejamentoRepository.save(planejamento), HttpStatus.CREATED);
    }

    @PutMapping("planeja")
    public ResponseEntity atualizaPlanejamento(@RequestBody Planejamento planejamento) throws IOException {
        Optional<Planejamento> planejamentoCadastrado = planejamentoRepository.findById(planejamento.getId());

        if(planejamentoCadastrado.isPresent())
            return new ResponseEntity(planejamentoRepository.save(planejamento), HttpStatus.OK);

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("planeja/{id}")
    public ResponseEntity deletaPlanejamento(@PathVariable("id")Long id){
        Optional<Planejamento> planejamentoCadastrado = planejamentoRepository.findById(id);

        if(planejamentoCadastrado.isPresent())
        {
            try {
                planejamentoRepository.deleteById(id);
                return new ResponseEntity(planejamentoCadastrado, HttpStatus.OK);
            } catch (Exception e){
                log.info("Exception: " + e);
                return new ResponseEntity(planejamentoCadastrado, HttpStatus.OK);
            }
        }

        return new ResponseEntity(id,HttpStatus.BAD_REQUEST);
    }
}
