package com.api.client.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUDITORIA")
public class Audit {

    @Transient
    public static final int MAX_DETAILS_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUDITORIA")
    private Long id;

    @Column(name = "DATA_ULTIMO_ACESSO")
    private LocalDate accessLastDate;

    @Column(name = "QTD_ACESSO")
    private Integer qtdAccess;

    @Column(name = "DETALHES", length = MAX_DETAILS_LENGTH)
    private String details;

    @ManyToOne
    @JoinColumn(name = "ID_FUNCIONARIO")
    private Staff staff;
}
