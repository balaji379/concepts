package com.stream.movie_service.GrpcService;

import com.google.protobuf.ByteString;
import com.stream.movie_service.DummyResponse;
import com.stream.movie_service.GrpcMovieServieGrpc;
import com.stream.movie_service.MovieRequest;
import com.stream.movie_service.Movieresponse;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.io.File;
import java.io.FileInputStream;

@GrpcService
public class GrpcMovieService extends GrpcMovieServieGrpc.GrpcMovieServieImplBase {
    @Override
    public void streamMovie(MovieRequest request, StreamObserver<Movieresponse> responseObserver) {
        System.out.println("file name is : " + request.getName());
        File videoFile = new File("C:\\Users\\Public\\Downloads\\SSYouTube.online_8K HDR  The Mirror Dimension (Spider-Man No Way Home)  Dolby 5.1_1080p.mp4");
        if (!videoFile.exists()) {
            responseObserver.onCompleted();
            return;
        }
        try {
            FileInputStream inputStream = new FileInputStream(videoFile);
            int byteread = 0;
            byte[] buffer = new byte[262144];
            while ((byteread = inputStream.read(buffer)) != -1) {
                Movieresponse movieresponse = Movieresponse.newBuilder().setChunk(ByteString.copyFrom(buffer, 0, byteread)).build();
                //System.out.println(movieresponse.getChunk().toString());
                responseObserver.onNext(movieresponse);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    @Override
    public void streamMovies(MovieRequest request, StreamObserver<DummyResponse> responseObserver) {
        System.out.println("file name is : " + request.getName());
        File videoFile = new File("C:\\Users\\DELL\\Downloads\\SSYouTube.online_gRPC Microservice JWT Authentication and Authorisation With Spring Security_1080p.mp4");
        if (!videoFile.exists()) {
            responseObserver.onCompleted();
            return;
        }
        try {
            FileInputStream inputStream = new FileInputStream(videoFile);
            int byteread = 0;
            byte[] buffer = new byte[262144];
            while ((byteread = inputStream.read(buffer)) != -1) {
                DummyResponse movieresponse = DummyResponse.newBuilder().setData(buffer.toString()).build();
                System.out.println(movieresponse.getData());
                responseObserver.onNext(movieresponse);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
