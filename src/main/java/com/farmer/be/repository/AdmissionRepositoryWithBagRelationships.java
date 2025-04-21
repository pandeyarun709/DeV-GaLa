package com.farmer.be.repository;

import com.farmer.be.domain.Admission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AdmissionRepositoryWithBagRelationships {
    Optional<Admission> fetchBagRelationships(Optional<Admission> admission);

    List<Admission> fetchBagRelationships(List<Admission> admissions);

    Page<Admission> fetchBagRelationships(Page<Admission> admissions);
}
