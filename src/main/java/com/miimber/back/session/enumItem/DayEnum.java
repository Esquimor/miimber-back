package com.miimber.back.session.enumItem;

public enum DayEnum {
	MONDAY("M"), TUESDAY("TU"), WEDNESDAY("W"), THURSDAY("TH"), FRIDAY("F"), SATURDAY("S"), SUNDAY("SU");
	
    private String day;
    
    private DayEnum(String day) {
        this.day = day;
    }
 
    public String getDay() {
        return day;
    }
}
