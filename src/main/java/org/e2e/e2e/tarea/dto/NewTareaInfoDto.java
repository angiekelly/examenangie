package org.e2e.e2e.tarea.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewTareaInfoDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String nombre;
    @NotNull
    @Size(min = 2, max = 50)
    private String descripcion;
    @NotNull
    @Size(min = 2, max = 50)
    private String fechalimite;
    @NotNull
    @Size(min = 2, max = 50)
    private String responsable;

}