package pfc.demo.Cuenta;

import org.springframework.data.jpa.repository.JpaRepository;
import pfc.demo.User.User;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByUser(User user);
}
