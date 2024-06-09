package pfc.demo.CategoriaGasto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pfc.demo.Cuenta.Cuenta;
import pfc.demo.Cuenta.CuentaResponse;
import pfc.demo.Cuenta.CuentaService;
import pfc.demo.Gasto.Gasto;
import pfc.demo.Gasto.GastoResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaGastoService {

    private final CuentaService cuentaService;
    private final CategoriaGastoRepository categoriaGastoRepository;

    public CategoriaGastoResponse addCategoria(CategoriaGastoRequest request, String username){
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());
        cuenta.setCantidad(cuentaResponse.getCantidad());
        cuenta.setUser(cuentaResponse.getUser());

        CategoriaGasto categoriaGasto = CategoriaGasto.builder()
                .nombre(request.getNombre())
                .cuenta(cuenta)
                .build();

        categoriaGastoRepository.save(categoriaGasto);

        return CategoriaGastoResponse.builder()
                .id(categoriaGasto.getId())
                .nombre(categoriaGasto.getNombre())
                .cuentaId(categoriaGasto.getCuenta().getId())
                .username(categoriaGasto.getCuenta().getUser().getUsername())
                .build();

    }

    public List<CategoriaGastoResponse> getCategoriasByCuenta(String username) {
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);
        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());

        List<CategoriaGasto> categoriaGastos = categoriaGastoRepository.findByCuenta(cuenta);

        return categoriaGastos.stream()
                .map(categoriaGasto -> CategoriaGastoResponse.builder()
                        .id(categoriaGasto.getId())
                        .nombre(categoriaGasto.getNombre())
                        .cuentaId(categoriaGasto.getCuenta().getId())
                        .username(categoriaGasto.getCuenta().getUser().getUsername())
                        .build())
                .collect(Collectors.toList());

    }


}
