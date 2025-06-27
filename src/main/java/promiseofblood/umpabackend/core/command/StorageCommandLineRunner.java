package promiseofblood.umpabackend.core.command;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.domain.service.StorageService;

@Component
@RequiredArgsConstructor
public class StorageCommandLineRunner implements CommandLineRunner {

  private final StorageService storageService;

  @Override
  public void run(String... args) {
    storageService.init();
  }

}

