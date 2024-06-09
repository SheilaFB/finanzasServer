package pfc.demo.Gasto;

import java.util.Date;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pfc.demo.CategoriaGasto.CategoriaGasto;
import pfc.demo.Cuenta.Cuenta;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gasto")
public class Gasto {

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
    private CategoriaGasto categoriaGasto;

}
