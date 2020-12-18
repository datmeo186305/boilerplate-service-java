package com.benit.team.service;

import com.benit.team.dto.member.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(value = "member-service")
public interface MemberFeignService {

	@RequestMapping(method = RequestMethod.GET, value = "/list")
    List<MemberDTO> getListMember();
	
	@RequestMapping(method = RequestMethod.GET, value = "/new-list")
    List<MemberDTO> getNewMembers();
	
}
