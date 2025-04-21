package com.farmer.be.repository;

import com.farmer.be.domain.Employee;
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
public class EmployeeRepositoryWithBagRelationshipsImpl implements EmployeeRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String EMPLOYEES_PARAMETER = "employees";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Employee> fetchBagRelationships(Optional<Employee> employee) {
        return employee.map(this::fetchQualifications);
    }

    @Override
    public Page<Employee> fetchBagRelationships(Page<Employee> employees) {
        return new PageImpl<>(fetchBagRelationships(employees.getContent()), employees.getPageable(), employees.getTotalElements());
    }

    @Override
    public List<Employee> fetchBagRelationships(List<Employee> employees) {
        return Optional.of(employees).map(this::fetchQualifications).orElse(Collections.emptyList());
    }

    Employee fetchQualifications(Employee result) {
        return entityManager
            .createQuery(
                "select employee from Employee employee left join fetch employee.qualifications where employee.id = :id",
                Employee.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Employee> fetchQualifications(List<Employee> employees) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, employees.size()).forEach(index -> order.put(employees.get(index).getId(), index));
        List<Employee> result = entityManager
            .createQuery(
                "select employee from Employee employee left join fetch employee.qualifications where employee in :employees",
                Employee.class
            )
            .setParameter(EMPLOYEES_PARAMETER, employees)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
