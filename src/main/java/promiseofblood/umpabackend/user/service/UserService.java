package promiseofblood.umpabackend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.user.dto.CollegeDto;
import promiseofblood.umpabackend.user.dto.MajorDto;
import promiseofblood.umpabackend.user.dto.region.RegionalLocalGovernmentDto;
import promiseofblood.umpabackend.user.repository.CollegeRepository;
import promiseofblood.umpabackend.user.repository.MajorRepository;
import promiseofblood.umpabackend.user.repository.RegionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final MajorRepository majorRepository;
  private final CollegeRepository collegeRepository;
  private final RegionRepository regionRepository;

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
