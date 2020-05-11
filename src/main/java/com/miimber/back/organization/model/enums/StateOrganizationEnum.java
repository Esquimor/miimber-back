package com.miimber.back.organization.model.enums;

public enum StateOrganizationEnum {
	ACTIVE("A"), SUSPENDED("S"), ARCHIVE("R");
	
    private String state;
    
    private StateOrganizationEnum(String state) {
        this.state = state;
    }
 
    public String getState() {
        return state;
    }
}