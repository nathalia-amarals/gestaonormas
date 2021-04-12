package com.indtexbr.gestaonormas.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Planejamento {
    @Id
    @GeneratedValue
    private Long id;
    private String nomeDoPlan;
    private String planoDeAcao;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="etapa_data", joinColumns=@JoinColumn(name="planejamento_id",referencedColumnName = "id"))
    private Map<String,String> etapaData = new HashMap<String,String>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoPlan() {
        return nomeDoPlan;
    }

    public void setNomeDoPlan(String nomeDoPlan) {
        this.nomeDoPlan = nomeDoPlan;
    }

    public String getPlanoDeAcao() {
        return planoDeAcao;
    }

    public void setPlanoDeAcao(String planoDeAcao) {
        this.planoDeAcao = planoDeAcao;
    }

    public Map<String, String> getEtapaData() {
        return etapaData;
    }

    public void setEtapaData(Map<String, String> etapaData) {
        this.etapaData = etapaData;
    }
}
