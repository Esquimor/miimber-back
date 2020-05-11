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
@Table(name="comments")
@Getter @Setter
public class CommentSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "comment", columnDefinition="TEXT", length = 2000, nullable = false)
	private String comment;

	@Column(name = "dateComment", nullable = false)
	private OffsetDateTime dateComment;
	
	@ManyToOne
	private CommentSession commentParent;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private Session session;
}
