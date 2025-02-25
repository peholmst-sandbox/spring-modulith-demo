package com.example.application.invoiceprocessing.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<InvoiceEntity> findByReferenceNumber(String referenceNumber);

    @Query("select i from InvoiceEntity i where not i.paid and i.dueDate < :date")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Stream<InvoiceEntity> findOverdue(LocalDate date);
}
