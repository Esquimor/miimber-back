package com.miimber.back.core.helper;

import java.time.OffsetDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import com.miimber.back.core.enums.LangEnum;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.session.model.Session;

@Service
public class MailJetService {

	@Value("${mailjet.public}")
	private String apiKeyPublic;

	@Value("${mailjet.private}")
	private String apiKeyPrivate;
    
    public MailjetResponse sendEmail(String fromEmail, String fromName, String toEmail, String toName, String subject, JSONObject variables, Integer template) throws MailjetException, MailjetSocketTimeoutException {
    	MailjetClient client;
        MailjetResponse response;
    	client = new MailjetClient(apiKeyPublic, apiKeyPrivate, new ClientOptions("v3.1"));
    	MailjetRequest request = new MailjetRequest(Emailv31.resource)
    			.property(Emailv31.MESSAGES, new JSONArray()
    	                .put(new JSONObject()
    	                    .put(Emailv31.Message.FROM, new JSONObject()
    	                        .put("Email", fromEmail))
    	                    .put(Emailv31.Message.TO, new JSONArray()
    	                        .put(new JSONObject()
    	                            .put("Email", toEmail)
    	                            .put("Name", toName)))
    	                    .put(Emailv31.Message.VARIABLES, variables)
    	                    .put(Emailv31.Message.SUBJECT, subject)
    	                    .put(Emailv31.Message.TEMPLATELANGUAGE, true)
    	                    .put(Emailv31.Message.TEMPLATEID, template)));
        response = client.post(request);
        return response;
    }
    
    public MailjetResponse sendEmailRegister(String email, String name, LangEnum lang, String token, Long userId) throws MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	switch(lang) {
    		case FR:
    			idTemplate = 1379267;
    			subject = "Valider votre compte";
    			break;
    		default:
    			idTemplate = 1357551;
    			subject = "Validate your account";
    			break;
    	}
    	
