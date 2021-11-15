package pl.napierala.nbpcodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.napierala.nbpcodechallenge.entity.BankAccountEntity;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    Optional<BankAccountEntity> findByUserCodeAndAccountNumber(String userCode, String accountNumber);

    List<BankAccountEntity> findByUserCode(String userCode);

    Optional<BankAccountEntity> findByAccountNumber(String accountNumber);

}