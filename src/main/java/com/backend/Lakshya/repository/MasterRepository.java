package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Master;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterRepository extends JpaRepository<Master,String> {
    List<Master> findByOwner(String owner);
}
