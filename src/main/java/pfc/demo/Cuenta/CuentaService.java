package pfc.demo.Cuenta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfc.demo.User.User;
import pfc.demo.User.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final UserRepository userRepository;
    private final CuentaRepository cuentaRepository;

    public CuentaResponse addCuenta(CuentaRequest request, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        Cuenta cuenta = Cuenta.builder()
                .cantidad(request.getCantidad())
                .user(user)
                .build();

        cuenta = cuentaRepository.save(cuenta);

        return CuentaResponse.builder()
                .id(cuenta.getId())
                .cantidad(cuenta.getCantidad())
                .user(cuenta.getUser())
                .build();
    }

    public CuentaResponse getCuenta(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        Optional<Cuenta> optionalCuenta = cuentaRepository.findByUser(user);

        if (optionalCuenta.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Cuenta cuenta = optionalCuenta.get();

        return CuentaResponse.builder()
                .id(cuenta.getId())
                .cantidad(cuenta.getCantidad())
                .user(cuenta.getUser())
                .build();
    }

    public void actualizarCantidadCuenta(String username, double nuevaCantidad) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        Optional<Cuenta> optionalCuenta = cuentaRepository.findByUser(user);

        if (optionalCuenta.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Cuenta cuenta = optionalCuenta.get();
        cuenta.setCantidad(nuevaCantidad);

        cuentaRepository.save(cuenta);
    }
}
