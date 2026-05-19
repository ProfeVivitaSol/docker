package com.clubdeportivo2.servicioreservas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clubdeportivo2.servicioreservas.model.Reserva;
import com.clubdeportivo2.servicioreservas.model.dto.Cancha;
import com.clubdeportivo2.servicioreservas.repository.ReservaRepository;
import java.util.List;

@Service
public class ReservaServicesImpl implements ReservaServices {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private WebClient webClient;

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    /* 
    @Override
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }*/

    @Override
    public Reserva crearReserva(Reserva reserva) {

        Cancha cancha = webClient
                .get()
                .uri("/api/canchas/" + reserva.getCanchaId())
                .retrieve()
                .bodyToMono(Cancha.class)
                .block();

        if (cancha == null) {
            throw new RuntimeException("La cancha no existe");
        }

        return reservaRepository.save(reserva);
    }


    @Override
    public List<Reserva> buscarReservasPorCancha(Long canchaId) {
        return reservaRepository.findByCanchaId(canchaId);
    }

    

}
