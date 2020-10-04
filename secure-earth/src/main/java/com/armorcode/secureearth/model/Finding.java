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
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "findings")
public class Finding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String code;
    private String filename;
    private String issue_confidence;
    private String issue_severity;
    @Column(columnDefinition="TEXT")
    private String issue_text;
    private Long line_number;
    private String more_info;
    private String test_id;
    private String test_name;
    @NotNull
    @ManyToOne
    private IngestionToken ingestionToken;
}
