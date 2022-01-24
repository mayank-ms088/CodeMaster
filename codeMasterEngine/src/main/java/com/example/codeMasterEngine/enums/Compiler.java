package com.example.codeMasterEngine.enums;

public enum Compiler {
    CPP("g++ -O2 -w -lm","./a.out","GNU C++17",".cpp");

    public final String compile_cmd;
    public final String output_cmd;
    public final String lang;
    public final String ext;

    Compiler(String cmd,String out,String lang,String ext) {
        this.compile_cmd = cmd;
        this.output_cmd = out;
        this.lang = lang;
        this.ext=ext;
    }
}
