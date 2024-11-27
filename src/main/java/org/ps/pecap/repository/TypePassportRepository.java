package org.ps.pecap.repository;

import org.ps.pecap.domain.TypePassport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypePassport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePassportRepository extends JpaRepository<TypePassport, Long> {}
