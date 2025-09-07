package com.example.ExpenseIQ.repository;

import com.example.ExpenseIQ.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Find groups by a specific user (if Group has a relation with User)
    List<Group> findByUsers_Id(Long userId);

    // Optional: Find group by name
    Group findByName(String name);
}