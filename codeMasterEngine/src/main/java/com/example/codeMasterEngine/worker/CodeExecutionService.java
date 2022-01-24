package com.example.codeMasterEngine.worker;

import com.example.codeMasterEngine.dto.CodeExecutionRequest;
import com.example.codeMasterEngine.dto.CodeExecutionResponse;
import com.example.codeMasterEngine.enums.Compiler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CodeExecutionService {

    public CodeExecutionResponse execute(CodeExecutionRequest codeExecutionRequest) throws IOException, InterruptedException {
        DockerSandbox sandbox = DockerSandbox.builder()
                .code(codeExecutionRequest.getCode())
                .testCases(codeExecutionRequest.getTestCases())
                .compiler(Arrays.stream(Compiler.values()).filter(v->v.lang.equals(codeExecutionRequest.getCodeLanguage())).findFirst().orElseThrow())
                .timeLimit(codeExecutionRequest.getTimeLimit())
                .build();
        return sandbox.run();
    }
}
