package com.vb.grpc_jwt.Jwt;

import io.grpc.*;
import io.jsonwebtoken.Claims;

public class JwtServerInterceptor implements ServerInterceptor {

    private final JwtUtility jwtUtil;

    public JwtServerInterceptor(JwtUtility jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        // 🔹 Extract "authorization" header from metadata
        String authHeader = headers.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            call.close(Status.UNAUTHENTICATED.withDescription("Missing or invalid Authorization header"), headers);
            return new ServerCall.Listener<>() {};
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        // 🔹 Validate the token
        Claims claims = jwtUtil.validateToken(token);
        if (claims == null) {
            call.close(Status.UNAUTHENTICATED.withDescription("Invalid or expired JWT token"), headers);
            return new ServerCall.Listener<>() {};
        }

        // 🔹 You can attach user info to context for downstream services
        Context ctx = Context.current()
                .withValue(Context.key("username"), claims.getSubject())
                .withValue(Context.key("role"), claims.get("role", String.class));

        // ✅ Proceed with the call if token is valid
        return Contexts.interceptCall(ctx, call, headers, next);
    }
}

