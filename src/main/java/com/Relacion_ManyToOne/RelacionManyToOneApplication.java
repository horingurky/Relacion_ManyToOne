package com.Relacion_ManyToOne;

import com.Relacion_ManyToOne.entities.Proyecto;
import com.Relacion_ManyToOne.entities.Tarea;
import com.Relacion_ManyToOne.repositories.ProyectoRepository;
import com.Relacion_ManyToOne.repositories.TareaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class RelacionManyToOneApplication {

	public static void main(String[] args) {


		ApplicationContext spring = SpringApplication.run(RelacionManyToOneApplication.class, args);

		ProyectoRepository proyectoRepository = spring.getBean(ProyectoRepository.class);
		TareaRepository tareaRepository = spring.getBean(TareaRepository.class);

		Proyecto proyecto1 = new Proyecto("Fuentes de alimentación", "Sustituir fuentes antiguas",
				LocalDate.of(2025, 1, 8), true);
		Proyecto proyecto2 = new Proyecto("Medidor fuerza", "Guia lineal para medir fuerza pull",
				LocalDate.of(2024, 9, 3), true);

		proyectoRepository.save(proyecto1);
		proyectoRepository.save(proyecto2);

		Tarea tarea1 = new Tarea("Título1","descripción1",false,proyecto1);
		Tarea tarea2 = new Tarea("Título2","descripción2",false,proyecto2);
		Tarea tarea3 = new Tarea("Título3","descripción3",false,proyecto1);
		Tarea tarea4 = new Tarea("Título4","descripción4",false,proyecto2);
		Tarea tarea5 = new Tarea("Título5","descripción5",false,proyecto2);

		tareaRepository.saveAll(List.of(tarea1, tarea2, tarea3, tarea4, tarea5));
		// método derivado para buscar proyectos por nombre
		//Proyecto findByNombreIgnoreCase(String nombre);
		String proyectoBuscado = "Fuentes de alimentación";
		Proyecto proyectoEncontrado = proyectoRepository.findByNombreIgnoreCase(proyectoBuscado);
		if(proyectoEncontrado != null){
			System.out.println("El proyecto encontrado es el: " + proyectoEncontrado.getNombre());
		}else {
			System.out.println("El proyecto de nombre '" + proyectoBuscado + "' no se ha encontrado o no existe");
		}
		// método derivado para buscar proyectos por fecha de inicio
		//List<Proyecto> findByFechaInicio(LocalDate fechaInicio);
		LocalDate fechaBuscada = LocalDate.of(2025,1,8);
		List<Proyecto> proyectosPorFecha = proyectoRepository.findByFechaInicio(fechaBuscada);
		if(proyectosPorFecha.isEmpty()){
			System.out.println("No existe un proyecto con fecha de inicio " + fechaBuscada);
		}else{System.out.println("Los proyectos con fecha de inicio " + fechaBuscada + " son:");
			for(Proyecto proyecto : proyectosPorFecha){
				System.out.println(proyecto.getNombre() + " : " + proyecto.getDescripcion());
			}
		}
		// consulta JPQL que encuentre todos los proyectos activos
		//@Query("select p from Proyecto p where p.activo = ?1")
		//List<Proyecto> findByActivo(Boolean activo);
		List<Proyecto> proyectosActivos = proyectoRepository.findByActivo(true);
		if(proyectosActivos.isEmpty()){
			System.out.println("No existen proyectos activos");
		}else {System.out.println("Los proyectos activos son:");
			for(Proyecto proyecto : proyectosActivos){
				System.out.println(proyecto.getNombre() + " : " + proyecto.getDescripcion());
			}
		}
		// método derivado para buscar tareas por título
		//Tarea findByTituloIgnoreCase(String titulo);
		String tituloBuscado = "Título3";
		Tarea tareaEncontrada = tareaRepository.findByTituloIgnoreCase(tituloBuscado);
		if(tareaEncontrada != null){
			System.out.println("La tarea buscada por título '" + tituloBuscado + "': "
					+ tareaEncontrada.getTitulo());
		}

		// método derivado para contar cuántas tareas no están completadas
		//long countByCompletada(Boolean completada);
		Long tareasNoCompletadas = tareaRepository.countByCompletadaFalse();
		System.out.println("Tareas a completar: " + tareasNoCompletadas);


		// consulta JPQL que encuentre todas las tareas tareas que pertenezcan a un mismo proyect
		//@Query("select t from Tarea t where t.proyecto.id = ?1")
		//List<Tarea> findByProyecto_Id(Long id);
		Long idProyecto = proyecto1.getId();
		List<Tarea> tareasProyecto = tareaRepository.findByProyecto_Id(idProyecto);
		if (tareasProyecto.isEmpty()){
			System.out.println("No se han encontrado tareas en el proyecto con id: " + proyecto1.getId());
		}else{System.out.println("Las tareas del proyecto con Título " + proyecto1.getNombre() + " son:");
			for (Tarea tarea : tareasProyecto ) {
				System.out.println(tarea.getTitulo() + ": " +
						(tarea.getCompletada() ? "Completada" : "No completada"));

			}
		}







	}
}