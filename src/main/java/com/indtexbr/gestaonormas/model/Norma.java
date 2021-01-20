package com.indtexbr.gestaonormas.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Norma {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String obs;
    private byte[] data;

    public Norma(){

    }

    public Norma(String fileName, String contentType, byte[] bytes) {
        this.name = fileName;
        this.data = bytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
