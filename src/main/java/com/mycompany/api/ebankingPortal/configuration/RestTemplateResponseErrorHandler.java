package com.mycompany.api.ebankingPortal.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.api.ebankingPortal.exception.*;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.webjars.NotFoundException;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ApiError error = mapper.readValue(httpResponse.getBody(), ApiError.class);

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            throw new ServerException(error.getError());
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new ForbiddenException(error.getError());
            } else if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new BadRequestException(error.getError());
            }
            throw new ServiceProviderException(error.getError());
        }
    }
}
