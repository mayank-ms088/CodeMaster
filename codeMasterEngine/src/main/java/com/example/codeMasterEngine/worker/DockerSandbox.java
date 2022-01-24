package com.example.codeMasterEngine.worker;

import com.example.codeMasterEngine.dto.CodeExecutionResponse;
import com.example.codeMasterEngine.dto.TestCase;
import com.example.codeMasterEngine.enums.Compiler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DockerSandbox {
        private static Random rand = new Random(10L);
        private Long timeLimit;

        private final String path = "/home/montooboss/Desktop/GITHUB/CodeMaster/codeMasterEngine/src/main/resources/temp/";

        private final String folder = Integer.toHexString(rand.nextInt());

        private final String vm_name = "codemaster_vm";

        private final String file_name = "test";

        private Compiler compiler;


        private String code;
        private List<TestCase> testCases;

        public CodeExecutionResponse run() throws IOException, InterruptedException {
                prepare();
                execute();
                return null;
        }

        private void createFile(String pathname, String success_msg,String fileContent) throws IOException {

                File codeFile = new File(pathname);
                if(codeFile.createNewFile()){
                        FileWriter writer = new FileWriter(pathname);
                        writer.write(fileContent);
                        writer.close();
                        log.info(success_msg);
                }
                else{
                        log.info("File Already exists");
                }
        }
        private Process execCmd(String cmd) throws IOException {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command("bash","-c",cmd);
                builder.directory(new File(path));
                Process proc = builder.start();
                return proc;
        }
        private void prepare() throws IOException, InterruptedException {
                String codeFile = file_name + compiler.ext;
                String cmd = "mkdir "+ this.folder + " && cp "+ path + "../CompilationScript/* "+ path + folder + " && chmod 744 -R"+ path + folder;

                Process process = execCmd(cmd);
                if(process.waitFor(10, TimeUnit.MILLISECONDS)){
                        log.info(cmd);
                       createFile(path+folder+"/"+codeFile, codeFile + " Successfully created",code);
                       execCmd("chmod 777 '" + path + folder + "/" + file_name + compiler.ext + "'");
                        for (int i=1;i <= testCases.size();i++) {
                                String inputFileName = "input" + "." + String.valueOf(i) + ".txt";
                                String outputFileName = "output" + "." + String.valueOf(i) + ".txt";
                                createFile(path + folder + "/" + inputFileName,inputFileName +  " Successfully created",testCases.get(i-1).getInput());
                                createFile(path + folder + "/" + outputFileName,outputFileName +  " Successfully created",testCases.get(i-1).getExpectedOutput());
                        }
                }
        }

        private CodeExecutionResponse execute() throws IOException, InterruptedException {
                String dockerScript = String.format("chmod 777 -R %1$s && cd %1$s && ./DockerTimeout.sh ", folder,path);
                String codeFile = "./" + file_name + compiler.ext;
                String compileCmd = String.format("\"./compile.sh \\\"%1$s\\\"\"", compiler.compile_cmd);
                String cmd = String.format("%1$s %2$ss -i -t -v %3$s:/usercode -w /usercode %4$s %5$s",dockerScript,"10",path+folder,vm_name,compileCmd);
                log.info(cmd);
                Process process=execCmd(cmd);
                if(process.waitFor(10L,TimeUnit.SECONDS)){
                        BufferedReader stdInput = new BufferedReader(new
                                InputStreamReader(process.getInputStream()));
                        BufferedReader stdError = new BufferedReader(new
                                InputStreamReader(process.getErrorStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                                System.out.println(s);
                        }

                        while ((s = stdError.readLine()) != null) {
                                System.out.println(s);
                        }
                }
                else{
                        log.info("NOT DONE");
                }
                return null;
        }
}
