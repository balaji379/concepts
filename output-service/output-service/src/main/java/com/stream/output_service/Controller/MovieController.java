package com.stream.output_service.Controller;

import com.stream.output_service.GrpcClient.MovieServiceClient;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    MovieServiceClient movieServiceClient;

    @GetMapping("/start-stream")
    public void startstream(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("video/mp4");
        response.setHeader("Content-Dispostion", "inline;filename=\"movie.mp4\"");
        AsyncContext asyncContext = request.startAsync();
        movieServiceClient.streamMovie("vb", asyncContext);
    }

    private static final String VIDEO_DIR = "C:/movies/"; // your path

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> streamVideo(
            @PathVariable String fileName,
            @RequestHeader(value = "Range", required = false) String rangeHeader
    ) throws IOException {

        File videoFile = new File("D:\\movies\\Intense_Study_40Hz_Gamma_Binaural_Beats_to_Increase_Productivi.mp4");
        if (!videoFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        long fileLength = videoFile.length();
        long rangeStart = 0;
        long rangeEnd = fileLength - 1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            rangeStart = Long.parseLong(ranges[0]);
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                rangeEnd = Long.parseLong(ranges[1]);
            }
        }

        if (rangeEnd >= fileLength) {
            rangeEnd = fileLength - 1;
        }

        long contentLength = rangeEnd - rangeStart + 1;

        Path path = videoFile.toPath();
        Resource resource = new UrlResource(path.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "video/mp4");
        headers.add("Accept-Ranges", "bytes");
        headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
        headers.add("Content-Length", String.valueOf(contentLength));

        InputStream inputStream = Files.newInputStream(path);
        inputStream.skip(rangeStart);

        byte[] data = inputStream.readNBytes((int) contentLength);
        inputStream.close();

        return ResponseEntity.status(rangeHeader == null ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(new ByteArrayResource(data));
    }
}
