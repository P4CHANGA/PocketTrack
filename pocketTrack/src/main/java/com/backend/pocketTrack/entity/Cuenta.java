package com.backend.pocketTrack.entity;

import com.backend.pocketTrack.enums.Moneda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "divisa", nullable = false)
    @Enumerated(EnumType.STRING)
    private Moneda divisa;

    @Column(nullable = false)
    private Double cantidad;

    @Column(name = "nombre", nullable = false,unique = true)
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Gastos> gastos = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
