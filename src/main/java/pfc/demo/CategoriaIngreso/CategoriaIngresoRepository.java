package pfc.demo.CategoriaIngreso;

import org.springframework.data.jpa.repository.JpaRepository;
import pfc.demo.Cuenta.Cuenta;

import java.util.List;

public interface CategoriaIngresoRepository extends JpaRepository<CategoriaIngreso, Long> {

    List<CategoriaIngreso> findByCuenta(Cuenta cuenta);


}
