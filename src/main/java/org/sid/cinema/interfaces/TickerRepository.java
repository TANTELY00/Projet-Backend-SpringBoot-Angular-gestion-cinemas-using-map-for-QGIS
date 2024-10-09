package org.sid.cinema.interfaces;

import org.sid.cinema.entites.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TickerRepository extends JpaRepository<Ticket,Long> {
}
