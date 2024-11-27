package org.ps.pecap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ps.pecap.web.rest.TestUtil;

class LogSystemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogSystem.class);
        LogSystem logSystem1 = new LogSystem();
        logSystem1.setId(1L);
        LogSystem logSystem2 = new LogSystem();
        logSystem2.setId(logSystem1.getId());
        assertThat(logSystem1).isEqualTo(logSystem2);
        logSystem2.setId(2L);
        assertThat(logSystem1).isNotEqualTo(logSystem2);
        logSystem1.setId(null);
        assertThat(logSystem1).isNotEqualTo(logSystem2);
    }
}
