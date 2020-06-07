package com.szwadronwilkowalfa.statystyki.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CANCER_RECORD")
@Table(name = "CANCER_RECORD", indexes = {
        @Index(name = "CANCER_RECORD_IDX1", columnList = "id"),
        @Index(name = "CANCER_RECORD_IDX2", columnList = "teryt")
})
public class CancerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    int rok;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "teryt", updatable = false, nullable = false)
    private Powiat powiat;
    private String plec;
    private String icd10;
    private long liczba;
}
