package org.review.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Logger {
    public static Logger loggger;
    private LogLevel logLevel = LogLevel.INFO;

    public Logger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Logger() {
    }

    public static Logger getInstance() {
        if (loggger == null) {
            loggger = new Logger();
        }
        return loggger;
    }

    public void info(String message) {
        if (logLevel == LogLevel.INFO) {
            System.out.println(getCurrentTime() + " - [INFO] " + message);
        }
    }

    public void error(String message) {
        if (logLevel == LogLevel.ERROR || logLevel == LogLevel.INFO || logLevel == LogLevel.DEBUG) {
            System.err.println(getCurrentTime() + " - [ERROR] " + message);
        }
    }

    public void debug(String message) {
        switch (logLevel) {
            case DEBUG:
                System.out.println(getCurrentTime() + " - [DEBUG] " + message);
                break;
            case INFO:
                System.out.println(getCurrentTime() + " - [INFO] " + message);
                break;
            case ERROR:
                System.err.println(getCurrentTime() + " - [ERROR] " + message);
                break;
        }
    }



    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }


}
