package com.api.client.entites;

import com.api.client.enums.Department;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FUNCIONARIO")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FUNCIONARIO")
    private Long id;

    @Column(name = "NOME_COMPLETO")
    private String completeName;

    @Column(name = "CPF")
    private String spokesmanDocument;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DATA_REGISTRO")
    @Temporal(TemporalType.DATE)
    private LocalDate registerDate;

    @Column(name = "ATIVO")
    private boolean isActive;

    @Column(name = "DEPARTAMENTO")
    @Enumerated(EnumType.STRING)
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staff")
    private List<Audit> audits;
}
