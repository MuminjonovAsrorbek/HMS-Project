package uz.dev.hmsproject.Specification;


import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.dev.hmsproject.entity.Patient;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by: suhrob
 */



public class PatientSpecification {

    public static Specification<Patient> build(String fullName, String phoneNumber) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fullName != null && !fullName.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%"));
            }

            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
