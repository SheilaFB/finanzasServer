package pfc.demo.Gasto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfc.demo.CategoriaGasto.*;
import pfc.demo.Cuenta.Cuenta;
import pfc.demo.Cuenta.CuentaResponse;
import pfc.demo.Cuenta.CuentaService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GastoService {
    private final CuentaService cuentaService;
    private final GastoRepository gastoRepository;
    private final CategoriaGastoRepository categoriaGastoRepository;

    /**
     * Añade un gasto
     * @param request
     * @param username
     * @return
     */
    public GastoResponse addGasto(GastoRequest request, String username) {

        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);


        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());
        cuenta.setCantidad(cuentaResponse.getCantidad() - request.getCantidad());
        cuenta.setUser(cuentaResponse.getUser());


        Optional<CategoriaGasto> categoriaGastoOptional = categoriaGastoRepository.findById(request.categoria_id);

        CategoriaGasto categoriaGasto = null;
        if (categoriaGastoOptional.isPresent()) {
            categoriaGasto = categoriaGastoOptional.get();
        } else {
            throw new RuntimeException("Categoría no encontrada para el nuevo ingreso");
        }

        Gasto gasto = Gasto.builder()
                .cantidad(request.getCantidad())
                .fecha(new Date())
                .descripcion(request.getDescripcion())
                .cuenta(cuenta)
                .categoriaGasto(categoriaGasto)
                .build();

        gastoRepository.save(gasto);

        cuentaService.actualizarCantidadCuenta(username, cuentaResponse.getCantidad() - request.getCantidad());

        return GastoResponse.builder()
                .id(gasto.getId())
                .fecha(gasto.getFecha())
                .descripcion(gasto.getDescripcion())
                .cantidad(gasto.getCantidad())
                .categoriaGasto(gasto.getCategoriaGasto())
                .id_cuenta(gasto.getCuenta().getId())
                .build();
    }


    /**
     * Devuelve todos los gastos de una cuenta
     * @param username
     * @return
     */
    public List<GastoResponse> getGastosByCuenta(String username) {
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);
        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());

        List<Gasto> gastos = gastoRepository.findByCuenta(cuenta);

        return gastos.stream()
                .map(gasto -> GastoResponse.builder()
                        .id(gasto.getId())
                        .cantidad(gasto.getCantidad())
                        .fecha(gasto.getFecha())
                        .descripcion(gasto.getDescripcion())
                        .categoriaGasto(gasto.getCategoriaGasto())
                        .id_cuenta(gasto.getCuenta().getId())
                        .build())
                .collect(Collectors.toList());

    }

    /**
     * Devuelve todos los gastos de una categoría
     * @param username
     * @param request
     * @return
     */
    public List<GastoResponse> getGastosByCuentaAndCategoria(String username, GastoRequest request ){
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());

        Optional<CategoriaGasto> categoriaGastoOptional = categoriaGastoRepository.findById(request.categoria_id);

        CategoriaGasto categoriaGasto = null;
        if (categoriaGastoOptional.isPresent()) {
            categoriaGasto = categoriaGastoOptional.get();
        } else {
            throw new RuntimeException("Categoría no encontrada");
        }

        List<Gasto> gastos = gastoRepository.findByCuentaAndCategoriaGasto(cuenta, categoriaGasto);

        return gastos.stream()
                .map(gasto -> GastoResponse.builder()
                        .id(gasto.getId())
                        .cantidad(gasto.getCantidad())
                        .fecha(gasto.getFecha())
                        .descripcion(gasto.getDescripcion())
                        .categoriaGasto(gasto.getCategoriaGasto())
                        .id_cuenta(gasto.getCuenta().getId())
                        .build())
                .collect(Collectors.toList());
    }


    public GastoResponse obtenerGasto(GastoRequest request){

        Optional<Gasto> gastoOptional = gastoRepository.findById(request.idGasto);

        System.out.println(request.getIdGasto());

        if (gastoOptional.isEmpty()) {
            throw new RuntimeException("Gasto no encontrado");
        }

        Gasto gasto = gastoOptional.get();

        return GastoResponse.builder()
                .id(gasto.getId())
                .fecha(gasto.getFecha())
                .descripcion(gasto.getDescripcion())
                .cantidad(gasto.getCantidad())
                .categoriaGasto(gasto.getCategoriaGasto())
                .id_cuenta(gasto.getCuenta().getId())
                .build();

    }


    /**
     * Actualizar un gasto
     * @param request
     * @return
     */
    public GastoResponse actualizarGasto(String username,GastoRequest request){

        Optional<Gasto> gastoOptional = gastoRepository.findById(request.idGasto);

        System.out.println(request.getIdGasto());

        if (gastoOptional.isEmpty()) {
            throw new RuntimeException("Gasto no encontrado");
        }

        Gasto gasto = gastoOptional.get();

        //Actualiza la descripción
        if (request.getDescripcion()!=null && !Objects.equals(gasto.getDescripcion(), request.getDescripcion())){
            gasto.setDescripcion(request.getDescripcion());
        }

        //Actualiza la categoría
        if (request.getCategoria_id()!= null &&!Objects.equals(gasto.getCategoriaGasto().getId(), request.getCategoria_id())){
            Optional<CategoriaGasto> categoriaGastoOptional = categoriaGastoRepository.findById(request.categoria_id);
            CategoriaGasto categoriaGasto = null;
            if (categoriaGastoOptional.isPresent()) {
                categoriaGasto = categoriaGastoOptional.get();
            } else {
                throw new RuntimeException("Categoría no encontrada");
            }

            gasto.setCategoriaGasto(categoriaGasto);
        }

        //Actualiza la cantidad
        if (request.getCantidad()!=null && !Objects.equals(gasto.getCantidad(), request.getCantidad())){
            double diferencia = gasto.getCantidad() - request.getCantidad();
            gasto.setCantidad(request.getCantidad());

            CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

            cuentaService.actualizarCantidadCuenta(username, cuentaResponse.getCantidad() + diferencia);

        }

        gastoRepository.save(gasto);

        return GastoResponse.builder()
                .id(gasto.getId())
                .fecha(gasto.getFecha())
                .descripcion(gasto.getDescripcion())
                .cantidad(gasto.getCantidad())
                .categoriaGasto(gasto.getCategoriaGasto())
                .id_cuenta(gasto.getCuenta().getId())
                .build();

    }

    /**
     * Eliminar un gasto
     * @param username
     * @param idGasto
     * @return
     */
    public String eliminarGasto(String username, Long idGasto) {
        try {

            Optional<Gasto> gastoOptional = gastoRepository.findById(idGasto);

            if (gastoOptional.isEmpty()) {
                throw new RuntimeException("Gasto no encontrado");
            }

            Gasto gasto = gastoOptional.get();

            double cantidad = gasto.getCantidad();

            CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

            cuentaService.actualizarCantidadCuenta(username, cuentaResponse.getCantidad() + cantidad);

            gastoRepository.deleteById(idGasto);
            return "Gasto eliminado exitosamente.";
        } catch (Exception e) {
            return "Se produjo un error al intentar eliminar el gasto con el id " + idGasto;
        }
    }


}
