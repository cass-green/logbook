package org.zalando.logbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JsonCompactingBodyFilterTest {

    private final BodyFilter unit = new JsonCompactingBodyFilter(new ObjectMapper());

    /*language=JSON*/
    private final String pretty = "{\n" +
            "  \"root\": {\n" +
            "    \"child\": \"text\"\n" +
            "  }\n" +
            "}";

    /*language=JSON*/
    private final String compacted = "{\"root\":{\"child\":\"text\"}}";

    @Test
    void shouldIgnoreEmptyBody() {
        final String filtered = unit.filter("application/json", "");
        assertThat(filtered, is(""));
    }

    @Test
    void shouldIgnoreInvalidContent() {
        final String invalidBody = "{\ninvalid}";
        final String filtered = unit.filter("application/json", invalidBody);
        assertThat(filtered, is(invalidBody));
    }

    @Test
    void shouldIgnoreInvalidContentType() {
        final String filtered = unit.filter("text/plain", pretty);
        assertThat(filtered, is(pretty));
    }

    @Test
    void shouldTransformValidJsonRequestWithSimpleContentType() {
        final String filtered = unit.filter("application/json", pretty);
        assertThat(filtered, is(compacted));
    }

    @Test
    void shouldTransformValidJsonRequestWithCompatibleContentType() {
        final String filtered = unit.filter("application/custom+json", pretty);
        assertThat(filtered, is(compacted));
    }

    @Test
    void shouldSkipInvalidJsonLookingLikeAValidOne() {
        final String invalidJson = "{invalid}";
        final String filtered = unit.filter("application/custom+json", invalidJson);
        assertThat(filtered, is(invalidJson));
    }

}