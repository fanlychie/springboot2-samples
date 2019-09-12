package org.fanlychie.fileupload.sample.controller;

import org.fanlychie.fileupload.sample.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by fanlychie on 2019/9/12.
 */
@Controller
public class FileUploadController {

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/fileupload")
    public String fileupload(MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            FileUtils.write(file);
        }
        return "success";
    }

}