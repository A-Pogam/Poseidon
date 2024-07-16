package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poseidoncapitalsolution.trading.constant.SqlQuery;
import com.poseidoncapitalsolution.trading.model.User;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Modifying
	@Transactional
	@Query(value = SqlQuery.truncateUserTestTable, nativeQuery = true)
    void resetUserTestTable();
}