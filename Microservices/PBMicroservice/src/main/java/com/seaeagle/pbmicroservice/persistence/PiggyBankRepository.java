package com.seaeagle.pbmicroservice.persistence;

import com.seaeagle.pbmicroservice.entites.PiggyBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiggyBankRepository extends JpaRepository<PiggyBank, Long> {

}
