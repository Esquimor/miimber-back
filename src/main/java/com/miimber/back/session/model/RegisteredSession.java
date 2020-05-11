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
@Table(name="registered")
@Getter @Setter
public class RegisteredSession implements Comparable<RegisteredSession> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Session session;
	
	@ManyToOne
	private User user;

	@Column(name = "dateRegistered", nullable = false)
	private OffsetDateTime dateRegistered;

	@Override
	public int compareTo(RegisteredSession o) {
		return dateRegistered.compareTo(o.getDateRegistered());
	}
}
