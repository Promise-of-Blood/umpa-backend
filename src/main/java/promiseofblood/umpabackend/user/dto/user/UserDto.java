package promiseofblood.umpabackend.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String name;
  private String profileImageUrl;

  public static UserDto of(promiseofblood.umpabackend.user.entitiy.User user) {
    return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .profileImageUrl(user.getProfileImageUrl())
            .build();
  }
}
