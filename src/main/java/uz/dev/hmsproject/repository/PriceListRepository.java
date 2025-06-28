package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uz.dev.hmsproject.entity.PriceList;
import uz.dev.hmsproject.exception.EntityNotFoundException;

import java.util.Optional;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    Optional<PriceList> findBySpecialityId(Long specialityId);

    default PriceList findBySpecialityIdOrThrow(Long specialityId) {
        return findBySpecialityId(specialityId).orElseThrow(() -> new EntityNotFoundException("PriceList not found with speciality id: " + specialityId, HttpStatus.NOT_FOUND));
    }

    default PriceList findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("PriceList not found with id: " + id, HttpStatus.NOT_FOUND));
    }

}