package pfc.demo.Ingreso;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pfc.demo.Gasto.GastoRequest;
import pfc.demo.Gasto.GastoResponse;
import pfc.demo.Jwt.JwtService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/ingreso")
@RequiredArgsConstructor
public class IngresoController {

    private final IngresoService ingresoService;
    private final JwtService jwtService;


    /**
     * Añade un ingreso
     * @param authentication
     * @param ingresoRequest
     * @return
     */
    @PostMapping(value = "new")
    public IngresoResponse addIngreso(Authentication authentication, @RequestBody IngresoRequest ingresoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return ingresoService.addIngreso(ingresoRequest, username);
    }

    /**
     * Obtiene todos los ingresos de una cuenta
     * @param authentication
     * @return
     */
    @GetMapping(value = "/cuenta")
    public List<IngresoResponse> getIngresosByCuenta(Authentication authentication) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return ingresoService.getIngresosByCuenta(username);
    }

    /**
     * Obtiene todos los ingresos de una categoría
     * @param authentication
     * @param ingresoRequest
     * @return
     */
    @GetMapping(value = "/cuenta/categoria")
    public List<IngresoResponse> getIngresosByCuentaAndCategoria(Authentication authentication, @RequestBody IngresoRequest ingresoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return ingresoService.getIngresosByCuentaAndCategoria(username, ingresoRequest);
    }

    /**
     * Actualiza un ingreso
     * @param authentication
     * @param ingresoRequest
     * @return
     */
    @PostMapping(value = "/actualizar")
    public IngresoResponse actualizarIngreso(Authentication authentication, @RequestBody IngresoRequest ingresoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return ingresoService.actualizarIngreso(username, ingresoRequest);
    }

    /**
     * Elimina un ingreso
     * @param authentication
     * @param idIngreso
     * @return
     */
    @DeleteMapping("/delete/{idIngreso}")
    public String eliminarIngreso(Authentication authentication, @PathVariable Long idIngreso){
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return ingresoService.eliminarIngreso(username, idIngreso);
    }
}
