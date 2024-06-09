package pfc.demo.CategoriaIngreso;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfc.demo.CategoriaGasto.CategoriaGasto;
import pfc.demo.CategoriaGasto.CategoriaGastoRepository;
import pfc.demo.CategoriaGasto.CategoriaGastoRequest;
import pfc.demo.CategoriaGasto.CategoriaGastoResponse;
import pfc.demo.Cuenta.Cuenta;
import pfc.demo.Cuenta.CuentaResponse;
import pfc.demo.Cuenta.CuentaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaIngresoService {

    private final CuentaService cuentaService;
    private final CategoriaIngresoRepository categoriaIngresoRepository;

    /**
     * Añadir una nueva categoría ingreso
     * @param request
     * @param username
     * @return
     */
    public CategoriaIngresoResponse addCategoria(CategoriaIngresoRequest request, String username){
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());
        cuenta.setCantidad(cuentaResponse.getCantidad());
        cuenta.setUser(cuentaResponse.getUser());

        CategoriaIngreso categoriaIngreso = CategoriaIngreso.builder()
                .nombre(request.getNombre())
                .cuenta(cuenta)
                .build();

        categoriaIngresoRepository.save(categoriaIngreso);

        return CategoriaIngresoResponse.builder()
                .id(categoriaIngreso.getId())
                .nombre(categoriaIngreso.getNombre())
                .cuentaId(categoriaIngreso.getCuenta().getId())
                .username(categoriaIngreso.getCuenta().getUser().getUsername())
                .build();

    }

    /**
     * Devuelve una lista de todas las categorías ingreso
     * @param username
     * @return
     */
    public List<CategoriaIngresoResponse> getCategoriasByCuenta(String username) {
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);
        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());

        List<CategoriaIngreso> categoriasIngreso = categoriaIngresoRepository.findByCuenta(cuenta);

        return categoriasIngreso.stream()
                .map(categoriaIngreso -> CategoriaIngresoResponse.builder()
                        .id(categoriaIngreso.getId())
                        .nombre(categoriaIngreso.getNombre())
                        .cuentaId(categoriaIngreso.getCuenta().getId())
                        .username(categoriaIngreso.getCuenta().getUser().getUsername())
                        .build())
                .collect(Collectors.toList());

    }


}
