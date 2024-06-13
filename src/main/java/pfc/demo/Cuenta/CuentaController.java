package pfc.demo.Cuenta;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import pfc.demo.Jwt.JwtService;


@CrossOrigin
@RestController
@RequestMapping("/api/cuenta")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;
    private final JwtService jwtService;

    @Operation(summary = "Crear nueva cuenta", description = "Permite crear una nueva cuenta para el usuario autenticado.")
    @PostMapping(value = "new")
    public CuentaResponse addCuenta(Authentication authentication, @RequestBody CuentaRequest cuentaRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return cuentaService.addCuenta(cuentaRequest, username);
    }

    @Operation(summary = "Obtener cuenta", description = "Permite obtener la cuenta del usuario autenticado.")
    @GetMapping(value = "get")
    public CuentaResponse getCuenta(Authentication authentication) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return cuentaService.getCuenta(username);
    }

    @Operation(summary = "Actualizar cantidad cuenta", description = "Permite actualizar la cantidad de la cuenta del usuario autenticado.")
    @PostMapping(value = "actualizar")
    public CuentaResponse actualizarCuenta(Authentication authentication, @RequestBody CuentaRequest cuentaRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return cuentaService.actualizarCantidadCuenta(username,cuentaRequest.getCantidad());
    }


}