package com.wildcodeschool.milkshake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.milkshake.entity.Waiter;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Long> {
    
}
