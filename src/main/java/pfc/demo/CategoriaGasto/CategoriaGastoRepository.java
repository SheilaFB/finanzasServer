package pfc.demo.CategoriaGasto;

import org.springframework.data.jpa.repository.JpaRepository;
import pfc.demo.Cuenta.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {

    List<CategoriaGasto> findByCuenta(Cuenta cuenta);


}
