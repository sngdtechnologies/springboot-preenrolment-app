package org.ps.pecap.repository;

import org.ps.pecap.domain.Annee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Annee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnneeRepository extends JpaRepository<Annee, Long> {}
