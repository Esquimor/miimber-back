package com.miimber.back.session.enumItem;

public enum RepeatEnum {
	ONE("1"), TWO("2"), THREE("3"), FOUR("4");
	
    private String repeat;
    
    private RepeatEnum(String repeat) {
        this.repeat = repeat;
    }
 
    public String getRepeat() {
        return repeat;
    }
}
