package csprng.bbs.controllers;

import csprng.bbs.services.BBSAlgorithm;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class FileController {

    private final BBSAlgorithm bbsAlgorithm;

    @Autowired
    public FileController(BBSAlgorithm bbsAlgorithm) {
        this.bbsAlgorithm = bbsAlgorithm;
    }

    @PostMapping("/")
    public void handleEncryptFileUpload(@RequestParam("fileSize") double fileSize,
                                                        HttpServletResponse response) {

        File file = bbsAlgorithm.calculate(fileSize);
        try {
            InputStream in = new FileInputStream(file);
            response.setContentType(String.valueOf(MediaType.APPLICATION_OCTET_STREAM));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.bin");
            IOUtils.copy(in, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
