package org.sid.cinema.service;

import jakarta.transaction.Transactional;
import org.sid.cinema.entites.*;
import org.sid.cinema.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class ImplementICinemaInitService implements ICinemaInitService{
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepositorie cinemaRepositorie;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private CathegorieRepositorie cathegorieRepository;
    @Autowired
    private TickerRepository tickerRepository;

    @Override
    public void initVille() {
        Stream.of("Antananarivo","Fianarantsoa","Toamasina","Diego").forEach(nameVille->{
            Ville ville = new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });
    }

    @Override
    public void intitCinema() {
        villeRepository.findAll().forEach(ville -> {
            Stream.of("SCOOP DIGITAL","HORIZON","MANA PROD","NOVEGASY").forEach(nameCinema->{
                Cinema cinema = new Cinema();
                cinema.setName(nameCinema);
                cinema.setNombreSalle((int) (3+Math.random()*7));
                cinema.setVille(ville);
                cinemaRepositorie.save(cinema);
            });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepositorie.findAll().forEach(cinema -> {
            for (int i=0 ; i<cinema.getNombreSalle() ; i++){
                Salle salle = new Salle();
                salle.setNom("Salle "+(i+1));
                salle.setCinema(cinema);
                salle.setNombrePlaces((int) (15+Math.random()*20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlace() {
        salleRepository.findAll().forEach(salle -> {
            for(int i=0; i<salle.getNombrePlaces(); i++){
                Place place = new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeance() {
        Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void initCathegorie() {
        Stream.of("Histoire","action","romantique","commedie").forEach(cat->{
            Cathegorie cathegorie = new Cathegorie();
            cathegorie.setName(cat);
            cathegorieRepository.save(cathegorie);
        });
    }

    @Override
    public void initFilm() {
        double[] duree = new double[] {1,1.5,2,3,2.5} ;
        List<Cathegorie> cathegories = cathegorieRepository.findAll();
        Stream.of("MALOK'ILA","ANAY","SEDRA","ILO AINA","ANDREBABE").forEach(f->{
            Film film = new Film();
            film.setTitre(f);
            film.setDuree(duree[new Random().nextInt(duree.length)]);
            film.setPhoto(f.replaceAll(" ","")+".jpg");
            film.setCathegorie(cathegories.get(new Random().nextInt(cathegories.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjection() {
        double[] prix = new double[]{30,50,90,100};
        villeRepository.findAll().forEach(ville -> {
            ville.getCinema().forEach(cinema -> {
                cinema.getSalle().forEach(salle -> {
                    filmRepository.findAll().forEach(film -> {
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setSalle(salle);
                            projection.setFilm(film);
                            projection.setPrix(prix[new Random().nextInt(prix.length)]);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });
                    });
                });
            });
        });
    }

    @Override
    public void initTicket() {
        projectionRepository.findAll().forEach(p->{
            p.getSalle().getPlaces().forEach(place -> {
                Ticket ticket= new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(p.getPrix());
                ticket.setProjection(p);
                ticket.setReserve(false);
                tickerRepository.save(ticket);
            });
        });
    }
}
