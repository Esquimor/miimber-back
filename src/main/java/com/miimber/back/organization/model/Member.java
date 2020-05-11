package com.miimber.back.organization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="members")
@Getter @Setter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @ManyToOne
    private User user;

	@Column(nullable = false)
	private RoleEnum role = RoleEnum.MEMBER;
    
    @ManyToOne
	@NotNull
    private Organization organization;
	
	public boolean canEditOrganization() {
		if (this.role == RoleEnum.MEMBER || this.role == RoleEnum.INSTRUCTOR) {
			return false;
		}
		return true;
	}
	
	public boolean canEmergeOrganization() {
		if (this.role == RoleEnum.MEMBER || this.role == RoleEnum.OFFICE) {
			return false;
		}
		return true;
	}
}
