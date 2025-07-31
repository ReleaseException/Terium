package cloud.terium.common.command;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum LogType implements Serializable {
    NOTHING(""),
    SCREEN("\u001B[36mSCREEN\u001B[0m: "),
    INFO("\u001B[36mINFO\u001B[0m: "),
    WARNING("\u001B[33mWARNING\u001B[0m: "),
    ERROR("\u001B[31mERROR\u001B[0m: ");

    private final String prefix;

    LogType(String prefix) {
        this.prefix = prefix;
    }
}