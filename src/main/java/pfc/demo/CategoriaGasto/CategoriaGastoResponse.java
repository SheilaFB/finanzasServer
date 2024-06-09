package pfc.demo.CategoriaGasto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pfc.demo.Cuenta.Cuenta;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaGastoResponse {
    Long id;
    String nombre;
    Long cuentaId;
    String username;
}
