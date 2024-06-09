package pfc.demo.CategoriaGasto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pfc.demo.Jwt.JwtService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categoriagasto")
@RequiredArgsConstructor
public class CategoriaGastoController {

    private final CategoriaGastoService categoriaGastoService;
    private final JwtService jwtService;

    @PostMapping(value = "new")
    public CategoriaGastoResponse addGasto(Authentication authentication, @RequestBody CategoriaGastoRequest request) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return categoriaGastoService.addCategoria(request, username);
    }

    @GetMapping(value = "all")
    public List<CategoriaGastoResponse> getAllByCategoria (Authentication authentication){
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return categoriaGastoService.getCategoriasByCuenta(username);
    }

}
