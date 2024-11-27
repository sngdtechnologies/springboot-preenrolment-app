package org.ps.pecap.repository;

import java.util.List;
import java.util.Optional;
import org.ps.pecap.domain.Profil;
import org.springframework.data.domain.Page;

public interface ProfilRepositoryWithBagRelationships {
    Optional<Profil> fetchBagRelationships(Optional<Profil> profil);

    List<Profil> fetchBagRelationships(List<Profil> profils);

    Page<Profil> fetchBagRelationships(Page<Profil> profils);
}
