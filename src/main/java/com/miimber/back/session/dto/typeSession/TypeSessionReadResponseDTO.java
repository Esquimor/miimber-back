package com.miimber.back.session.dto.typeSession;

import com.miimber.back.session.model.TypeSession;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TypeSessionReadResponseDTO {

	private long id;
	private String name;
	
	public TypeSessionReadResponseDTO(TypeSession typeSession) {
		this.id = typeSession.getId();
		this.name = typeSession.getName();
	}
}
