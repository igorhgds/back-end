package br.project.financial.entities;

import br.project.financial.enums.TransactionType;
import br.project.financial.util.TransactionTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = TransactionTypeConverter.class)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "customer")
    private String customer;

    @Column(name = "category")
    private String category;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;


    @Column(name = "branch")
    private String branch;

    @Column(name = "date")
    private LocalDate date;
}
