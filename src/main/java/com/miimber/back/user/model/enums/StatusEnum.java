package com.miimber.back.user.model.enums;

public enum StatusEnum {
	Validated("V"), Waiting("W");
	
    private String role;
    
    private StatusEnum(String role) {
        this.role = role;
    }
 
    public String getRole() {
        return role;
    }
}
