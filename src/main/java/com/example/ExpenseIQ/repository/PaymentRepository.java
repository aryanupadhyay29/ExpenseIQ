package com.example.ExpenseIQ.repository;

import com.example.ExpenseIQ.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments made by a user
    List<Payment> findByPayer_Id(Long payerId);

    // Find all payments received by a user
    List<Payment> findByReceiver_Id(Long receiverId);

    // Find payments related to a group
    List<Payment> findByGroup_Id(Long groupId);

    // Find by transactionId
    Payment findByTransactionId(String transactionId);
}
