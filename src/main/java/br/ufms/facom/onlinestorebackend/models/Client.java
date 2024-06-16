package br.ufms.facom.onlinestorebackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "clients")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private Date birthdate;

    private String document1;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
}
