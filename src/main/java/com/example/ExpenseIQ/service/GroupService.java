package com.example.ExpenseIQ.service;

import com.example.ExpenseIQ.model.Group;
import com.example.ExpenseIQ.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getGroupsByUserId(Long userId) {
        return groupRepository.findByMembers_Id(userId);
    }

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }
}
