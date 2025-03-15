package auca.ac.rw.cinemaTicket.repositories;


import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryUnitRepository extends JpaRepository<CategoryUnit, UUID> {

    Optional<CategoryUnit> findById(UUID id);  
    boolean existsByNameAndDescription(String name, String description);
    Optional<CategoryUnit> findByNameAndDescription(String name, String description);
   
  
   
   
}




