package org.sid.cinema.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Cinema implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double logitude;
    private double latitude;
    private double altitude;
    private int nombreSalle;
    @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salle;
    @ManyToOne
    private Ville ville;
}
