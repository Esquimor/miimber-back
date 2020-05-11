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
import com.miimber.back.forum.dto.category.CategoryForumCreateRequestDTO;
import com.miimber.back.forum.dto.category.CategoryForumCreateUpdateResponseDTO;
import com.miimber.back.forum.dto.category.CategoryForumMultipleUpdateRequestDTO;
import com.miimber.back.forum.dto.category.CategoryForumReadResponseDTO;
import com.miimber.back.forum.dto.category.CategoryForumTemplateUpdateRequestDTO;
import com.miimber.back.forum.model.CategoryForum;
import com.miimber.back.forum.service.CategoryForumService;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryForumController {
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryForumService categoryService;

	@RequestMapping(value = "/organization/{id}/forum/category/", method = RequestMethod.GET)
	public ResponseEntity<?> readCategories(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(id, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<CategoryForumReadResponseDTO> categoriesResponse = new ArrayList<CategoryForumReadResponseDTO>();
        
        for(CategoryForum category: categoryService.getByOrganization(id)) {
        	categoriesResponse.add(new CategoryForumReadResponseDTO(category));
        }
        return ResponseEntity.ok(categoriesResponse);
	}
	
	@RequestMapping(value = "/organization/{idOrg}/forum/category/{idCat}", method = RequestMethod.GET)
	public ResponseEntity<?> readCategory(@PathVariable Long idOrd, @PathVariable Long idCat) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(idOrd, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new CategoryForumReadResponseDTO(categoryService.get(idCat)));
	}
	
	@RequestMapping(value = "/organization/{id}/forum/category/", method = RequestMethod.POST)
	public ResponseEntity<?> createCategory(@PathVariable Long id, @RequestBody CategoryForumCreateRequestDTO categoryDto) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(id, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!member.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        CategoryForum category = new CategoryForum();
        category.setOrganization(member.getOrganization());
        category.setTitle(categoryDto.getTitle());
        category.setPosition(member.getOrganization().getCategoriesForum().size());
        
        return ResponseEntity.ok(new CategoryForumCreateUpdateResponseDTO(categoryService.create(category)));
	}
	
	@RequestMapping(value = "/organization/{idOrg}/forum/category/{idCat}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCategory(@PathVariable Long idOrg, @RequestBody CategoryForumTemplateUpdateRequestDTO categoryDto, @PathVariable Long idCat) {
		CategoryForum category = categoryService.get(idCat);
		
		if (category == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		if (category.getId() != categoryDto.getId()) {
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

        category.setTitle(categoryDto.getTitle());
        category.setPosition(categoryDto.getPosition());
        
        return ResponseEntity.ok(new CategoryForumCreateUpdateResponseDTO(categoryService.update(category)));
	}
	
	@RequestMapping(value = "/organization/{id}/forum/category/", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMultipleCategory(@PathVariable Long id, @RequestBody CategoryForumMultipleUpdateRequestDTO categoriesDto) throws Exception {
		User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationIdAndByUser(id, user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!member.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        List<CategoryForumCreateUpdateResponseDTO> categoriesResponse = new ArrayList<CategoryForumCreateUpdateResponseDTO>();
        for(CategoryForumTemplateUpdateRequestDTO categoryDto: categoriesDto.getCategories()) {
        	CategoryForum category = categoryService.get(categoryDto.getId());
        	if (category == null) {
    			return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            category.setTitle(categoryDto.getTitle());
            category.setPosition(categoryDto.getPosition());
        	categoriesResponse.add(new CategoryForumCreateUpdateResponseDTO(categoryService.update(category)));
        }
        
        return ResponseEntity.ok(categoriesResponse);
	}
	
	@RequestMapping(value = "/organization/{idOrg}/forum/category/{idCat}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCategory(@PathVariable Long idOrg, @PathVariable Long idCat) throws Exception {
		CategoryForum category = categoryService.get(idCat);
		
		if (category == null) {
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
        
        categoryService.delete(category);
        
        return new ResponseEntity(HttpStatus.OK);
	}
}
