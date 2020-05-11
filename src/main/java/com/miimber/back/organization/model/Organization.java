package com.miimber.back.organization.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.miimber.back.forum.model.CategoryForum;
import com.miimber.back.organization.model.enums.StateOrganizationEnum;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.model.TypeSession;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="organizations")
@Getter @Setter
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(name = "stripe")
	private String stripe;
	
	@Column(name = "stripeEnd")
	private Timestamp stripeEnd;
	
	@Column(name = "state")
	private StateOrganizationEnum state;
	
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE)
    private List<Member> members;
	
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE)
    private List<TypeSession> typeSessions;
	
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE)
    private List<Session> sessions;
	
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE)
    private List<CategoryForum> categoriesForum;
	
	public Organization() {
		this.name = "";
		this.members = new ArrayList<Member>();
		this.typeSessions = new ArrayList<TypeSession>();
		this.sessions = new ArrayList<Session>();
		this.categoriesForum = new ArrayList<CategoryForum>();
		this.state = StateOrganizationEnum.ACTIVE;
	}
	
	public Organization(String name) {
		this.name = name;
		this.members = new ArrayList<Member>();
		this.typeSessions = new ArrayList<TypeSession>();
		this.sessions = new ArrayList<Session>();
		this.categoriesForum = new ArrayList<CategoryForum>();
		this.state = StateOrganizationEnum.ACTIVE;
	}

	public void addMember(Member newMember) {
		this.members.add(newMember);
	}
	
	public Boolean isActif() {
		return this.state == StateOrganizationEnum.ACTIVE;
	}
}
