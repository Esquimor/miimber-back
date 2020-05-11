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
import com.miimber.back.forum.dto.talk.TalkForumCreateRequestDTO;
import com.miimber.back.forum.dto.talk.TalkForumCreateResponseDTO;
import com.miimber.back.forum.dto.talk.TalkForumReadResponseDTO;
import com.miimber.back.forum.model.TalkForum;
import com.miimber.back.forum.model.MessageForum;
import com.miimber.back.forum.model.SubjectForum;
import com.miimber.back.forum.service.TalkForumService;
import com.miimber.back.forum.service.MessageForumService;
import com.miimber.back.forum.service.SubjectForumService;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TalkForumController {
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TalkForumService talkService;
	
	@Autowired
	private SubjectForumService subjectService;
	
	@Autowired
	private MessageForumService messageService;

	@RequestMapping(value = "/organization/{idOrg}/forum/talk/", method = RequestMethod.POST)
	public ResponseEntity<?> createTalk(@PathVariable Long idOrg, @RequestBody TalkForumCreateRequestDTO talkDto) throws Exception {
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrg, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        SubjectForum subject = subjectService.get(talkDto.getIdSubject());
        if (subject == null) {
        	return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (subject.getCategory().getOrganization().getId() != idOrg) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        TalkForum talk = new TalkForum();
        talk.setDateTalk(OffsetDateTime.now());
        talk.setTitle(talkDto.getTitle());
        talk.setSubject(subject);
        talk.setUser(user);
        
        talk = talkService.create(talk);
        
        MessageForum message = new MessageForum();
        message.setDateMessage(OffsetDateTime.now());
        message.setTalk(talk);
        message.setMessage(talkDto.getMessage());
        message.setUser(user);
        
        messageService.create(message);
        
        return ResponseEntity.ok(new TalkForumCreateResponseDTO(talk));
	}
	
	@RequestMapping(value = "/organization/{idOrg}/forum/talk/{idTalk}", method = RequestMethod.GET)
	public ResponseEntity<?> readTalk(@PathVariable Long idOrg, @PathVariable Long idTalk) throws Exception {
		TalkForum talk = talkService.get(idTalk);
		
		if (talk == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		if (talk.getSubject().getCategory().getOrganization().getId() != idOrg) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrg, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new TalkForumReadResponseDTO(talk));
	}
}
