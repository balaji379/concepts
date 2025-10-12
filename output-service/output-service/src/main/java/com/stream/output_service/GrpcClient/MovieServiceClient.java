package com.stream.output_service.GrpcClient;

import com.stream.movie_service.GrpcMovieServieGrpc;
import com.stream.movie_service.MovieRequest;
import com.stream.movie_service.Movieresponse;
import io.grpc.stub.StreamObserver;
import jakarta.servlet.AsyncContext;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

@Service
public class MovieServiceClient {
    @GrpcClient("movie-service")
    GrpcMovieServieGrpc.GrpcMovieServieStub movieClient;

    public void streamMovie(String movieName, AsyncContext asyncContext) {
        MovieRequest movieRequest = MovieRequest.newBuilder().setName(movieName).build();
        movieClient.streamMovie(movieRequest, new StreamObserver<Movieresponse>() {
            @Override
            public void onNext(Movieresponse movieresponse) {
                OutputStream out = null;
                try {
                    out = asyncContext.getResponse().getOutputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    out.write(movieresponse.getChunk().toByteArray());
                    System.out.println(Arrays.toString(movieresponse.getChunk().toByteArray()));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Arrays.toString(movieresponse.getChunk().toByteArray()));
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error message " + throwable.getCause());
            }

            @Override
            public void onCompleted() {
                System.out.println("response is completed ");
                try {
                    asyncContext.getResponse().getOutputStream().flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                asyncContext.complete();
            }
        });
    }
}
