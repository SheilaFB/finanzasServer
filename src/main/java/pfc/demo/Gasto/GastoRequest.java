package pfc.demo.Gasto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GastoRequest {
    Long idGasto;
    Double cantidad;
    String descripcion;
    Long categoria_id;
}
