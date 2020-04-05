package com.szwadronwilkowalfa.statystyki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CancerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @JsonProperty("col1")
    int rok;
    @JsonProperty("col2")
    int powiat;
    @JsonProperty("col3")
    String plec;
    @JsonProperty("col4")
    String icd10;
    @JsonProperty("col5")
    long liczba;
}
