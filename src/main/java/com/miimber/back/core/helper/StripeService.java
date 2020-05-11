package com.miimber.back.core.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionCreateParams.Item;
import com.stripe.param.SubscriptionUpdateParams;

@Service
public class StripeService {

	@Value("${stripe.apikey}")
	private String apiKey;
	
	public Customer createCustomer(String token, String email) throws StripeException {
        Stripe.apiKey = apiKey;
		CustomerCreateParams customerParams =
		  CustomerCreateParams.builder()
		    .setSource(token)
		    .setEmail(email)
		    .build();
	
		return Customer.create(customerParams);
	}
	
	public Subscription updateCardForSubscription(String subscriptionId, String token) throws StripeException {
        Stripe.apiKey = apiKey;
        Subscription subscription = getSubscription(subscriptionId);
		String customerId = subscription.getCustomer();
		
		Customer customer =
				  Customer.retrieve(customerId);
		
		Map<String, Object> params = new HashMap<>();
		params.put("source", token);

		Card card =
		  (Card) customer.getSources().create(params);

		SubscriptionUpdateParams subscriptionParams =
		  SubscriptionUpdateParams.builder()
		  	.setDefaultPaymentMethod(card.getId())
		    .build();
		
		return subscription.update(subscriptionParams);
	}
	
	public Customer updateEmailCustomerForSubscription(String subscriptionId, String email) throws StripeException {
        Stripe.apiKey = apiKey;
        Subscription subscription = getSubscription(subscriptionId);
		String customerId = subscription.getCustomer();
		
		Customer customer =
				  Customer.retrieve(customerId);

		Map<String, Object> params = new HashMap<>();
		params.put("email", email);

		return customer.update(params);
	}
	
	public Subscription createSubscription(Customer customer, String plan, Long quantity) throws StripeException {
        Stripe.apiKey = apiKey;
		Item item = Item.builder()
				.setPlan(plan)
				.setQuantity(quantity)
				.build();
		
		SubscriptionCreateParams subscriptionParams = 
				SubscriptionCreateParams.builder()
				.setCustomer(customer.getId())
				.addItem(item)
			    .setProrationBehavior(SubscriptionCreateParams.ProrationBehavior.ALWAYS_INVOICE)
				.build()
				;
		
		return Subscription.create(subscriptionParams);
	}
	
	public Subscription getSubscription(String subscriptionId) throws StripeException {
        Stripe.apiKey = apiKey;
		return Subscription.retrieve(subscriptionId);
	}
	
	public Subscription updateSubscription(String subscriptionId, Long quantity) throws StripeException {
        Stripe.apiKey = apiKey;
        Subscription subscription = getSubscription(subscriptionId);

		SubscriptionUpdateParams params =
		  SubscriptionUpdateParams.builder()
		    .addItem(
		      SubscriptionUpdateParams.Item.builder()
		        .setId(subscription.getItems().getData().get(0).getId())
				.setQuantity(quantity)
		        .build())
		    .build();
		
		return subscription.update(params);
	}
	
	public Subscription deleteSubscription(String subscriptionId) throws StripeException {
        Stripe.apiKey = apiKey;
        Subscription subscription = getSubscription(subscriptionId);
        
		return subscription.cancel();
	}
	
	public Subscription addOneMemberSubscription(String subscriptionId) throws StripeException {
        Stripe.apiKey = apiKey;
        Subscription subscription = getSubscription(subscriptionId);
		
        Long quantity = subscription.getItems().getData().get(0).getQuantity() + 1;

		SubscriptionUpdateParams params =
		  SubscriptionUpdateParams.builder()
		    .addItem(
		      SubscriptionUpdateParams.Item.builder()
		        .setId(subscription.getItems().getData().get(0).getId())
				.setQuantity(quantity)
		        .build())
		    .build();
		
		return subscription.update(params);
	}
	
	public Subscription removeOneMemberSubscription(String subscriptionId) throws StripeException {
        Stripe.apiKey = apiKey;
        Subscription subscription = getSubscription(subscriptionId);
		
        Long quantity = subscription.getItems().getData().get(0).getQuantity() - 1;

		SubscriptionUpdateParams params =
		  SubscriptionUpdateParams.builder()
		    .addItem(
		      SubscriptionUpdateParams.Item.builder()
		        .setId(subscription.getItems().getData().get(0).getId())
				.setQuantity(quantity)
		        .build())
		    .build();
		
		return subscription.update(params);
	}
	
	public Charge chargeCustomerByCard(Customer customer, String currency, Long amount) throws StripeException {
        Stripe.apiKey = apiKey;
		ChargeCreateParams chargeParams =
		  ChargeCreateParams.builder()
		    .setAmount(amount)
		    .setCurrency(currency)
		    .setCustomer(customer.getId())
		    .build();
		
		return Charge.create(chargeParams);
	}
}
