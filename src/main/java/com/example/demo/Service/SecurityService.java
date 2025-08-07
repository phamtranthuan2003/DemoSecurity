package com.example.demo.Service;

import java.util.List;
import com.example.demo.Entity.SecurityServiceEntity;

public interface SecurityService {
    List<SecurityServiceEntity> findAll();

    SecurityServiceEntity findById(Long id);

    void save(SecurityServiceEntity service);

    void delete(Long id);
}
