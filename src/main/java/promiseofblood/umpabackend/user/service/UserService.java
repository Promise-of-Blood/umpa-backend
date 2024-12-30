package promiseofblood.umpabackend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.user.dto.UniversityDto;
import promiseofblood.umpabackend.user.repository.UniversityRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UniversityRepository universityRepository;

  public List<UniversityDto> listUniversities() {
    return universityRepository.findAll().stream().map(UniversityDto::of).toList();
  }
}
