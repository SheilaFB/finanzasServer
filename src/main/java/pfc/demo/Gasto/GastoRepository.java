package pfc.demo.Gasto;

import org.springframework.data.jpa.repository.JpaRepository;
import pfc.demo.CategoriaGasto.CategoriaGasto;
import pfc.demo.Cuenta.Cuenta;
import pfc.demo.Cuenta.CuentaRequest;

import java.util.List;
import java.util.Optional;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByCuenta(Cuenta cuenta);

    List<Gasto> findByCuentaAndCategoriaGasto(Cuenta cuenta, CategoriaGasto categoriaGasto);
}
