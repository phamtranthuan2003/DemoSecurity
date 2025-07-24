package com.example.demo.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.SecurityServiceEntity;
import com.example.demo.Repository.SecurityServiceRepository;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final SecurityServiceRepository serviceRepository;

    public SecurityServiceImpl(SecurityServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<SecurityServiceEntity> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public SecurityServiceEntity findById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public void save(SecurityServiceEntity service) {
        serviceRepository.save(service);
    }

    @Override
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
