package promiseofblood.umpabackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.dto.CollegeDto;
import promiseofblood.umpabackend.dto.MajorDto;
import promiseofblood.umpabackend.dto.RegionalLocalGovernmentDto;
import promiseofblood.umpabackend.dto.response.UserResponse;
import promiseofblood.umpabackend.repository.CollegeRepository;
import promiseofblood.umpabackend.repository.MajorRepository;
import promiseofblood.umpabackend.repository.RegionRepository;
import promiseofblood.umpabackend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final MajorRepository majorRepository;
  private final CollegeRepository collegeRepository;
  private final RegionRepository regionRepository;

  public List<UserResponse> getUsers() {

    return userRepository.findAll().stream().map(UserResponse::of).toList();
  }

  public void deleteUser(Long id) {
    
    userRepository.deleteById(id);
  }

  public List<MajorDto> listMajors() {

    return majorRepository.findAll().stream().map(MajorDto::of).toList();
  }

  public List<CollegeDto> listColleges() {

    return collegeRepository.findAll().stream().map(CollegeDto::of).toList();
  }

  public List<RegionalLocalGovernmentDto> listRegions() {

    return regionRepository.findAll().stream().map(RegionalLocalGovernmentDto::of).toList();
  }

}
