package com.miimber.back.user.dto;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TemplateAttendeeDTO {
	
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private MemberDTO member;
	private long attendeeId;
	private boolean isRegistered;
	
	public TemplateAttendeeDTO(Member member, long attendeeId, boolean isRegistered) {
		User user = member.getUser();
		this.setId(user.getId());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setEmail(user.getEmail());
		this.setMember(new MemberDTO(member));
		this.setAttendeeId(attendeeId);
		this.setRegistered(isRegistered);
	}
	
	public TemplateAttendeeDTO(User user, long attendeeId, boolean isRegistered) {
		this.setId(user.getId());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setEmail(user.getEmail());
		this.setAttendeeId(attendeeId);
		this.setRegistered(isRegistered);
	}

	@Getter @Setter
	private class MemberDTO {
		
		private long id;
		private RoleEnum role;
		
		public MemberDTO(Member member) {
			this.setId(member.getId());
			this.setRole(member.getRole());
		}
	}
}

