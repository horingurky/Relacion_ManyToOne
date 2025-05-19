package com.Relacion_ManyToOne.repositories;

import com.Relacion_ManyToOne.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    // método derivado para buscar proyectos por nombre
    Proyecto findByNombreIgnoreCase(String nombre);

    // método derivado para buscar proyectos por fecha de inicio
    List<Proyecto> findByFechaInicio(LocalDate fechaInicio);

    // consulta JPQL que encuentre todos los proyectos activos
    @Query("select p from Proyecto p where p.activo = ?1")
    List<Proyecto> findByActivo(Boolean activo);
}

