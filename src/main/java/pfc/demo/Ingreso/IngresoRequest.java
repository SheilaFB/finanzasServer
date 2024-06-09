package pfc.demo.Ingreso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngresoRequest {
    Long idIngreso;
    Double cantidad;
    String descripcion;
    Long categoria_id;
}
