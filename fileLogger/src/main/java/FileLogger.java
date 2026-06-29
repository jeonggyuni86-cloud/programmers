import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    private final File logDir;
    private final File logFile;
    private final DateTimeFormatter formatter;

    public FileLogger() {
        String home = System.getProperty("user.home");
        this.logDir = new File(home, "Desktop/app-logs");
        this.logFile = new File(logDir, "app.log");
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    void log(LogType type, String message) {
        if(!logDir.exists())
            logDir.mkdirs();
        String line = makeLog(LocalDateTime.now(), type, message);
        try (FileWriter fw = new FileWriter(logFile, true);) {
            fw.write(line);
        } catch(IOException e) {
            System.out.println("로그 기록 실패: " + e.getMessage());
        }
    }


    private String makeLog(LocalDateTime now, LogType type, String message) {
        return "%s [%s] %s%n".formatted(now.format(formatter), type, message);
    }

    public String getLogFilePath() {
        return logFile.getAbsolutePath();
    }
}
