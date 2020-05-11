package com.miimber.back.session.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="attendees")
@Getter @Setter
public class AttendeeSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Session session;
	
	@ManyToOne
	private User user;

	@Column(name = "dateCheck", nullable = false)
	private OffsetDateTime dateCheck;
}
