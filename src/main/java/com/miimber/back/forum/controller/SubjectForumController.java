package com.miimber.back.forum.controller;

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

import com.miimber.back.core.helper.Helper;
import com.miimber.back.forum.dto.subject.SubjectForumCreateRequestDTO;
import com.miimber.back.forum.dto.subject.SubjectForumCreateUpdateResponseDTO;
import com.miimber.back.forum.dto.subject.SubjectForumMultipleUpdateRequestDTO;
import com.miimber.back.forum.dto.subject.SubjectForumReadResponseDTO;
import com.miimber.back.forum.dto.subject.SubjectForumTemplateUpdateRequestDTO;
import com.miimber.back.forum.model.CategoryForum;
import com.miimber.back.forum.model.SubjectForum;
import com.miimber.back.forum.service.CategoryForumService;
import com.miimber.back.forum.service.SubjectForumService;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubjectForumController {
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryForumService categoryService;
	
	@Autowired
	private SubjectForumService subjectService;
	
	@RequestMapping(value = "/organization/{idOrg}/forum/subject/{idSub}", method = RequestMethod.GET)
	public ResponseEntity<?> readSubjects(@PathVariable Long idOrg, @PathVariable Long idSub) throws Exception {
        SubjectForum subject = subjectService.get(idSub);
        
        if (subject == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
		
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrg, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        if (subject.getCategory().getOrganization().getId() != idOrg) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        return ResponseEntity.ok(new SubjectForumReadResponseDTO(subject));
	}
	
	@RequestMapping(value = "/organization/{id}/forum/subject/", method = RequestMethod.POST)
	public ResponseEntity<?> createSubject(@PathVariable Long id, @RequestBody SubjectForumCreateRequestDTO subjectDto) throws Exception {
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(id, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!member.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        CategoryForum category = categoryService.get(subjectDto.getIdCategory());
        if (category == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        SubjectForum subject = new SubjectForum();
        subject.setCategory(category);
        subject.setTitle(subjectDto.getTitle());
        subject.setPosition(category.getSubjects().size());
        
        return ResponseEntity.ok(new SubjectForumCreateUpdateResponseDTO(subjectService.create(subject)));
	}
	
	@RequestMapping(value = "/organization/{idOrg}/forum/subject/{idSub}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSubject(@PathVariable Long idOrg, @PathVariable Long idSub, @RequestBody SubjectForumTemplateUpdateRequestDTO subjectDto) throws Exception {
		SubjectForum subject = subjectService.get(idSub);
		if (subject == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		if (subject.getId() != idSub) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrg, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!member.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        subject.setTitle(subjectDto.getTitle());
        subject.setPosition(subjectDto.getPosition());

        return ResponseEntity.ok(new SubjectForumCreateUpdateResponseDTO(subjectService.update(subject)));
	}
	
	@RequestMapping(value = "/organization/{idOrg}/forum/subject/", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSubjects(@PathVariable Long idOrg, @RequestBody SubjectForumMultipleUpdateRequestDTO subjectsDto) throws Exception {
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrg, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!member.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        List<SubjectForumCreateUpdateResponseDTO> subjectsResponse = new ArrayList<SubjectForumCreateUpdateResponseDTO>();
        for(SubjectForumTemplateUpdateRequestDTO subjectDto: subjectsDto.getSubjects()) {
        	SubjectForum subject = subjectService.get(subjectDto.getId());
        	if (subject == null) {
    			return new ResponseEntity(HttpStatus.NOT_FOUND);
        	}
        	subject.setTitle(subjectDto.getTitle());
        	subject.setPosition(subjectDto.getPosition());
        	subjectsResponse.add(new SubjectForumCreateUpdateResponseDTO(subjectService.update(subject)));
        }
        return ResponseEntity.ok(subjectsResponse);
	}
	
	@RequestMapping(value = "/organization/{idOrg}/forum/subject/{idSub}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSubject(@PathVariable Long idOrg, @PathVariable Long idSub) throws Exception {
		SubjectForum subject = subjectService.get(idSub);
		if (subject == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrg, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!member.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        subjectService.delete(subject);
        return new ResponseEntity(HttpStatus.OK);
	}
}
