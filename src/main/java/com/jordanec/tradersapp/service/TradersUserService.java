package com.jordanec.tradersapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jordanec.tradersapp.model.*;
import com.jordanec.tradersapp.repository.UserRepository;

@Service
public class TradersUserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;


	/*
	* 사용자가 로그인 할 경우 spring security를 통해 인증 확인을 거치게 된다.
	* userRepository.findOneByUsername(username)를 통해
	* username이 db에 있는지 확인하고 있으면 정보를 가져온다.
	* (정보: id, email, enabled, password, role, username)
	* 여기서 password는 encoding된 형태로 저장되어있다.
	* spring-security를 통해 자체적으로 id, password를 받아
	* 해당 password를 encoding하여 db에 저장된 비밀번호와 일치하면 pass
	* 그렇지 않으면 인증 실패이다.
	* 좀 더 자세히 살펴보면,
	* AbstractUserAuthenticationProvider.class에서
	* this.additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken)authentication);
	* 함수를 통해 비밀번호가 일치하는지 확인
	* （일치할　경우　ｕｎｄｅｆｉｎｅｄ
	* 　불일치　경우　ｅｘｃｅｐｔｉｏｎ　（ｍｅｓｓａｇｅ：　Bad credentials）
	*
	 * */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findOneByUsername(username);
	}
	
}
