package com.gmail.dlwk0807.dagotit.repository;

import com.gmail.dlwk0807.dagotit.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByKey(String key);
}
