package com.example.ExpenseIQ.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExpenseResponse {


    private Long id;

    private String title;

    private Double amount;

    private String category;

    private LocalDateTime date;

    private String description;

    private Long userId;

    private String userName;

    private Long groupId;

    private String groupName;
}