package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.User;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String name;
  private String profileImageUrl;
  private String oneLineDescription;
  private String fullDescription;
  private String url;
  private String major;
  private SocialUserDto socialUser;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static UserDto of(User user) {
    return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .profileImageUrl(user.getProfileImageUrl())
            .oneLineDescription(user.getOneLineDescription())
            .fullDescription(user.getFullDescription())
            .url(user.getUrl())
            .major(user.getMajor().getName())
            .socialUser(SocialUserDto.of(user.getSocialUser()))
            .build();
  }
}
