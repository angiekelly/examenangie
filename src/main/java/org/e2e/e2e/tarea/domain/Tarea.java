package org.e2e.e2e.tarea.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.e2e.e2e.empleado.domain.Empleado;
import org.e2e.e2e.ride.domain.Ride;
import org.e2e.e2e.user.domain.User;
import org.e2e.e2e.vehicle.domain.Vehicle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tarea  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;
	@Column
	private String descripcion;
	@Column
	private String fechalimite;
	@Column
	private String responsable;

}
