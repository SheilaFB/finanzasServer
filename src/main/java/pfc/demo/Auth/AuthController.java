package pfc.demo.Auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    /**
     * Método para realizar login. Devuelve el token
     * @param request
     * @return
     */
    @Operation(summary = "Inicio de sesión", description = "Permite a un usuario iniciar sesión y obtener un token de autenticación.")
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Método para registrar un usuario. Crea el token.
     * @param request
     * @return
     */
    @Operation(summary = "Registro de usuario", description = "Registra un nuevo usuario y genera un token de autenticación.")
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}
