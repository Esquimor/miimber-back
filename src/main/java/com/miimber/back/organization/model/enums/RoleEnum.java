package com.miimber.back.organization.model.enums;

public enum RoleEnum {
	OWNER("O"), INSTRUCTOR("I"), MEMBER("M"), OFFICE("OF"), OFFICE_INSTRUCTOR("OI");
	
    private String role;
    
    private RoleEnum(String role) {
        this.role = role;
    }
 
    public String getRole() {
        return role;
    }
}
