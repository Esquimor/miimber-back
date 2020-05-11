package com.miimber.back.core.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private final String jwttoken;
	
	public JwtResponseDTO(String jwttoken) {
		this.jwttoken = jwttoken;
	}
}
