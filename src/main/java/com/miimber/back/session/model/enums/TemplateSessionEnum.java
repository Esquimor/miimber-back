package com.miimber.back.session.model.enums;

public enum TemplateSessionEnum {
	MULTIPLE("M"), ONCE("O");
	
    private String recurrency;
    
    private TemplateSessionEnum(String recurrency) {
        this.recurrency = recurrency;
    }
 
    public String getRecurrency() {
        return recurrency;
    }
}
