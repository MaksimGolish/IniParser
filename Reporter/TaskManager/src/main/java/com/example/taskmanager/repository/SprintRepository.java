package com.example.taskmanager.repository;

import com.example.taskdriver.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, UUID> {
}
