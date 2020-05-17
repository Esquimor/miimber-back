package com.miimber.back.session.model.enums;

public enum TemplateSessionStatusEnum {
	ARCHIVE("A"), GOING("GO");
	
    private String status;
    
    private TemplateSessionStatusEnum(String status) {
        this.status = status;
    }
 
    public String getStatus() {
        return status;
    }
}
