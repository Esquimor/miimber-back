package com.miimber.back.session.model.enums;

public enum RegisteredEnum {
	TAKEN("T"), WAITING("W");
	
    private String status;
    
    private RegisteredEnum(String status) {
        this.status = status;
    }
 
    public String getStatus() {
        return status;
    }
}
