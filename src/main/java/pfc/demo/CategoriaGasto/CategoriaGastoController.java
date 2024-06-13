package pfc.demo.CategoriaGasto;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Crear categoría de gasto", description = "Permite crear una nueva categoría de gasto para el usuario autenticado.")
    @PostMapping(value = "new")
    public CategoriaGastoResponse addGasto(Authentication authentication, @RequestBody CategoriaGastoRequest request) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return categoriaGastoService.addCategoria(request, username);
    }

    @Operation(summary = "Listar categoría de gasto", description = "Lista las categoría de gasto del usuario autenticado.")
    @GetMapping(value = "all")
    public List<CategoriaGastoResponse> getAllByCategoria (Authentication authentication){
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return categoriaGastoService.getCategoriasByCuenta(username);
    }

}
