package kr.co.umpabackend.infrastructure.storage;

import kr.co.umpabackend.application.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageCommandLineRunner implements CommandLineRunner {

  private final StorageService storageService;

  @Override
  public void run(String... args) {
    storageService.init();
  }
}
