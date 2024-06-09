package pfc.demo.Ingreso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pfc.demo.CategoriaIngreso.CategoriaIngreso;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngresoResponse {
    Long id;
    Double cantidad;
    Date fecha;
    String descripcion;
    Long id_cuenta;
    CategoriaIngreso categoriaIngreso;
}
