package org.e2e.e2e.empleado.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.e2e.e2e.vehicle.dto.VehicleBasicDto;

@Data
public class EmpleadoResponseDto {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 3, max = 50)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 50)
    private String apellido;

    @NotNull
    @Size(min = 3, max = 50)
    private String correo;

    @NotNull
    @Size(min = 3, max = 50)
    private String departamente;
    @NotNull
    @Size(min = 3, max = 50)
    private String fechaingreso;

}
