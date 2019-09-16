package org.fanlychie.filedownload.sample.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileDownloadApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void before() throws IOException {
        File file = new File("c:/upload/test.txt");
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writer.write("Hello World.");
            writer.flush();
        }
    }

    @Test
    public void testFiledownload1() throws Throwable {
        mvc.perform(get("/filedownload1").param("filename", "test.txt"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFiledownload2() throws Throwable {
        mvc.perform(get("/filedownload2").param("filename", "test.txt"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}