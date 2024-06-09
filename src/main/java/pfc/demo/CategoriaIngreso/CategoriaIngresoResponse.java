package pfc.demo.CategoriaIngreso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaIngresoResponse {
    Long id;
    String nombre;
    Long cuentaId;
    String username;
}
