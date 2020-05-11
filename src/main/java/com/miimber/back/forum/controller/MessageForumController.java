package com.miimber.back.forum.controller;

import java.time.OffsetDateTime;

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

import com.miimber.back.core.helper.Helper;
import com.miimber.back.forum.dto.message.MessageForumCreateRequestDTO;
import com.miimber.back.forum.dto.message.MessageForumCreateResponseDTO;
import com.miimber.back.forum.model.MessageForum;
import com.miimber.back.forum.model.TalkForum;
import com.miimber.back.forum.service.MessageForumService;
import com.miimber.back.forum.service.TalkForumService;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageForumController {
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TalkForumService talkService;
	
	@Autowired
	private MessageForumService messageService;
	
	@RequestMapping(value = "/organization/{idOrg}/forum/message/", method = RequestMethod.POST)
	public ResponseEntity<?> createPost(@PathVariable Long idOrg, @RequestBody MessageForumCreateRequestDTO messageDto) throws Exception {
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrg, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        TalkForum talk = talkService.get(messageDto.getTalkId());
        if (talk == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (talk.getSubject().getCategory().getOrganization().getId() != idOrg) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        MessageForum message = new MessageForum();
        message.setDateMessage(OffsetDateTime.now());
        message.setTalk(talk);
        message.setMessage(messageDto.getMessage());
        message.setUser(user);
        
        return ResponseEntity.ok(new MessageForumCreateResponseDTO(messageService.create(message)));
	}
}
