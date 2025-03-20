package com.prgrms.be.intermark.domain.newerd.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserTobe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Length(min = 2, max = 25)
	@NotBlank
	@Column(name = "nickname", nullable = false, length = 25)
	private String nickname;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "social_type", nullable = false)
	private SocialType socialType;

	@NotBlank
	@Column(name = "social_id", nullable = false, length = 64)
	private String socialId;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", nullable = false, length = 15)
	private UserRole role;

	@Nullable
	@Column(name = "refresh_token", unique = true)
	private String refreshToken;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	@Nullable
	@Column(name = "birth")
	private LocalDate birth;

}
