package com.miimber.back.core.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.helper.MailJetService;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.StateOrganizationEnum;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.organization.service.OrganizationService;
import com.miimber.back.user.model.User;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Invoice;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebhookController {

	@Value("${stripe.apikey}")
	private String apiKey;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MailJetService mailjetservice;
	
	@RequestMapping( value ="/webhook/stripe" ,method = RequestMethod.POST) 
	public ResponseEntity<?> webhookStripe(@RequestBody(required=true)String  request, @RequestHeader("Stripe-Signature") String signature) throws Exception {        
        Event event = null;

        try {
            event = Webhook.constructEvent(request, signature, "whsec_yulUUYrGrvWwlmgG5MQokO7pchI1Ojm0");
        } catch (SignatureVerificationException e) {
			return new ResponseEntity(HttpStatus.CONFLICT);
        }

        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
			return new ResponseEntity(HttpStatus.CONFLICT);
        }
        

        switch (event.getType()) {
	        case "invoice.payment_succeeded":
	            Invoice invoiceSucceeded = (Invoice) stripeObject;
	            Organization organizationSucceeded = organizationService.getOrganizationByStripe(invoiceSucceeded.getSubscription());
	            if (organizationSucceeded == null) {
	            	return new ResponseEntity(HttpStatus.NOT_FOUND);
	            }
	            Timestamp stripeEnd = new Timestamp(invoiceSucceeded.getPeriodEnd() * 1000 + 3600 * 24 * 3);
	            organizationSucceeded.setStripeEnd(stripeEnd);
	            organizationService.update(organizationSucceeded);
	            break;
	        case "invoice.payment_failed":
	            Invoice invoiceFailed = (Invoice) stripeObject;
	            Organization organizationFailed = organizationService.getOrganizationByStripe(invoiceFailed.getSubscription());
	            if (organizationFailed == null) {
	            	return new ResponseEntity(HttpStatus.NOT_FOUND);
	            }
	            Member memberOwner = memberService.getFirstMemberOwnerForOrganization(organizationFailed);
	            User userOwner = memberOwner.getUser();
				mailjetservice.sendEmailPaymentFailed(userOwner.getEmail(), userOwner.getFirstName(), userOwner.getLang(), organizationFailed);
	            organizationFailed.setState(StateOrganizationEnum.SUSPENDED);
	            organizationService.update(organizationFailed);
				break;
        }

        return new ResponseEntity(HttpStatus.OK);
	}
}
