package com.exam.entity;

import java.util.*;

import javax.persistence.*;

import com.exam.model.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String armyNo;
	private String armyRank;
	private String unit;
	private String trade;
	private boolean enabled = true;
	private String squad;
	private String profile;
	private String role;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;

	@OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<BatchUserMapping> batchUserMappings;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<Authority> set = new HashSet<>();
		String[] split = this.getRole().split(",");
		for (String string : split) {
			set.add(new Authority(string));
		}
		
//		this.userRoles.forEach(userRole -> {
//			set.add(new Authority(userRole.getRole().getRoleName()));
//		});
		return set;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

}
