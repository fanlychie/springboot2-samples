package org.fanlychie.filedownload.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class FileDownloadController {

    @Autowired
    private ServletContext servletContext;

    private static File directory = new File("c:/upload");

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/filedownload1")
    public ResponseEntity<InputStreamResource> filedownload1(String filename) throws IOException {
        File file = new File(directory + "/" + filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s", file.getName()))
                .contentType(MediaType.parseMediaType(servletContext.getMimeType(file.getName())))
                .contentLength(file.length())
                .body(new InputStreamResource(new FileInputStream(file)));
    }

    @GetMapping("/filedownload2")
    public void filedownload2(HttpServletResponse resonse, String filename) throws IOException {
        File file = new File(directory + "/" + filename);
        resonse.setContentType(servletContext.getMimeType(file.getName()));
        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s", file.getName()));
        resonse.setContentLength((int) file.length());

        int size;
        byte[] buffer = new byte[200 * 1024];

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream bos = new BufferedOutputStream(resonse.getOutputStream())) {
            while ((size = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, size);
            }
        }
    }

}