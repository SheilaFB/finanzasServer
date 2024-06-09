package pfc.demo.Cuenta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pfc.demo.Gasto.Gasto;
import pfc.demo.User.User;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponse {
    Long id;
    Double cantidad;
    User user;
}
