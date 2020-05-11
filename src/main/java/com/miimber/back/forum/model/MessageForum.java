package com.miimber.back.forum.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="messages_forum")
@Getter @Setter
public class MessageForum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "message", columnDefinition="TEXT", length = 2000, nullable = false)
	private String message;
	
	@Column(name = "dateMessage")
	private OffsetDateTime dateMessage;
	
    @ManyToOne
	@NotNull
	private TalkForum talk;
    
    @ManyToOne
	@NotNull
	private User user;
}
