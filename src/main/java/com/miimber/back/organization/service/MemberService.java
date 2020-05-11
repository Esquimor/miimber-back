package com.miimber.back.organization.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.organization.repository.MemberRepository;
import com.miimber.back.user.model.User;

@Service
public class MemberService implements IMemberService {

	@Autowired
	private MemberRepository memberRepository;
	@Override
	public Member getMemberByOrganizationIdAndByUser(Long id, User user) {
		return memberRepository.findOneByOrganizationIdAndUser(id, user);
	}

	@Override
	public List<Member> getMemberByOrganizationId(Long id) {
		return memberRepository.findByOrganizationId(id);
	}

	@Override
	public Member getMemberByOrganizationAndByUser(Organization organization, User user) {
		return memberRepository.findOneByOrganizationAndUser(organization, user);
	}
	
	@Override
	public boolean existsMemberByUserAndOrganization(User user, Organization organization) {
		return existsMemberByUserAndOrganization(user, organization);
	}

	@Override
	public Member create(Member entity) {
		return memberRepository.save(entity);
	}

	@Override
	public Member update(Member entity) {
		return memberRepository.save(entity);
	}

	@Override
	public void delete(Member entity) {
		memberRepository.delete(entity);
	}

	@Override
	public Member get(Long id) {
		Optional<Member> member = memberRepository.findById(id);
		if (member.isEmpty()) {
			return null;
		}
		return member.get();
	}

	@Override
	public Member getFirstMemberOwnerForOrganization(Organization organization) {
		return memberRepository.findFirstByOrganizationAndRole(organization, RoleEnum.OWNER);
	}


}
