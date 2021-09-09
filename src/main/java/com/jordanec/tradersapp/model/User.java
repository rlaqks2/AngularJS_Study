package com.jordanec.tradersapp.model;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/*
*	@Entity: database 에 저장하기 위해 user 가 정의한 class (domain 이라고도 한다.)
* 	@Id: primary key 를 가지는 변수를 선언하는 것
* 	@Table: 실제 database table 이름과 @Entity 가 일치해야하며,
* 			다를 경우 @Table(name="") 이렇게 사용해도 된다.
* 	@Column: table column 명과 다르게 사용하고 싶으면, @Column(name="")으로 사용한다.
* 	
* 	method: sava(): 레코드 저장, findOne(): primary key 로 한 건 찾기,
* 			findAll(): 전체 레고드 가져오기(정렬, 페이징 가능), count(): 레코드 갯수, delete(): 레코드 삭제,
* 			findBy로 시작: 쿼리를 요청하는 메서드 임을 알림, countBy로 시작: 쿼리 결과 레코드 수를 요청하는 메소드
* 			쿼리 메소드에 포함한 키워드: findBy를 사용
* 			(And, Or, Between, LessThan, GreaterThanEqual, Like, IsNull, In, OrderBy)
*
* 	Pageable 에서는 파라미터를 자동 수집: page: 몇번 째 page 인지 전달, size: 한 page 에 몇개의 항목을 보여줄 것인지,
* 									sort: 정렬정보를 전달 (정렬정보는 필드이름, 정렬방향의 포맷으로 전달)
*/
@Entity
@Table(name = "\"User\"")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",scope=User.class)
public class User extends BaseModel implements Serializable, UserDetails  {
	@JsonIgnore
	private static final long serialVersionUID = 6534751544232619891L;
	public static enum Role {
		ROLE_ADMIN, ROLE_USER
	}
	@Column(unique=true, nullable = false)
	private String username;
    @Column(unique=true, nullable = false)
    private String email;
    @JsonProperty(access=Access.WRITE_ONLY)
    private String password;
	private boolean enabled = true;
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;
	
    public User() {}
    
    public User(String username, String email, String password, Role role) {
    	super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public void setPassword(String password) {
        this.password = password;
    }

	public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	@Override
	public List<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role.name()));
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}