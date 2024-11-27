package org.ps.pecap.repository;

import org.ps.pecap.domain.Acces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Acces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccesRepository extends JpaRepository<Acces, Long> {}
