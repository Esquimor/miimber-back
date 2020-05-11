package com.miimber.back.session.model;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.miimber.back.organization.model.Organization;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="sessions")
@Getter @Setter
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id; 
	
	@Column(name = "title", length = 255, nullable = false)
	private String title;
	
	@Column(name = "description", columnDefinition="TEXT", length = 5000, nullable = false)
	private String description;
	
	@Column(name = "startDate", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private OffsetDateTime start;
	
	@Column(name = "endDate", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private OffsetDateTime end;
	
	@Column(name = "limitUsers", nullable = false)
	private int limit;
	
	@OneToMany(mappedBy = "session")
    private List<AttendeeSession> attendees;
	
	@OneToMany(mappedBy = "session")
    private List<RegisteredSession> registereds;
	
	@OneToMany(mappedBy = "session")
    private List<CommentSession> comments;
    
    @ManyToOne
    private Organization organization;
    
    @ManyToOne
    private TemplateSession templateSession;
    
    @ManyToOne
    private TypeSession typeSession;
	
	public void addAttendee(AttendeeSession attendee) {
		this.attendees.add(attendee);
	}
	
	public void removeAttendee(AttendeeSession attendee) {
		this.attendees.remove(attendee);
	}
	
	public void addRegistered(RegisteredSession registered) {
		this.registereds.add(registered);
	}
	
	public void removeRegistered(RegisteredSession registered) {
		this.registereds.remove(registered);
	}
	
	public void addComment(CommentSession comment) {
		this.comments.add(comment);
	}
	
	public void removeComment(CommentSession comment) {
		this.comments.remove(comment);
	}
}
