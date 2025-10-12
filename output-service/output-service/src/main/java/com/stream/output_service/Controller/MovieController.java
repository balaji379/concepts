package com.stream.output_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.output_service.GrpcClient.MovieServiceClient;
import com.stream.output_service.model.Thumbnails;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    MovieServiceClient movieServiceClient;

    List<Thumbnails> thumbnails = new ArrayList<>();

    @PostConstruct
    public void generateThumbnails() throws IOException {
        thumbnails.add(Thumbnails.builder().id(1).name("SSYouTube.online_4K HDR IMAX  Wanda in Mirror Dimension - Doctor Strange in the Multiverse of Madness  Dolby 5.1_1080p").img(Base64.getEncoder().encodeToString(new FileInputStream(new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\SSYouTube.online_4K HDR IMAX  Wanda in Mirror Dimension - Doctor Strange in the Multiverse of Madness  Dolby 5.1_1080p.mp4")).readAllBytes())).build());
        thumbnails.add(Thumbnails.builder().id(1).name("SSYouTube.online_8K HDR  The Mirror Dimension (Spider-Man No Way Home)  Dolby 5.1_1080p").img(Base64.getEncoder().encodeToString(new FileInputStream(new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\SSYouTube.online_8K HDR  The Mirror Dimension (Spider-Man No Way Home)  Dolby 5.1_1080p.mp4")).readAllBytes())).build());
        thumbnails.add(Thumbnails.builder().id(1).name("SSYouTube.online_SPIDER-MAN HOMECOMING Best Action Scenes 4K ᴴᴰ_720p").img(Base64.getEncoder().encodeToString(new FileInputStream(new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\SSYouTube.online_SPIDER-MAN HOMECOMING Best Action Scenes 4K ᴴᴰ_720p.mp4")).readAllBytes())).build());
        thumbnails.add(Thumbnails.builder().id(1).name("SSYouTube.online_SPIDER-MAN HOMECOMING Best Action Scenes 4K ᴴᴰ_720p").img(Base64.getEncoder().encodeToString(new FileInputStream(new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\SSYouTube.online_SPIDER-MAN HOMECOMING Best Action Scenes 4K ᴴᴰ_720p.mp4")).readAllBytes())).build());
        thumbnails.add(Thumbnails.builder().id(1).name("SSYouTube.online_Spring Boot + gRPC Client Streaming Explained \uD83D\uDE80  Real-Time Bulk Stock Updates Demo  @Javatechie_1080p").img(Base64.getEncoder().encodeToString(new FileInputStream(new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\SSYouTube.online_Spring Boot + gRPC Client Streaming Explained \uD83D\uDE80  Real-Time Bulk Stock Updates Demo  @Javatechie_1080p.mp4")).readAllBytes())).build());
        thumbnails.add(Thumbnails.builder().id(1).name("SSYouTube.online_Spring Boot + gRPC  Server Streaming Explained  Real-Time Stock Update Example @Javatechie_1080p").img(Base64.getEncoder().encodeToString(new FileInputStream(new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\SSYouTube.online_Spring Boot + gRPC  Server Streaming Explained  Real-Time Stock Update Example @Javatechie_1080p.mp4")).readAllBytes())).build());
        thumbnails.add(Thumbnails.builder().id(1).name("SSYouTube.online_SPIDER-MAN HOMECOMING Best Action Scenes 4K ᴴᴰ_720p").img(Base64.getEncoder().encodeToString(new FileInputStream(new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\SSYouTube.online_SPIDER-MAN HOMECOMING Best Action Scenes 4K ᴴᴰ_720p.mp4")).readAllBytes())).build());
    }

    @GetMapping("/start-thumbnail-stream")
    public StreamingResponseBody startThumbnailsStream(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("i have caught you request");
        return outputStream -> {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            try {
                for (Thumbnails thumbnail : thumbnails) {
                    String json = new ObjectMapper().writeValueAsString(thumbnail);
                    System.out.println("response : " + json);
                    writer.write(json);
                    writer.write("\n");
                    writer.flush();
                    Thread.sleep(2000);
                }

            } catch (Exception e) {
            }
        };
    }



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

        File videoFile = new File("C:\\Users\\DELL\\OneDrive\\Desktop\\vbgithub\\concepts\\output-service\\output-service\\src\\main\\java\\video\\" + fileName);
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

