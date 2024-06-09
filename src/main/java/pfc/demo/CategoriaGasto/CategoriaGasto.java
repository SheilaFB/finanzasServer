package pfc.demo.CategoriaGasto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pfc.demo.Cuenta.Cuenta;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria_gasto", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class CategoriaGasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne()
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
}
