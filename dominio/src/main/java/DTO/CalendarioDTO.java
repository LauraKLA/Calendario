package calendario.api.dominio.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CalendarioDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd")
    private Date fecha;
    private String tipo;
    private String descripcion;

    public CalendarioDTO(Long id, Date fecha, String tipo, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
