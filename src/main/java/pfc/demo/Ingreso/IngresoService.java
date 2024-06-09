package pfc.demo.Ingreso;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfc.demo.CategoriaIngreso.CategoriaIngreso;
import pfc.demo.CategoriaIngreso.CategoriaIngresoRepository;
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
public class IngresoService {
    private final CuentaService cuentaService;
    private final IngresoRepository ingresoRepository;
    private final CategoriaIngresoRepository categoriaIngresoRepository;

    /**
     * Añade un ingreso
     * @param request
     * @param username
     * @return
     */
    public IngresoResponse addIngreso(IngresoRequest request, String username) {

        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);


        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());
        cuenta.setCantidad(cuentaResponse.getCantidad() + request.getCantidad());
        cuenta.setUser(cuentaResponse.getUser());


        Optional<CategoriaIngreso> categoriaIngresoOptional = categoriaIngresoRepository.findById(request.categoria_id);

        CategoriaIngreso categoriaIngreso = null;
        if (categoriaIngresoOptional.isPresent()) {
            categoriaIngreso = categoriaIngresoOptional.get();
        } else {
            throw new RuntimeException("Category not found for the account");
        }

        Ingreso ingreso = Ingreso.builder()
                .cantidad(request.getCantidad())
                .fecha(new Date())
                .descripcion(request.getDescripcion())
                .cuenta(cuenta)
                .categoriaIngreso(categoriaIngreso)
                .build();

        ingresoRepository.save(ingreso);

        cuentaService.actualizarCantidadCuenta(username, cuentaResponse.getCantidad() + request.getCantidad());

        return IngresoResponse.builder()
                .id(ingreso.getId())
                .fecha(ingreso.getFecha())
                .descripcion(ingreso.getDescripcion())
                .cantidad(ingreso.getCantidad())
                .categoriaIngreso(ingreso.getCategoriaIngreso())
                .id_cuenta(ingreso.getCuenta().getId())
                .build();
    }


    /**
     * Devuelve todos los ingresos de una cuenta
     * @param username
     * @return
     */
    public List<IngresoResponse> getIngresosByCuenta(String username) {
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);
        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());

        List<Ingreso> ingresos = ingresoRepository.findByCuenta(cuenta);

        return ingresos.stream()
                .map(ingreso -> IngresoResponse.builder()
                        .id(ingreso.getId())
                        .cantidad(ingreso.getCantidad())
                        .fecha(ingreso.getFecha())
                        .descripcion(ingreso.getDescripcion())
                        .categoriaIngreso(ingreso.getCategoriaIngreso())
                        .id_cuenta(ingreso.getCuenta().getId())
                        .build())
                .collect(Collectors.toList());

    }

    /**
     * Devuelve todos los ingresos de una categoría
     * @param username
     * @param request
     * @return
     */
    public List<IngresoResponse> getIngresosByCuentaAndCategoria(String username, IngresoRequest request ){
        CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaResponse.getId());

        Optional<CategoriaIngreso> categoriaingresoOptional = categoriaIngresoRepository.findById(request.categoria_id);

        CategoriaIngreso categoriaIngreso = null;
        if (categoriaingresoOptional.isPresent()) {
            categoriaIngreso = categoriaingresoOptional.get();
        } else {
            throw new RuntimeException("Category not found for the account");
        }

        List<Ingreso> ingresos = ingresoRepository.findByCuentaAndCategoriaIngreso(cuenta, categoriaIngreso);

        return ingresos.stream()
                .map(ingreso -> IngresoResponse.builder()
                        .id(ingreso.getId())
                        .cantidad(ingreso.getCantidad())
                        .fecha(ingreso.getFecha())
                        .descripcion(ingreso.getDescripcion())
                        .categoriaIngreso(ingreso.getCategoriaIngreso())
                        .id_cuenta(ingreso.getCuenta().getId())
                        .build())
                .collect(Collectors.toList());
    }


    /**
     * Obtener un ingreso
     * @param request
     * @return
     */
    public IngresoResponse obtenerIngreso(IngresoRequest request){

        Optional<Ingreso> ingresoOptional = ingresoRepository.findById(request.getIdIngreso());

        if (ingresoOptional.isEmpty()) {
            throw new RuntimeException("Ingreso no encontrado");
        }

        Ingreso ingreso = ingresoOptional.get();

        return IngresoResponse.builder()
                .id(ingreso.getId())
                .fecha(ingreso.getFecha())
                .descripcion(ingreso.getDescripcion())
                .cantidad(ingreso.getCantidad())
                .categoriaIngreso(ingreso.getCategoriaIngreso())
                .id_cuenta(ingreso.getCuenta().getId())
                .build();

    }


    /**
     * Actualizar un ingreso
     * @param request
     * @return
     */
    public IngresoResponse actualizarIngreso(String username,IngresoRequest request){

        Optional<Ingreso> ingresoOptional = ingresoRepository.findById(request.getIdIngreso());

        if (ingresoOptional.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Ingreso ingreso = ingresoOptional.get();

        //Actualiza la descripción
        if (request.getDescripcion()!=null && !Objects.equals(ingreso.getDescripcion(), request.getDescripcion())){
            ingreso.setDescripcion(request.getDescripcion());
        }

        //Actualiza la categoría
        if (request.getCategoria_id()!= null &&!Objects.equals(ingreso.getCategoriaIngreso().getId(), request.getCategoria_id())){
            Optional<CategoriaIngreso> categoriaIngresoOptional = categoriaIngresoRepository.findById(request.categoria_id);
            CategoriaIngreso categoriaIngreso = null;
            if (categoriaIngresoOptional.isPresent()) {
                categoriaIngreso = categoriaIngresoOptional.get();
            } else {
                throw new RuntimeException("Categoría de ingreso no encontrada");
            }

            ingreso.setCategoriaIngreso(categoriaIngreso);
        }

        //Actualiza la cantidad
        if (request.getCantidad()!=null && !Objects.equals(ingreso.getCantidad(), request.getCantidad())){
            double diferencia = ingreso.getCantidad() - request.getCantidad();
            ingreso.setCantidad(request.getCantidad());

            CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

            cuentaService.actualizarCantidadCuenta(username, cuentaResponse.getCantidad() - diferencia);

        }

        ingresoRepository.save(ingreso);

        return IngresoResponse.builder()
                .id(ingreso.getId())
                .fecha(ingreso.getFecha())
                .descripcion(ingreso.getDescripcion())
                .cantidad(ingreso.getCantidad())
                .categoriaIngreso(ingreso.getCategoriaIngreso())
                .id_cuenta(ingreso.getCuenta().getId())
                .build();

    }

    /**
     * Eliminar un Ingreso
     * @param username
     * @param idIngreso
     * @return
     */
    public String eliminarIngreso(String username, Long idIngreso) {
        try {

            Optional<Ingreso> ingresoOptional = ingresoRepository.findById(idIngreso);

            if (ingresoOptional.isEmpty()) {
                throw new RuntimeException("Ingreso no encontrado");
            }

            Ingreso ingreso = ingresoOptional.get();

            double cantidad = ingreso.getCantidad();

            CuentaResponse cuentaResponse = cuentaService.getCuenta(username);

            cuentaService.actualizarCantidadCuenta(username, cuentaResponse.getCantidad() - cantidad);

            ingresoRepository.deleteById(idIngreso);
            return "Ingreso eliminado exitosamente.";
        } catch (Exception e) {
            return "Se produjo un error al intentar eliminar el ingreso con el id " +idIngreso;
        }
    }


}
