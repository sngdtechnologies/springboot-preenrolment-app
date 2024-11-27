package org.ps.pecap.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.ps.pecap.domain.Profil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProfilRepositoryWithBagRelationshipsImpl implements ProfilRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Profil> fetchBagRelationships(Optional<Profil> profil) {
        return profil.map(this::fetchAcces);
    }

    @Override
    public Page<Profil> fetchBagRelationships(Page<Profil> profils) {
        return new PageImpl<>(fetchBagRelationships(profils.getContent()), profils.getPageable(), profils.getTotalElements());
    }

    @Override
    public List<Profil> fetchBagRelationships(List<Profil> profils) {
        return Optional.of(profils).map(this::fetchAcces).orElse(Collections.emptyList());
    }

    Profil fetchAcces(Profil result) {
        return entityManager
            .createQuery("select profil from Profil profil left join fetch profil.acces where profil is :profil", Profil.class)
            .setParameter("profil", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Profil> fetchAcces(List<Profil> profils) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, profils.size()).forEach(index -> order.put(profils.get(index).getId(), index));
        List<Profil> result = entityManager
            .createQuery("select distinct profil from Profil profil left join fetch profil.acces where profil in :profils", Profil.class)
            .setParameter("profils", profils)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
