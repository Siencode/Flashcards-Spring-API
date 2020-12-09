package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}