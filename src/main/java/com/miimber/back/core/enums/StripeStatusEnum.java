package com.miimber.back.core.enums;

public enum StripeStatusEnum {
	TRIALING("trialing"), ACTIVE("active"), INCOMPLETE("incomplete"), INCOMPLETE_EXPIRED("incomplete_expired"), PAST_DUE("past_due"), CANCELED("canceled"), UNPAID("unpaid");
	
    private String status;
    
    private StripeStatusEnum(String status) {
        this.status = status;
    }
 
    public String getStatus() {
        return status;
    }
}
