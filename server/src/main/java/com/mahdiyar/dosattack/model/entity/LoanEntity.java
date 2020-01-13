package com.mahdiyar.dosattack.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
public class LoanEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, length = 40, nullable = false, columnDefinition = "varchar(40)")
    private String id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankEntity bank;
    private Long amount;
    @Column(name = "done")
    private boolean done;
}
