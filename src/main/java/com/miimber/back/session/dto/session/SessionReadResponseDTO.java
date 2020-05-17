package com.miimber.back.session.dto.session;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.session.model.CommentSession;
import com.miimber.back.session.model.RegisteredSession;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.model.TemplateSession;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.model.enums.RegisteredEnum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionReadResponseDTO {

	private long id;
	private String title;
	private String description;
	private OffsetDateTime start;
	private OffsetDateTime end;
	private LocalDate sessionDate;
	private OrganizationDTO organization;
	private TypeSessionDTO typeSession;
	private MeDTO me;
	private List<RegisteredDTO> registereds;
	private int limit;
	private List<CommentDTO> comments;
	
	public SessionReadResponseDTO(Session session, Member member, Long userId) {
		TemplateSession templateSession = session.getTemplateSession();
		this.setId(session.getId());
		this.setTitle(templateSession.getTitle());
		this.setDescription(templateSession.getDescription());
		this.start = session.getStartDate();
		this.end = session.getEndDate();
		this.setLimit(templateSession.getLimit());
		this.setOrganization(new OrganizationDTO(session.getOrganization()));
		this.setTypeSession(new TypeSessionDTO(templateSession.getTypeSession()));
		this.setMe(new MeDTO(member, getByUserId(session, userId)));
		if ( member != null && (member.getRole() == RoleEnum.INSTRUCTOR ||
			member.getRole() == RoleEnum.OFFICE_INSTRUCTOR ||
			member.getRole() == RoleEnum.OWNER)) {
		}
		setRegistereds(session);
		this.comments = new ArrayList<CommentDTO>();
		for(CommentSession comment: session.getComments()) {
			this.comments.add(new CommentDTO(comment));
		}
	}
	
	private RegisteredSession getByUserId(Session session, long id) {
		RegisteredSession findRegistered = null;
		for(RegisteredSession registered: session.getRegistereds()) {
			if (registered.getUser().getId() == id) {
				findRegistered = registered;
				break;
			}
		}
		return findRegistered;
	}
	
	private void setRegistereds(Session session) {
		this.registereds = new ArrayList<RegisteredDTO>();
		List<Member> members = session.getOrganization().getMembers();
		List<RegisteredSession> registereds = session.getRegistereds();
		Collections.sort(registereds);
		int limitSession = session.getTemplateSession().getLimit();
		for(int i = 0; i < registereds.size(); i++) {
			boolean isMember = false;
			for(Member member: members) {
				if (member.getUser().getId() == registereds.get(i).getUser().getId()) {
					isMember = true;
					break;
				}
			}
			RegisteredEnum status = RegisteredEnum.TAKEN;
			if (limitSession != 0) {
				status = limitSession > i ? RegisteredEnum.TAKEN : RegisteredEnum.WAITING;
			}
			this.registereds.add(new RegisteredDTO(registereds.get(i), isMember, status));
		}
	}
	
	@Getter @Setter
	private class OrganizationDTO {
		
		private long id;
		private String name;
		
		public OrganizationDTO(Organization organization) {
			this.setId(organization.getId());
			this.setName(organization.getName());
		}
	}
	
	@Getter @Setter
	private class TypeSessionDTO {
		
		private long id;
		private String name;
		
		public TypeSessionDTO(TypeSession typeSession) {
			this.setId(typeSession.getId());
			this.setName(typeSession.getName());
		}
	}
	
	@Getter @Setter
	private class MeDTO {
		
		private MemberUserDTO member;
		private MeRegisteredDTO registered;
		
		public MeDTO(Member member, RegisteredSession registered) {
			this.member = new MemberUserDTO(member);
			if (registered != null) {
				this.setRegistered(new MeRegisteredDTO(registered));
			}
		}

		@Getter @Setter
		private class MeRegisteredDTO {
			
			private long id;
			private OffsetDateTime dateRegistered;
			
			public MeRegisteredDTO(RegisteredSession registered) {
				this.setId(registered.getId());
				this.setDateRegistered(registered.getDateRegistered());
			}
		}

		@Getter @Setter
		private class MemberUserDTO {
			
			private long id;
			private RoleEnum role;
			
			public MemberUserDTO(Member member) {
				this.setId(member.getId());
				this.setRole(member.getRole());
			}
		}
	}

	@Getter @Setter
	private class RegisteredDTO {
		
		private long id;
		private OffsetDateTime dateRegistered;
		private boolean isMember;
		private UserRegisteredDTO user;
		private RegisteredEnum status;
		
		public RegisteredDTO(RegisteredSession registered, boolean isMember, RegisteredEnum status) {
			this.setId(registered.getId());
			this.setDateRegistered(registered.getDateRegistered());
			this.setMember(isMember);
			this.setUser(new UserRegisteredDTO(registered.getUser()));
			this.setStatus(status);
		}

		@Getter @Setter
		private class UserRegisteredDTO {
			
			private long id;
			private String firstName;
			private String lastName;
			private String email;
			
			public UserRegisteredDTO(User user) {
				this.setId(user.getId());
				this.setFirstName(user.getFirstName());
				this.setLastName(user.getLastName());
				this.setEmail(user.getEmail());
			}
		}
	}
	
	@Getter @Setter
	private class CommentDTO {
		
		private Long id;
		private String comment;
		private OffsetDateTime dateComment;
		private UserDTO user;
		
		public CommentDTO(CommentSession comment) {
			this.id = comment.getId();
			this.comment = comment.getComment();
			this.dateComment = comment.getDateComment();
			this.user = new UserDTO(comment.getUser());
		}
		
		@Getter @Setter
		private class UserDTO {
		
			private Long id;
			private String firstName;
			private String lastName;
			private String email;
			
			public UserDTO(User user) {
				this.id = user.getId();
				this.firstName= user.getFirstName();
				this.lastName = user.getLastName();
				this.email = user.getEmail();
			}
		}
	}
}
