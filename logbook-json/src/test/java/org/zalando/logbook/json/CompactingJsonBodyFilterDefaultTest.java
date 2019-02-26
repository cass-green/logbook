package org.zalando.logbook.json;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.BodyFilter;

import static java.util.ServiceLoader.load;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;

final class CompactingJsonBodyFilterDefaultTest {

    @Test
    void shouldDeclareCompactingJsonBodyFilterByDefault() {
        assertThat(load(BodyFilter.Default.class), hasItem(instanceOf(CompactingJsonBodyFilter.class)));
    }

}