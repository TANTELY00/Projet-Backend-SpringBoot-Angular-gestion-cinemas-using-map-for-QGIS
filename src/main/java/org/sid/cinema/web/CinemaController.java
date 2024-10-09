package org.sid.cinema.web;

import lombok.Data;
import org.sid.cinema.entites.Film;
import org.sid.cinema.entites.Ticket;
import org.sid.cinema.interfaces.FilmRepository;
import org.sid.cinema.interfaces.TickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CinemaController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TickerRepository tickerRepository;

    @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] images(@PathVariable(name = "id") Long id) throws Exception{
        Film film = filmRepository.findById(id).get();
        String photoName = film.getPhoto();
        File file = new File(System.getProperty("user.home")+"/images/"+photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TickerForm tickerForm){
            List<Ticket> ticketList = new ArrayList<>();
            tickerForm.getTickets().forEach(idTicket->{
            Ticket ticket = tickerRepository.findById(idTicket).get();
            ticket.setNomClient(tickerForm.getNomClient());
            ticket.setReserve(true);
            tickerRepository.save(ticket);
            ticketList.add(ticket);
       });
            return ticketList;
    }
}

@Data
class  TickerForm{
    private String nomClient;
    private List<Long> tickets = new ArrayList<>();
}
