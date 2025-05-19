package promiseofblood.umpabackend.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<UserDto> getUsers() {

    List<UserDto> users = userRepository.findAll()
      .stream()
      .map(UserDto::of)
      .toList();

    return users;
  }

  public void deleteUsers() {
    
    userRepository.deleteAll();
  }
}
