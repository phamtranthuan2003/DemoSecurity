package com.example.demo.Repository;

import com.example.demo.Entity.SecurityServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityServiceRepository extends JpaRepository<SecurityServiceEntity, Long> {
}
