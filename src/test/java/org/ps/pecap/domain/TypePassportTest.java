package org.ps.pecap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ps.pecap.web.rest.TestUtil;

class TypePassportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePassport.class);
        TypePassport typePassport1 = new TypePassport();
        typePassport1.setId(1L);
        TypePassport typePassport2 = new TypePassport();
        typePassport2.setId(typePassport1.getId());
        assertThat(typePassport1).isEqualTo(typePassport2);
        typePassport2.setId(2L);
        assertThat(typePassport1).isNotEqualTo(typePassport2);
        typePassport1.setId(null);
        assertThat(typePassport1).isNotEqualTo(typePassport2);
    }
}
