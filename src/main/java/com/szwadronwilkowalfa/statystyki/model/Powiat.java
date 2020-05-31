package com.szwadronwilkowalfa.statystyki.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "POWIAT")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Powiat {
    @Id
    @Column(name = "teryt")
    private String teryt;
    private String nazwa;
}
