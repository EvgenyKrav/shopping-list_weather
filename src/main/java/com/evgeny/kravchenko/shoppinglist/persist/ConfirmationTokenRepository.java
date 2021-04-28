package com.evgeny.kravchenko.shoppinglist.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
    Optional<ConfirmationToken> findByUserUsername(String username);
    void deleteByTokenId(long tokenId);
}
