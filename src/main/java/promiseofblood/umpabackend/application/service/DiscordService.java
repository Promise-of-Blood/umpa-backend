package promiseofblood.umpabackend.application.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DiscordService {

  @Value("${discord.webhook.url:}")
  private String webhookUrl;

  private final RestTemplate restTemplate;

  public void sendErrorMessage(Exception ex, String path, LocalDateTime errorTime) {
    if (webhookUrl == null || webhookUrl.isEmpty()) {
      return;
    }

    try {
      String formattedTime = errorTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

      List<Map<String, Object>> fields = getFieldsForDiscord(ex, path, formattedTime);

      Map<String, Object> embed = new HashMap<>();
      embed.put("title", "🚨 서버 오류 발생");
      embed.put("description", "UMPA 백엔드에서 예외가 발생했습니다. 자세한 내용은 아래를 참고하세요.");
      embed.put("color", 15158332);
      embed.put("fields", fields);
      embed.put("timestamp", errorTime.toString());

      Map<String, Object> footer = new HashMap<>();
      footer.put("text", "UMPA Backend Error Monitor");
      embed.put("footer", footer);

      List<Map<String, Object>> embeds = new ArrayList<>();
      embeds.add(embed);

      Map<String, Object> payload = new HashMap<>();
      payload.put("embeds", embeds);

      restTemplate.postForObject(webhookUrl, payload, String.class);
    } catch (Exception e) {
      System.out.println("Discord 웹훅 전송 실패: " + e.getMessage());
    }
  }

  private static List<Map<String, Object>> getFieldsForDiscord(
      Exception ex, String path, String formattedTime) {
    List<Map<String, Object>> fields = new ArrayList<>();

    Map<String, Object> pathField = new HashMap<>();
    pathField.put("name", "🔗 요청 경로");
    pathField.put("value", "`" + path + "`");
    pathField.put("inline", false);
    fields.add(pathField);

    Map<String, Object> typeField = new HashMap<>();
    typeField.put("name", "🏷️ 예외 타입");
    typeField.put("value", "`" + ex.getClass().getName() + "`");
    typeField.put("inline", false);
    fields.add(typeField);

    Map<String, Object> errorField = new HashMap<>();
    errorField.put("name", "❌ 예외 메시지");
    String errorMessage = ex.getMessage() != null ? ex.getMessage() : "(메시지 없음)";
    if (errorMessage.length() > 500) {
      errorMessage = errorMessage.substring(0, 500) + "...";
    }
    errorField.put("value", "```\n" + errorMessage + "\n```");
    errorField.put("inline", false);
    fields.add(errorField);

    Map<String, Object> stackTraceField = new HashMap<>();
    stackTraceField.put("name", "📋 Stack Trace (상위 10줄)");
    String stackTrace = getShortStackTrace(ex, 10);
    stackTraceField.put("value", "```java\n" + stackTrace + "\n```");
    stackTraceField.put("inline", false);
    fields.add(stackTraceField);

    Map<String, Object> timeField = new HashMap<>();
    timeField.put("name", "⏰ 발생 시간");
    timeField.put("value", "`" + formattedTime + "`");
    timeField.put("inline", false);
    fields.add(timeField);

    return fields;
  }

  private static String getShortStackTrace(Exception ex, int maxLines) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    String[] lines = sw.toString().split("\n");
    StringBuilder shortStackTrace = new StringBuilder();
    int lineCount = Math.min(lines.length, maxLines);
    for (int i = 0; i < lineCount; i++) {
      if (i > 0) shortStackTrace.append("\n");
      String line = lines[i];
      if (line.length() > 120) line = line.substring(0, 120) + "...";
      shortStackTrace.append(line);
    }
    if (lines.length > maxLines) {
      shortStackTrace.append("\n... (").append(lines.length - maxLines).append("줄 더 있음)");
    }
    return shortStackTrace.toString();
  }
}
