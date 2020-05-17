package com.miimber.back.session.dto.templateSession;

import java.time.OffsetTime;

import com.miimber.back.organization.model.Organization;
import com.miimber.back.session.model.TemplateSession;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.model.enums.TemplateSessionEnum;
import com.miimber.back.session.model.enums.TemplateSessionStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TemplateSessionTemplateCreateReadResponseDTO {

	private Long id;
	private String title;
	private String description;
	private TemplateSessionEnum recurrency;
	private Integer day;
	private OffsetTime startHour;
	private OffsetTime endHour;
	private int limit;
	private TypeSessionDTO typeSession;
	private OrganizationDTO organization;
	private TemplateSessionStatusEnum status;
	
	public TemplateSessionTemplateCreateReadResponseDTO(TemplateSession templateSession) {
		this.id = templateSession.getId();
		this.title = templateSession.getTitle();
		this.description = templateSession.getDescription();
		this.recurrency = templateSession.getRecurrency();
		this.day = templateSession.getDay();
		this.startHour = templateSession.getStartHour();
		this.endHour = templateSession.getEndHour();
		this.limit = templateSession.getLimit();
		this.typeSession = new TypeSessionDTO(templateSession.getTypeSession());
		this.organization = new OrganizationDTO(templateSession.getOrganization());
		this.status = templateSession.getStatus();
	}
	
	@Getter @Setter
	private class TypeSessionDTO {
		
		private Long id;
		private String name;
		
		public TypeSessionDTO(TypeSession typeSession) {
			this.id = typeSession.getId();
			this.name = typeSession.getName();
		}
	}
	
	@Getter @Setter
	private class OrganizationDTO {
		private Long id;
		
		public OrganizationDTO(Organization organization) {
			this.id = organization.getId();
		}
	}
}
