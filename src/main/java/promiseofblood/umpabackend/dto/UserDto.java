package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import promiseofblood.umpabackend.domain.entitiy.User;

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
  private Oauth2UserDto oauth2UserDto;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static UserDto of(User user) {
    // todo
    return null;
  }
}
