package com.wilterson.validation.enums;

public enum ExportLocationOption {

    EMAIL("EMAIL"),

    SFTP("SFTP"),

    FTP("FTP");

    public final String name;

    ExportLocationOption(String name) {
        this.name = name;
    }
}
