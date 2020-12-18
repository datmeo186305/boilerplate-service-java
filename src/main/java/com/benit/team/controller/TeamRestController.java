package com.benit.team.controller;

import com.benit.team.dto.Team;
import com.benit.team.dto.member.MemberDTO;
import com.benit.team.service.MemberFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RefreshScope
@RestController
public class TeamRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamRestController.class);
	
	@Value("${text.greeting:Hello default }")
    private String message;
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private MemberFeignService memberFeignService;
	
	@RequestMapping("/")
	public String home() {
		return message + env.getProperty("server.port");
	}
	
	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
	
	@HystrixCommand
	@GetMapping("/list")
	public List<Team> getAll() {
		List<Team> teams = Arrays.asList(
				new Team(1, "Team 1"),
				new Team(2, "Team 2"),
				new Team(3, "Team 3"));
		return teams;
	}
	
	@HystrixCommand(fallbackMethod = "fallback")
	@RequestMapping("/member/{id}")
	public List<Object> getListMember(@PathVariable final int id) {
		LOGGER.info("Get members ... ");
		
		@SuppressWarnings("unchecked")
		List<Object> members = restTemplate.getForObject("http://member-service/list", List.class);
		LOGGER.debug(members.toString());
		return members;
	}
	
	@RequestMapping("/member-reign/{id}")
	public List<MemberDTO> getListMemberByReign(@PathVariable final int id) {
		LOGGER.info("Get members by reign service ... ");
		
		List<MemberDTO> members = memberFeignService.getNewMembers();
		
		return members;
	}
	
	// a fallback method to be called if failure happened
	public List<Object> fallback(int galleryId, Throwable hystrixCommand) {
		return null;
	}
}
