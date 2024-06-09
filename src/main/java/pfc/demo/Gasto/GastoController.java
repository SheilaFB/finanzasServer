package pfc.demo.Gasto;

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


    @PostMapping(value = "new")
    public GastoResponse addGasto(Authentication authentication, @RequestBody GastoRequest gastoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.addGasto(gastoRequest, username);
    }

    @GetMapping(value = "/cuenta")
    public List<GastoResponse> getGastosByCuenta(Authentication authentication) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.getGastosByCuenta(username);
    }

    @GetMapping(value = "/cuenta/categoria")
    public List<GastoResponse> getGastosByCuentaAndCategoria(Authentication authentication, @RequestBody GastoRequest gastoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.getGastosByCuentaAndCategoria(username, gastoRequest);
    }

    @PostMapping(value = "/actualizar")
    public GastoResponse actualizarGasto(Authentication authentication, @RequestBody GastoRequest gastoRequest) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.actualizarGasto(username, gastoRequest);
    }

    @DeleteMapping(value = "/delete/{idGasto}")
    public String eliminarGasto (Authentication authentication,  @PathVariable Long idGasto){
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return gastoService.eliminarGasto(username, idGasto);
    }
}
