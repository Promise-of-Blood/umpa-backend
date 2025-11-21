package kr.co.umpabackend.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import kr.co.umpabackend.infrastructure.config.StorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

  private final Path fileLocation;

  @Autowired
  public FileSystemStorageService(StorageConfig storageConfig) {

    if (storageConfig.getFileLocation().trim().isEmpty()) {
      throw new RuntimeException("File upload location can not be Empty.");
    }

    this.fileLocation = Paths.get(storageConfig.getFileLocation());
  }

  @Override
  public String store(MultipartFile file, String... filePaths) {
    if (file.isEmpty() || file.getOriginalFilename() == null) {
      throw new RuntimeException("Empty file");
    }

    String filename =
        StringUtils.cleanPath(
            System.currentTimeMillis() + file.getOriginalFilename().replace(" ", ""));

    Path targetDirectory = fileLocation;
    for (String pathPart : filePaths) {
      if (pathPart == null || pathPart.contains("..")) {
        throw new RuntimeException("Invalid path segment: " + pathPart);
      }
      targetDirectory = targetDirectory.resolve(pathPart);
    }

    Path destination = targetDirectory.resolve(filename).normalize().toAbsolutePath();

    if (!destination.startsWith(fileLocation.toAbsolutePath())) {
      throw new RuntimeException("Invalid file path traversal attempt");
    }

    try {
      Files.createDirectories(targetDirectory);
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new RuntimeException("File storage failed", e);
    }

    return Paths.get("/" + fileLocation + "/" + String.join("/", filePaths))
        .resolve(filename)
        .toString()
        .replace("\\", "/");
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.fileLocation, 1)
          .filter(path -> !path.equals(this.fileLocation))
          .map(this.fileLocation::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read stored files", e);
    }
  }

  @Override
  public Path load(String filename) {

    return fileLocation.resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read file: " + filename);
      }

    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + filename, e);
    }
  }

  @Override
  public void deleteAll() {

    FileSystemUtils.deleteRecursively(fileLocation.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(fileLocation);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage", e);
    }
  }
}
