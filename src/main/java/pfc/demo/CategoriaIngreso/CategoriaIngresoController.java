package pfc.demo.CategoriaIngreso;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pfc.demo.Jwt.JwtService;
import pfc.demo.CategoriaIngreso.CategoriaIngreso;
import pfc.demo.CategoriaIngreso.CategoriaIngresoRequest;
import pfc.demo.CategoriaIngreso.CategoriaIngresoResponse;
import pfc.demo.CategoriaIngreso.CategoriaIngresoService;



import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categoriaingreso")
@RequiredArgsConstructor
public class CategoriaIngresoController {

    private final CategoriaIngresoService categoriaIngresoService;
    private final JwtService jwtService;

    /**
     * Añadir una nueva categoría de ingreso
     * @param authentication
     * @param request
     * @return
     */
    @Operation(summary = "Crear categoría de ingreso", description = "Permite crear una nueva categoría de ingreso para el usuario autenticado.")
    @PostMapping(value = "new")
    public CategoriaIngresoResponse addIngreso(Authentication authentication, @RequestBody CategoriaIngresoRequest request) {
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return categoriaIngresoService.addCategoria(request, username);
    }

    /**
     * Listar todas las categorías de ingreso
     * @param authentication
     * @return
     */
    @Operation(summary = "Listar categoría de ingreso", description = "Lista las categoría de ingreso del usuario autenticado.")
    @GetMapping(value = "all")
    public List<CategoriaIngresoResponse> getAllByCategoria (Authentication authentication){
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return categoriaIngresoService.getCategoriasByCuenta(username);
    }

}
