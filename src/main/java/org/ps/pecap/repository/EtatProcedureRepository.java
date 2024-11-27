package org.ps.pecap.repository;

import org.ps.pecap.domain.EtatProcedure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EtatProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatProcedureRepository extends JpaRepository<EtatProcedure, Long> {}
