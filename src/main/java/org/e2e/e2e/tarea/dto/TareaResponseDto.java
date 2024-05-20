package org.e2e.e2e.tarea.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TareaResponseDto {
    @NotNull
    private Long id;
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
