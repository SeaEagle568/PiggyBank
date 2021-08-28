package com.seaeagle.pbmicroservice.persistence;

import com.seaeagle.pbmicroservice.entites.PiggyBank;
import com.seaeagle.pbmicroservice.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
