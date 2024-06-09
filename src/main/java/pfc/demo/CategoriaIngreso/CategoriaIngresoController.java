package pfc.demo.CategoriaIngreso;

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
    @GetMapping(value = "all")
    public List<CategoriaIngresoResponse> getAllByCategoria (Authentication authentication){
        String username = jwtService.getUsernameFromAuthentication(authentication);
        return categoriaIngresoService.getCategoriasByCuenta(username);
    }

}
