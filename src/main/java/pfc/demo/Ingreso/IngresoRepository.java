package pfc.demo.Ingreso;

import org.springframework.data.jpa.repository.JpaRepository;
import pfc.demo.CategoriaIngreso.CategoriaIngreso;
import pfc.demo.Cuenta.Cuenta;

import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    List<Ingreso> findByCuenta(Cuenta cuenta);

    List<Ingreso> findByCuentaAndCategoriaIngreso(Cuenta cuenta, CategoriaIngreso categoriaIngreso);
}
