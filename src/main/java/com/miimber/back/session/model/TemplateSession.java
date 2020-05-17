package com.miimber.back.session.model;

import java.time.OffsetDateTime;
import java.time.OffsetTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.miimber.back.organization.model.Organization;
import com.miimber.back.session.model.enums.TemplateSessionEnum;
import com.miimber.back.session.model.enums.TemplateSessionStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="template_sessions")
@Getter @Setter
public class TemplateSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "limitUsers", nullable = false)
	private int limit;
	
	@Column(name = "title", length = 255, nullable = false)
	private String title;
	
	@Column(name = "description", columnDefinition="TEXT", length = 5000, nullable = false)
	private String description;
	
	@Column(name = "startHour", columnDefinition = "TIME WITH TIME ZONE", nullable = false)
	private OffsetTime startHour;
	
	@Column(name = "endHour", columnDefinition = "TIME WITH TIME ZONE", nullable = false)
	private OffsetTime endHour;
	
	private TemplateSessionEnum recurrency;
    
	private Integer day;
	
	private TemplateSessionStatusEnum status;
	
    @ManyToOne
    private Organization organization;
    
    @ManyToOne
    private TypeSession typeSession;
}