    	return this.sendEmail(
			"no-reply@test280407.ga", 
			"Miimber", 
			email, 
			name, 
			subject,
			new JSONObject()
				.put("token", token)
				.put("id", userId),
			idTemplate
		);
    }
    
    public MailjetResponse sendEmailResetPassword(String email, String name, LangEnum lang, String token, Long userId) throws MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	switch(lang) {
    		case FR:
    			idTemplate = 1359680;
    			subject = "Mot de passe oublié";
    			break;
    		default:
    			idTemplate = 1379269;
    			subject = "Reset Password";
    			break;
    	}
    	
    	return this.sendEmail(
			"no-reply@test280407.ga", 
			"Miimber", 
			email, 
			name, 
			subject,
			new JSONObject()
				.put("token", token)
				.put("id", userId),
			idTemplate
		);
    }
    
    public MailjetResponse sendEmailInvitation(String email, String name, LangEnum lang, String token, Long userId, String sender, String organization) throws MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	switch(lang) {
    		case FR:
    			idTemplate = 1359682;
    			subject = "Bonjour. Tu es invité sur Miimber.";
    			break;
    		default:
    			idTemplate = 1379272;
    			subject = "Hello. You are invited on Miimber.";
    			break;
    	}
    	
    	return this.sendEmail(
			"no-reply@test280407.ga", 
			"Miimber", 
			email, 
			name, 
			subject,
			new JSONObject()
				.put("sender", sender)
				.put("organization", organization)
				.put("token", token)
				.put("id", userId),
			idTemplate
		);
    }
    
    public MailjetResponse sendEmailChangeEmail(String email, String name, LangEnum lang, String token, Long userId) throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	switch(lang) {
    		case FR:
    			idTemplate = 1379146;
    			subject = "Changement de mail";
    			break;
    		default:
    			idTemplate = 1379266;
    			subject = "Mail change";
    			break;
    	}
    	
    	return this.sendEmail(
    			"no-reply@test280407.ga",
    			"Miimber",
    			email,
    			name,
    			subject,
    			new JSONObject()
					.put("token", token)
					.put("id", userId),
    			idTemplate
    		);
    }
    
    public MailjetResponse sendEmailTakenSession(String email, String name, LangEnum lang, Session session)  throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	String date;
    	
    	switch(lang) {
    		case FR:
    			idTemplate = 1390934;
    			subject = "Participation confirmé";
    			date = getDate(session.getStartDate(), session.getEndDate(), lang, "de", "à");
    			break;
    		default:
    			idTemplate = 1390928;
    			subject = "Participation confirmed";
    			date = getDate(session.getStartDate(), session.getEndDate(), lang, "to", "at");
    			break;
    	}
    	
    	return this.sendEmail(
    			"no-reply@test280407.ga",
    			"Miimber",
    			email,
    			name,
    			subject,
    			new JSONObject()
				.put("session", session.getTemplateSession().getTitle())
				.put("id", session.getId())
				.put("organization", session.getOrganization().getName())
				.put("date", date),
    			idTemplate
    		);
    }
    
    public MailjetResponse sendEmailWaitingSession(String email, String name, LangEnum lang, Session session) throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	String date;
    	
    	switch(lang) {
    		case FR:
    			idTemplate = 1391122;
    			subject = "Participation mise en attente";
    			date = getDate(session.getStartDate(), session.getEndDate(), lang, "de", "à");
    			break;
    		default:
    			idTemplate = 1391123;
    			subject = "Participation put on hold";
    			date = getDate(session.getStartDate(), session.getEndDate(), lang, "to", "at");
    			break;
    	}
    	
    	return this.sendEmail(
    			"no-reply@test280407.ga",
    			"Miimber",
    			email,
    			name,
    			subject,
    			new JSONObject()
				.put("session", session.getTemplateSession().getTitle())
				.put("id", session.getId())
				.put("organization", session.getOrganization().getName())
				.put("date", date),
    			idTemplate
    		);
    }
    
    public MailjetResponse sendEmailCanceledSession(String email, String name, LangEnum lang, Session session) throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	String date;
    	
    	switch(lang) {
    		case FR:
    			idTemplate = 1391198;
    			subject = "Participation annulé";
    			date = getDate(session.getStartDate(), session.getEndDate(), lang, "de", "à");
    			break;
    		default:
    			idTemplate = 1391194;
    			subject = "Participation canceled";
    			date = getDate(session.getStartDate(), session.getEndDate(), lang, "to", "at");
    			break;
    	}
    	
    	return this.sendEmail(
    			"no-reply@test280407.ga",
    			"Miimber",
    			email,
    			name,
    			subject,
    			new JSONObject()
				.put("session", session.getTemplateSession().getTitle())
				.put("organization", session.getOrganization().getName())
				.put("date", date),
    			idTemplate
    		);
    }
    
    public MailjetResponse sendEmailPaymentFailed(String email, String name, LangEnum lang, Organization organization) throws JSONException, MailjetException, MailjetSocketTimeoutException {
    	Integer idTemplate;
    	String subject;
    	
    	switch(lang) {
    		case FR:
    			idTemplate = 1403281;
    			subject = "Paiement refusé";
    			break;
    		default:
    			idTemplate = 1403284;
    			subject = "Payment failed";
    			break;
    	}
    	
    	return this.sendEmail(
    			"no-reply@test280407.ga",
    			"Miimber",
    			email,
    			name,
    			subject,
    			new JSONObject()
				.put("organization", organization.getName()),
    			idTemplate
    		);
    }
    
    private String getDate(OffsetDateTime start, OffsetDateTime end, LangEnum lang, String attachWordFirst, String attachWordSecond) {
    	int hourStart = start.getHour();
    	int minuteStart = start.getMinute();
    	int hourEnd = end.getHour();
    	int minuteEnd = end.getMinute();
    	int dayMonthValue = start.getDayOfMonth();
    	String dayWeekValue = start.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, new Locale(lang.getLang()));
    	return dayWeekValue + " " + dayMonthValue + " " + attachWordFirst + " "+ hourStart + "h"+ minuteStart + " " + attachWordSecond + " "+ hourEnd + "h"+ minuteEnd;
    }
}
