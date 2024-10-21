package foot_court.place.ports.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage = getErrorMessage(response);

        StatusConstants status = getStatusConstants(response.status());

        return new ResponseStatusException(HttpStatus.valueOf(status.getCode()), status.getMessage() + errorMessage);
    }

    private StatusConstants getStatusConstants(int statusCode) {
        for (StatusConstants status : StatusConstants.values()) {
            if (status.getCode() == statusCode) {
                return status;
            }
        }
        return StatusConstants.UNKNOWN_ERROR;
    }

    private String getErrorMessage(Response response) {
        try {
            if (response.body() != null) {
                return IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            return StatusConstants.UNKNOWN_ERROR.getMessage();
        }
        return Optional.ofNullable(response.reason()).orElse(StatusConstants.UNKNOWN_ERROR.getMessage());
    }
}