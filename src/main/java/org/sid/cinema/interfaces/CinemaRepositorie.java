package org.sid.cinema.interfaces;

import org.sid.cinema.entites.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CinemaRepositorie extends JpaRepository<Cinema,Long> {
}
