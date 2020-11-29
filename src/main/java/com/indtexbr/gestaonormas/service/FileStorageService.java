package com.indtexbr.gestaonormas.service;

import com.indtexbr.gestaonormas.model.Norma;
import com.indtexbr.gestaonormas.repository.NormaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private NormaRepository fileDBRepository;

    public Norma store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Norma name = new Norma(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(name);
    }

    public Norma getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<Norma> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
