package org.ps.pecap.repository;

import org.ps.pecap.domain.LogSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LogSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogSystemRepository extends JpaRepository<LogSystem, Long> {}
