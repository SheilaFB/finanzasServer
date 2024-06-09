package pfc.demo.Ingreso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pfc.demo.CategoriaIngreso.CategoriaIngreso;
import pfc.demo.Cuenta.Cuenta;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingreso")
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double cantidad;

    @Column(nullable = false)
    private Date fecha;

    @Column()
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    @ManyToOne()
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaIngreso categoriaIngreso;

}
