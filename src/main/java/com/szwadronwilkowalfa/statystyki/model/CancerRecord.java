package com.szwadronwilkowalfa.statystyki.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CANCER_RECORD")
public class CancerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    int rok;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teryt")
    private Powiat powiat;
    private String plec;
    private String icd10;
    private long liczba;
}
