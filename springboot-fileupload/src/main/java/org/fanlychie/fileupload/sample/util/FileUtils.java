package org.fanlychie.fileupload.sample.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileUtils {

    private static final int BUFFERED_SIZE = (int) (1 * 1024 * 1024 * 0.5);

    private static File directory = new File("c:/upload");

    static {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void write(MultipartFile file) throws IOException {
        try (InputStream in = file.getInputStream();
             OutputStream os = new FileOutputStream(new File(directory, file.getOriginalFilename()))) {
            int size;
            byte[] buffer = new byte[BUFFERED_SIZE];
            while ((size = in.read(buffer)) != -1) {
                os.write(buffer, 0, size);
            }
            os.flush();
        }
    }

}