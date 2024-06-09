package pfc.demo.Cuenta;

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

    @PostMapping(value = "new")
    public CuentaResponse addCuenta(Authentication authentication, @RequestBody CuentaRequest cuentaRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return cuentaService.addCuenta(cuentaRequest, username);
    }

    @GetMapping(value = "get")
    public CuentaResponse getCuenta(Authentication authentication) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return cuentaService.getCuenta(username);
    }


}