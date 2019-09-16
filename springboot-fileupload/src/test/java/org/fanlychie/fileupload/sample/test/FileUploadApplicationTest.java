package org.fanlychie.fileupload.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testFileupload() throws Throwable {
        mvc.perform(multipart("/fileupload")
                .file(new MockMultipartFile("files", "test.txt",
                        MediaType.MULTIPART_FORM_DATA_VALUE, "Hello World.".getBytes("UTF-8"))))
        .andExpect(status().isOk())
        .andDo(print())
        ;
    }

}