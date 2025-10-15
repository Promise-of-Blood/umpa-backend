package promiseofblood.umpabackend.application.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteUserCommand {

  @NonNull private final String loginId;

  private final boolean isHardDelete;
}
