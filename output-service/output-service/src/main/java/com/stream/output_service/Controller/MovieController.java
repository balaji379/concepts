package com.stream.output_service.Controller;

import com.google.common.net.HttpHeaders;
import com.stream.output_service.GrpcClient.MovieServiceClient;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;  // for Spring Boot 3+

import java.io.IOException;

@RestController
@RequestMapping("/api/output-service")
public class MovieController {

    @Autowired
    MovieServiceClient movieServiceClient;

    @GetMapping("/start-stream/{moviename}")
    public void startStream(@PathVariable("moviename") String moviename, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("request id is " + moviename);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(0);
        movieServiceClient.streamMovie(moviename, asyncContext);
    }
}