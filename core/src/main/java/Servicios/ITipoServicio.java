package calendario.api.core.servicios;

import java.util.List;

import calendario.api.dominio.entidades.Tipo;

public interface ITipoServicio {

    public List<Tipo> listar();

    public Tipo obtener(Long id);

    public List<Tipo> buscar(String tipo);
}
