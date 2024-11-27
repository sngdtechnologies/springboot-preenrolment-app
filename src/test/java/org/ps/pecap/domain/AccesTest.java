package org.ps.pecap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ps.pecap.web.rest.TestUtil;

class AccesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acces.class);
        Acces acces1 = new Acces();
        acces1.setId(1L);
        Acces acces2 = new Acces();
        acces2.setId(acces1.getId());
        assertThat(acces1).isEqualTo(acces2);
        acces2.setId(2L);
        assertThat(acces1).isNotEqualTo(acces2);
        acces1.setId(null);
        assertThat(acces1).isNotEqualTo(acces2);
    }
}
