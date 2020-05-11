package com.miimber.back.organization.controller.organization;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.enums.StripeStatusEnum;
import com.miimber.back.core.helper.Helper;
import com.miimber.back.core.helper.StripeService;
import com.miimber.back.organization.dto.organization.OrganizationAndMemberReadResponseDTO;
import com.miimber.back.organization.dto.organization.OrganizationCreateReadUpdateResponseDTO;
import com.miimber.back.organization.dto.organization.OrganizationCreateRequestDTO;
import com.miimber.back.organization.dto.organization.OrganizationForManageReadResponseDTO;
import com.miimber.back.organization.dto.organization.OrganizationRequestDTO;
import com.miimber.back.organization.dto.organization.OrganizationTokenRequestDTO;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.organization.model.enums.StateOrganizationEnum;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.organization.service.OrganizationService;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.service.TypeSessionService;
import com.miimber.back.user.model.User;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TypeSessionService typeSessionService;
	
	@Autowired
	private StripeService stripeService;
	
	@Autowired
	private Helper helper;

	/**
	 * Get all organization where the user is the owner
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/editable", method = RequestMethod.GET)
	public ResponseEntity<?> readOrganization() throws Exception {
        UserDetails currentUser = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = helper.getUserToken(currentUser);
        List<OrganizationCreateReadUpdateResponseDTO> responseOrganization = new ArrayList<OrganizationCreateReadUpdateResponseDTO>();
        for (Organization organization : organizationService.getOrganizationEditable(user)) 
        { 
        	responseOrganization.add(new OrganizationCreateReadUpdateResponseDTO(organization));
        }
		return ResponseEntity.ok(responseOrganization);
	}
	
	/**
	 * Get All organization to manage
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/{id}/manage", method = RequestMethod.GET)
	public ResponseEntity<?> readOrganizationByIdAndManage(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(id, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        if (member.getRole().equals(RoleEnum.MEMBER)) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        return ResponseEntity.ok(new OrganizationForManageReadResponseDTO(
        		member.getOrganization()
			));
	}
	
	@RequestMapping(value = "/organization/{id}/active", method = RequestMethod.GET)
	public ResponseEntity<?> activeOrganization(@PathVariable Long id) throws Exception {
		Organization organization = organizationService.get(id);
		
		if (organization == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		if (organization.getStripe() == null) {
			return new ResponseEntity(HttpStatus.OK);
		}
		Subscription subscription = stripeService.getSubscription(organization.getStripe());
		String statusStripe = subscription.getStatus();
		if (statusStripe == StripeStatusEnum.TRIALING.getStatus() || statusStripe == StripeStatusEnum.ACTIVE.getStatus()) {
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * Get organization by id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> readOrganization(@PathVariable long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
		Organization organization = organizationService.get(id);
		
		if (organization == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
        if (organization.getStripe() != null && organization.getStripeEnd().before(new Timestamp(System.currentTimeMillis()))) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
		
		Member member = memberService.getMemberByOrganizationAndByUser(organization, user);
		
		return ResponseEntity.ok(new OrganizationAndMemberReadResponseDTO(organization, member));
	}
	
	/**
	 * Create a organization and put the user has owner
	 * @param organizationDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/", method = RequestMethod.POST)
	public ResponseEntity<?> readOrganizationAll(@RequestBody OrganizationCreateRequestDTO organizationDto) throws Exception {
        UserDetails currentUser = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = helper.getUserToken(currentUser);
        
        Customer customer = stripeService.createCustomer(organizationDto.getTokenId(), user.getEmail());
        
        Subscription subscription = stripeService.createSubscription(customer, organizationDto.getSubscription(), 1L);
        Timestamp stripeEnd = new Timestamp(subscription.getCurrentPeriodEnd() * 1000 + 3600 * 24 * 3);
        
        Organization newOrganization = new Organization(organizationDto.getName());
        newOrganization= organizationService.create(newOrganization);
        newOrganization.setStripe(subscription.getId());
        newOrganization.setStripeEnd(stripeEnd);
        newOrganization.setState(StateOrganizationEnum.ACTIVE);
        
        Member newMember = new Member();
        newMember.setOrganization(newOrganization);
        newMember.setUser(user);
        newMember.setRole(RoleEnum.OWNER);
        newMember = memberService.create(newMember);
        
        newOrganization.addMember(newMember);
        
        TypeSession newTypeSession = new TypeSession();
        newTypeSession.setOrganization(newOrganization);
        newTypeSession.setName("Default");
        
        typeSessionService.create(newTypeSession);
        
        return ResponseEntity.ok(new OrganizationCreateReadUpdateResponseDTO(newOrganization));
	}

	/**
	 * Find if an organization name is taken
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> readNameExit(@PathVariable String name) throws Exception {
		Organization organization = organizationService.getOrganizationByName(name);
		if (organization == null) {
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.CONFLICT);
	}
	
	/**
	 * Change the organization stripe card
	 * @param organizationDto
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/{id}/card", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrganizationCard(@RequestBody OrganizationTokenRequestDTO organizationDto, @PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (memberUser.getRole() != RoleEnum.OWNER) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Organization organization = memberUser.getOrganization();
        stripeService.updateCardForSubscription(organization.getStripe(), organizationDto.getToken());

        organization.setState(StateOrganizationEnum.ACTIVE);
        organizationService.update(organization);
        return new ResponseEntity(HttpStatus.OK);
	}
	
	/**
	 * Update an organization
	 * @param organizationDto
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrganization(@RequestBody OrganizationRequestDTO organizationDto, @PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Organization organization = memberUser.getOrganization();
        organization.setName(organizationDto.getName());
        return ResponseEntity.ok(new OrganizationCreateReadUpdateResponseDTO(organizationService.update(organization)));
	}
	
	/**
	 * Delete a organization
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/organization/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrganization(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (memberUser.getRole() != RoleEnum.OWNER) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Organization organization = memberUser.getOrganization();
        if (organization.getStripe() != null) {
            stripeService.deleteSubscription(organization.getStripe());
        }
        organizationService.delete(organization);
        return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/organization/{id}/archive", method = RequestMethod.PUT)
	public ResponseEntity<?> archiveOrganization(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (memberUser.getRole() != RoleEnum.OWNER) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Organization organization = memberUser.getOrganization();
        if (organization.getStripe() != null) {
            stripeService.deleteSubscription(organization.getStripe());
        }
        organization.setState(StateOrganizationEnum.ARCHIVE);
        organizationService.update(organization);
        for(Member member: organization.getMembers()) {
        	memberService.delete(member);
        }
        return new ResponseEntity(HttpStatus.OK);
	}
}
