package com.pm.doctor_service.repository;

import com.pm.doctor_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<User, UUID> {
}
