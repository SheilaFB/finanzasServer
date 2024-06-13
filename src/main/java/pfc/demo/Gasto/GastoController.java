package pfc.demo.Gasto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pfc.demo.Jwt.JwtService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/gasto")
@RequiredArgsConstructor
public class GastoController {

    private final GastoService gastoService;
    private final JwtService jwtService;


    @Operation(summary = "Crear gasto", description = "Permite crear un nuevo gasto para el usuario autenticado.")
    @PostMapping(value = "new")
    public GastoResponse addGasto(Authentication authentication, @RequestBody GastoRequest gastoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.addGasto(gastoRequest, username);
    }

    @Operation(summary = "Obtener gastos", description = "Permite listar todos los gastos del usuario autenticado.")
    @GetMapping(value = "/cuenta")
    public List<GastoResponse> getGastosByCuenta(Authentication authentication) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.getGastosByCuenta(username);
    }

    @Operation(summary = "Obtener gastos por categoría", description = "Permite listar todos los gastos de una categoría del usuario autenticado.")
    @GetMapping(value = "/cuenta/categoria")
    public List<GastoResponse> getGastosByCuentaAndCategoria(Authentication authentication, @RequestBody GastoRequest gastoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.getGastosByCuentaAndCategoria(username, gastoRequest);
    }

    @Operation(summary = "Actualizar gasto", description = "Permite actualizar un gasto del usuario autenticado.")
    @PostMapping(value = "/actualizar")
    public GastoResponse actualizarGasto(Authentication authentication, @RequestBody GastoRequest gastoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.actualizarGasto(username, gastoRequest);
    }

    @Operation(summary = "Eliminar gasto", description = "Permite eliminar un gasto del usuario autenticado.")
    @DeleteMapping(value = "/delete/{idGasto}")
    public String eliminarGasto (Authentication authentication,  @PathVariable Long idGasto){
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.eliminarGasto(username, idGasto);
    }
}
