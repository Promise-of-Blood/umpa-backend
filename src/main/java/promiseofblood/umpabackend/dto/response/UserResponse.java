package promiseofblood.umpabackend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.College;
import promiseofblood.umpabackend.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserResponse {
  private Long id;
  private String userType;
  private String name;
  private String profileImageUrl;
  private String major;
  private List<String> wantedColleges;
  private String oauth2Provider;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  static public UserResponse of(User user) {
    return UserResponse.builder()
            .id(user.getId())
            .userType(user.getUserType().getName())
            .name(user.getName())
            .profileImageUrl(user.getProfileImageUrl())
            .major(user.getMajor().getName())
            .wantedColleges(
                    user.getWantedColleges().stream()
                            .map(College::getName)
                            .toList()
            )
            .oauth2Provider(user.getSocialUser().getOauth2Provider().getName())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
  }

}
