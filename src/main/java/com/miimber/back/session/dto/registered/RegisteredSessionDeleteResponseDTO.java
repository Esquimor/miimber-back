package com.miimber.back.session.dto.registered;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisteredSessionDeleteResponseDTO {

	private long id;
	
	public RegisteredSessionDeleteResponseDTO(Long id) {
		this.id = id;
	}
}
