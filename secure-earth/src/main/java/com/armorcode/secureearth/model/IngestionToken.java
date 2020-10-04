package com.armorcode.secureearth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = "token")
})
@Getter
@Setter
public class IngestionToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String details;

    @NotNull
    @ManyToOne
    private Tenant tenant;

    @NotNull
    @ManyToOne
    private User user;
}
