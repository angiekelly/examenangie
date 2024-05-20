package org.e2e.e2e.empleado.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewEmpleadoInfoDto {
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