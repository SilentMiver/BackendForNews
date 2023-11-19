package org.example.exceptions;

public abstract class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public static class HttpClientException extends ServerException {
        public HttpClientException(Throwable cause) {
            super(cause);
        }

        public HttpClientException(String message) {
            super(message);
        }
    }
}
