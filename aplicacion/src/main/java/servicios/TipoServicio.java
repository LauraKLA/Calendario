package calendario.api.aplicacion.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import calendario.api.dominio.entidades.Tipo;
import calendario.api.core.servicios.ITipoServicio;
import calendario.api.infraestructura.repositorios.ITipoRepositorio;

@Service
public class TipoServicio implements ITipoServicio {

    private final ITipoRepositorio repositorio;

    public TipoServicio(ITipoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Tipo> listar() {
        return repositorio.findAll();
    }

    @Override
    public Tipo obtener(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public List<Tipo> buscar(String tipo) {
        return repositorio.findByTipoContainingIgnoreCase(tipo);
    }
}