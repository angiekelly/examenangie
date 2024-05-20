package org.e2e.e2e.empleado.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.e2e.e2e.ride.domain.Ride;
import org.e2e.e2e.user.domain.User;
import org.e2e.e2e.vehicle.domain.Vehicle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Empleado extends User {

    @Column
    private String departamento;

	@Column
	private String fechaingreso;

    @OneToMany(mappedBy = "proyecto")
    private List<Proyecto> proyecto;

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getFechaingreso() {
		return fechaingreso;
	}

	public void setFechaingreso(String fechaingreso) {
		this.fechaingreso = fechaingreso;
	}

	public List<Proyecto> getProyecto() {
		return proyecto;
	}

	public void setProyecto(List<Proyecto> proyecto) {
		this.proyecto = proyecto;
	}
}
