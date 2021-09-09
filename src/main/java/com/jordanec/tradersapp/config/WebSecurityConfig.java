package com.jordanec.tradersapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.jordanec.tradersapp.service.TradersUserService;
/*
* ｓｐｒｉｎｇ－ｓｅｃｕｒｉｔｙ　적용의　ｃｏｎｆｉｇ　파일이며，
* 해당　파일은　처음에만　읽는다．
* */
@Configuration
@EnableWebSecurity
/*
	prePostEnabled=true 주면 @PreAuthorize 활성화 시켜준다. (ex_ @PreAuthorize("hasRole('ROLE_USER')"))
	* securedEnabled = true 주면 @Secured 활성화 된다.	(ex_ @Secured("ROLE_USER"))
*/
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	TradersUserService tradersUserService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(tradersUserService)
			.passwordEncoder(passwordEncoder());
	}


	/*
	*	Spring Security
	* 	권한 별 접근 설정
	*
	*
	* */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/","/register", "/login").permitAll()
				.antMatchers("/api/v2/**").authenticated()
			.and()
				.httpBasic()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
				//.csrf().disable();

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}