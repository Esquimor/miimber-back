package com.miimber.back.session.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.miimber.back.organization.model.Organization;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="type_sessions")
@Getter @Setter
public class TypeSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
    
    @ManyToOne
    private Organization organization;
	
	public TypeSession() {
		this.name = "";
	}
}
