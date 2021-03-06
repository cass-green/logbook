package org.zalando.logbook.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.MockHttpRequest;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@LogbookTest
class FormatStyleDefaultTest {

    @Autowired
    private Logbook logbook;

    @MockBean
    private HttpLogWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        doReturn(true).when(writer).isActive();
    }

    @Test
    void shouldUseJsonFormatter() throws IOException {
        logbook.process(MockHttpRequest.create()).write();

        verify(writer).write(any(Precorrelation.class), argThat(allOf(startsWith("{"), endsWith("}"))));
    }

}
