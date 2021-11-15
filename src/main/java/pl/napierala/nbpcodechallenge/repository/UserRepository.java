package pl.napierala.nbpcodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.napierala.nbpcodechallenge.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
}