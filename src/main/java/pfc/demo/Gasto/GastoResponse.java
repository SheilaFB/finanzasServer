package pfc.demo.Gasto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pfc.demo.CategoriaGasto.CategoriaGasto;
import pfc.demo.Cuenta.Cuenta;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GastoResponse {
    Long id;
    Double cantidad;
    Date fecha;
    String descripcion;
    Long id_cuenta;
    CategoriaGasto categoriaGasto;
}
