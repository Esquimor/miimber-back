package com.miimber.back.forum.model;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="talks_forum")
@Getter @Setter
public class TalkForum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name= "title", length = 100, nullable = false)
	private String title;
	
	@Column(name = "dateTalk")
	private OffsetDateTime dateTalk;
	
    @ManyToOne
	@NotNull
	private SubjectForum subject;
    
    @ManyToOne
	@NotNull
	private User user;
    
	@OneToMany(mappedBy = "talk", cascade = CascadeType.REMOVE)
    private List<MessageForum> messages;
}
