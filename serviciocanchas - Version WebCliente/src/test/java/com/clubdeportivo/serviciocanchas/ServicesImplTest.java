package com.clubdeportivo.serviciocanchas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.clubdeportivo.serviciocanchas.model.Cancha;
import com.clubdeportivo.serviciocanchas.repository.CanchaRepository;
import com.clubdeportivo.serviciocanchas.services.ServicesImpl;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

public class ServicesImplTest {
    @Mock
    private CanchaRepository canchaRepository;

    @InjectMocks
    private ServicesImpl services;

    private Cancha cancha;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        cancha = new Cancha(
                1L,
                "Cancha Central",
                "Futbol",
                25000
        );
    }

    @Test
    void listarCanchasTest() {

        when(canchaRepository.findAll())
                .thenReturn(Arrays.asList(cancha));

        List<Cancha> resultado = services.listarCanchas();

        assertEquals(1, resultado.size());
        assertEquals("Cancha Central", resultado.get(0).getNombre());

        verify(canchaRepository, times(1)).findAll();
    }

    @Test
    void crearCanchaTest() {

        when(canchaRepository.save(cancha))
                .thenReturn(cancha);

        Cancha resultado = services.crearCancha(cancha);

        assertNotNull(resultado);
        assertEquals("Futbol", resultado.getTipo());

        verify(canchaRepository, times(1)).save(cancha);
    }

    @Test
    void buscarCanchaTest() {

        when(canchaRepository.findById(1L))
                .thenReturn(Optional.of(cancha));

        Cancha resultado = services.buscarCancha(1L);

        assertNotNull(resultado);
        assertEquals(25000, resultado.getPrecioPorHora());

        verify(canchaRepository, times(1)).findById(1L);
    }
}
