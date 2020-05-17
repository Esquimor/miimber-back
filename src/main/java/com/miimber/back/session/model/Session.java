package com.miimber.back.session.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
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
	
	@Column(name = "sessionDate", columnDefinition = "DATE", nullable = false)
	private LocalDate sessionDate;
	
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
    
    public OffsetDateTime getStartDate() {
    	OffsetTime start = this.templateSession.getStartHour();
    	return OffsetDateTime.of(this.sessionDate, start.toLocalTime(), start.getOffset());
    }
    
    public OffsetDateTime getEndDate() {
    	OffsetTime end = this.templateSession.getEndHour();
    	return OffsetDateTime.of(this.sessionDate, end.toLocalTime(), end.getOffset());
    }
	
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
