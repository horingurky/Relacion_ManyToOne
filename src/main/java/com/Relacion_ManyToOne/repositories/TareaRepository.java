package com.Relacion_ManyToOne.repositories;

import com.Relacion_ManyToOne.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    // método derivado para buscar tareas por título
    Tarea findByTituloIgnoreCase(String titulo);

    // método derivado para contar cuántas tareas no están completadas
    long countByCompletadaFalse();

    // consulta JPQL que encuentre todas las tareas tareas que pertenezcan a un mismo proyect
    @Query("select t from Tarea t where t.proyecto.id = ?1")
    List<Tarea> findByProyecto_Id(Long id);


}
