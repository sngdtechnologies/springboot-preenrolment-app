package org.ps.pecap.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ps.pecap.web.rest.TestUtil;

class EtatProcedureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatProcedure.class);
        EtatProcedure etatProcedure1 = new EtatProcedure();
        etatProcedure1.setId(1L);
        EtatProcedure etatProcedure2 = new EtatProcedure();
        etatProcedure2.setId(etatProcedure1.getId());
        assertThat(etatProcedure1).isEqualTo(etatProcedure2);
        etatProcedure2.setId(2L);
        assertThat(etatProcedure1).isNotEqualTo(etatProcedure2);
        etatProcedure1.setId(null);
        assertThat(etatProcedure1).isNotEqualTo(etatProcedure2);
    }
}
