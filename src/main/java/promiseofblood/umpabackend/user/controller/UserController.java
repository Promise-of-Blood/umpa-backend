package promiseofblood.umpabackend.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import promiseofblood.umpabackend.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "사용자 API")
public class UserController {

  private final UserService userService;
}
