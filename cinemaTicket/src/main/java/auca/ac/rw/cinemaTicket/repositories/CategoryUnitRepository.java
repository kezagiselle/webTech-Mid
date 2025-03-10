package auca.ac.rw.cinemaTicket.repositories;


import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryUnitRepository extends JpaRepository<CategoryUnit, UUID> {

    Optional<CategoryUnit> findById(UUID id);  // Corrected the entity type to CategoryUnit
    Optional<CategoryUnit> existsByNameAndDescription(String name, String description);
    boolean existsByName(String name);

    // No need to define save() as it's already inherited from JpaRepository
}




