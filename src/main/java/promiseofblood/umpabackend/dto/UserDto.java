package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.User;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String name;
  private String profileImageUrl;
  private SocialUserDto socialUser;

  public static UserDto of(User user) {
    return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .socialUser(SocialUserDto.of(user.getSocialUser()))
            .profileImageUrl(user.getProfileImageUrl())
            .build();
  }
}
