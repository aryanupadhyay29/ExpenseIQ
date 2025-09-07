package com.example.ExpenseIQ.service;

import com.example.ExpenseIQ.model.Group;
import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.repository.GroupRepository;
import com.example.ExpenseIQ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Create group
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    // ✅ Get group by ID
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));
    }

    // ✅ Get all groups
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // ✅ Add user to group
    public Group addUserToGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        group.getMembers().add(user);
        return groupRepository.save(group);
    }
}