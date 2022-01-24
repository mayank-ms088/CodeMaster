package com.codeMaster.codeMasterAPI.enums;


public enum CodeLanguage {
    CPP17("GNU C++17"), JAVA("java"), PYTHON("python3");

    private String value;
    private CodeLanguage(String s) {
        this.value = s;
    }
}
