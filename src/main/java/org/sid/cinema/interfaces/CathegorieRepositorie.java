package org.sid.cinema.interfaces;

import org.sid.cinema.entites.Cathegorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CathegorieRepositorie extends JpaRepository<Cathegorie,Long> {
}
