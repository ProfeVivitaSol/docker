package com.clubdeportivo2.servicioreservas;

import com.clubdeportivo2.servicioreservas.exception.CanchaNotFoundException;
import com.clubdeportivo2.servicioreservas.model.Reserva;
import com.clubdeportivo2.servicioreservas.model.dto.Cancha;
import com.clubdeportivo2.servicioreservas.repository.ReservaRepository;
import com.clubdeportivo2.servicioreservas.services.ReservaServicesImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceImplTest {

     @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ReservaServicesImpl reservaServices;

    private Reserva reserva;

    private Cancha cancha;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        reserva = new Reserva(
                1L,
                1L,
                "Juan Perez",
                LocalDate.now(),
                LocalTime.of(18, 0),
                LocalTime.of(19, 0)
        );

        cancha = new Cancha();
        cancha.setId(1L);
        cancha.setNombre("Cancha Central");
    }

    @Test
    void crearReservaTest() {

        doReturn(requestHeadersUriSpec)
                .when(webClient)
                .get();

        doReturn(requestHeadersSpec)
                .when(requestHeadersUriSpec)
                .uri("/api/canchas/1");

        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.onStatus(any(), any()))
                .thenReturn(responseSpec);

        when(responseSpec.bodyToMono(Cancha.class))
                .thenReturn(Mono.just(cancha));

        when(reservaRepository.save(reserva))
                .thenReturn(reserva);

        Reserva resultado = reservaServices.crearReserva(reserva);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombreCliente());

        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void crearReservaCanchaNoExisteTest() {

        doReturn(requestHeadersUriSpec)
                .when(webClient)
                .get();

        doReturn(requestHeadersSpec)
                .when(requestHeadersUriSpec)
                .uri("/api/canchas/1");

        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);

        when(responseSpec.onStatus(any(), any()))
                .thenReturn(responseSpec);

        when(responseSpec.bodyToMono(Cancha.class))
                .thenReturn(Mono.empty());

        assertThrows(
                CanchaNotFoundException.class,
                () -> reservaServices.crearReserva(reserva)
        );
    }
}
 