package org.ps.pecap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ps.pecap.web.rest.TestUtil;

class PassportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passport.class);
        Passport passport1 = new Passport();
        passport1.setId(1L);
        Passport passport2 = new Passport();
        passport2.setId(passport1.getId());
        assertThat(passport1).isEqualTo(passport2);
        passport2.setId(2L);
        assertThat(passport1).isNotEqualTo(passport2);
        passport1.setId(null);
        assertThat(passport1).isNotEqualTo(passport2);
    }
}
