package kr.co.umpabackend.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("storage")
public class StorageConfig {

  private String fileLocation = "upload-dir";

  private String proxyPrefix = "static/upload-dir";
}
