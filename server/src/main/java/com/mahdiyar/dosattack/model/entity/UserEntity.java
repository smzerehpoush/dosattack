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
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, length = 40, nullable = false, columnDefinition = "varchar(40)")
    private String id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "hashed_password")
    private String hashedPassword;
    @Column(name = "balance", columnDefinition = "default 0")
    private long balance;
    @Column(name = "is_admin")
    private boolean isAdmin;
}
