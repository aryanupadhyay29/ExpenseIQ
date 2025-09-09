package com.example.ExpenseIQ.controller;

import com.example.ExpenseIQ.model.Group;
import com.example.ExpenseIQ.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/user/{userId}")
    public List<Group> getGroupsByUser(@PathVariable Long userId) {
        return groupService.getGroupsByUserId(userId);
    }

    @GetMapping("/{name}")
    public Group getGroupByName(@PathVariable String name) {
        return groupService.getGroupByName(name);
    }

    @PostMapping("/")
    public Group createGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }
}
