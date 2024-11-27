package org.ps.pecap.repository;

import org.ps.pecap.domain.Passport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Passport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {}
