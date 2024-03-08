package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByBizNo(String bizNo);

    boolean existsByBizNo(String bizNo);
}
