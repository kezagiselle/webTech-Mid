package auca.ac.rw.cinemaTicket.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.cinemaTicket.models.UserModel;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByNamesAndEmail(String names, String email);

    Optional<UserModel> findByNames(String names);
}
