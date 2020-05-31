package com.szwadronwilkowalfa.statystyki.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "teryt", updatable = false, nullable = false)
    private Powiat powiat;
    private String plec;
    private String icd10;
    private long liczba;
}
