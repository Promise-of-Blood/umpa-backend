package promiseofblood.umpabackend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.user.dto.CollegeDto;
import promiseofblood.umpabackend.user.dto.MajorDto;
import promiseofblood.umpabackend.user.repository.CollegeRepository;
import promiseofblood.umpabackend.user.repository.MajorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final MajorRepository majorRepository;
  private final CollegeRepository collegeRepository;

  public List<MajorDto> listMajors() {
    return majorRepository.findAll().stream().map(MajorDto::of).toList();
  }

  public List<CollegeDto> listColleges() {
    return collegeRepository.findAll().stream().map(CollegeDto::of).toList();
  }
}
