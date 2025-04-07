package calendario.api.dominio.entidades;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "calendario")
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idtipo", nullable = false) // Asegura que coincide con la BD
    private Tipo tipo;

    @Column(name = "descripcion")
    private String descripcion;

    // Getters
    public Long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
