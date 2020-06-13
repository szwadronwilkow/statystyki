package com.szwadronwilkowalfa.statystyki.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Person {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long id;
	  private String firstName;
	  private String lastName;
	  private String email;
	  private String password;
}
