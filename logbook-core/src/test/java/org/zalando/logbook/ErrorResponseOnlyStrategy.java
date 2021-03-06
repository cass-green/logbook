package org.zalando.logbook;

import java.io.IOException;

/**
 * Proof of concept
 */
final class ErrorResponseOnlyStrategy implements Strategy {

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request,
            final Sink sink) {
        // do nothing
    }

    @Override
    public void write(final Correlation correlation, final HttpRequest request, final HttpResponse response,
            final Sink sink) throws IOException {

        if (response.getStatus() >= 400) {
            sink.writeBoth(correlation, request, response);
        }
    }

}
