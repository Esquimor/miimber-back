package com.miimber.back.session.controller;

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
import com.miimber.back.session.dto.comment.CommentSessionCreateRequestDTO;
import com.miimber.back.session.dto.comment.CommentSessionCreateUpdateResponseDTO;
import com.miimber.back.session.dto.comment.CommentSessionUpdateRequestDTO;
import com.miimber.back.session.model.CommentSession;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.service.CommentSessionService;
import com.miimber.back.session.service.SessionService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentSessionController {
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private CommentSessionService commentService;

	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value= "/comment/", method = RequestMethod.POST)
	public ResponseEntity<?> createComment(@RequestBody CommentSessionCreateRequestDTO commentDto) throws Exception {
		Session session = sessionService.get(commentDto.getSessionId());
		
		if (session == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        CommentSession comment = new CommentSession();
        comment.setComment(commentDto.getComment());
        comment.setDateComment(commentDto.getDateComment());
        comment.setSession(session);
        comment.setUser(user);
        if (commentDto.getCommentParentId() != null) {
        	comment.setCommentParent(commentService.get(commentDto.getCommentParentId()));
        }
        
        return ResponseEntity.ok(new CommentSessionCreateUpdateResponseDTO(commentService.create(comment)));
	}
	
	@RequestMapping(value= "/comment/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateComment(@RequestBody CommentSessionUpdateRequestDTO commentDto, @PathVariable Long id) throws Exception {
		CommentSession comment = commentService.get(id);
		
		if (comment == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		if(comment.getId() != commentDto.getId()) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (comment.getUser().getId() != user.getId()) {
			return new ResponseEntity(HttpStatus.CONFLICT);
        }
     
        comment.setComment(commentDto.getComment());
        comment.setDateComment(commentDto.getDateComment());
        
        return ResponseEntity.ok(new CommentSessionCreateUpdateResponseDTO(commentService.update(comment)));
	}
}
