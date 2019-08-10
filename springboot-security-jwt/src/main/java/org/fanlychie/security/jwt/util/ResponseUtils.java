package org.fanlychie.security.jwt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public final class ResponseUtils {

    public static void writeAsString(HttpServletResponse response, Object obj) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            String text = new ObjectMapper().writeValueAsString(obj);
            PrintWriter writer = response.getWriter();
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("写出消息异常", e);
        }
    }

}