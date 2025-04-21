package com.farmer.be.repository;

import com.farmer.be.domain.Admission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AdmissionRepositoryWithBagRelationshipsImpl implements AdmissionRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ADMISSIONS_PARAMETER = "admissions";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Admission> fetchBagRelationships(Optional<Admission> admission) {
        return admission.map(this::fetchBeds);
    }

    @Override
    public Page<Admission> fetchBagRelationships(Page<Admission> admissions) {
        return new PageImpl<>(fetchBagRelationships(admissions.getContent()), admissions.getPageable(), admissions.getTotalElements());
    }

    @Override
    public List<Admission> fetchBagRelationships(List<Admission> admissions) {
        return Optional.of(admissions).map(this::fetchBeds).orElse(Collections.emptyList());
    }

    Admission fetchBeds(Admission result) {
        return entityManager
            .createQuery(
                "select admission from Admission admission left join fetch admission.beds where admission.id = :id",
                Admission.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Admission> fetchBeds(List<Admission> admissions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, admissions.size()).forEach(index -> order.put(admissions.get(index).getId(), index));
        List<Admission> result = entityManager
            .createQuery(
                "select admission from Admission admission left join fetch admission.beds where admission in :admissions",
                Admission.class
            )
            .setParameter(ADMISSIONS_PARAMETER, admissions)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
