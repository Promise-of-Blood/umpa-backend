package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.Role;


@Entity
@Getter
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStampedEntity implements UserDetails {

  // 로그인용 ID, 비밀번호(일반 회원가입)
  @Column(unique = true)
  private String loginId;

  private String password;

  // 닉네임, 성별, 프로필사진
  @Column(unique = true)
  private String username;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String profileImageUrl;

  // 학생 프로필
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "student_profile_id")
  private StudentProfile studentProfile;

  // 선생님 프로필
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

  // 소셜 로그인 유저
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "oauth2_user_id")
  private Oauth2User oauth2User;

  @Enumerated(EnumType.STRING)
  private Role role;

  public User patchDefaultProfile(String username, String profileImageUrl) {
    this.username = username;
    this.profileImageUrl = profileImageUrl;

    return this;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(
      new SimpleGrantedAuthority("ROLE_" + this.getRole().name())
    );
  }

  @Override
  public String getPassword() {

    return password;
  }

  @Override
  public boolean isAccountNonExpired() {

    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {

    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {

    return UserDetails.super.isEnabled();
  }
}
