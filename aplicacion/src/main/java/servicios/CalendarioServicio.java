package calendario.api.aplicacion.servicios;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import calendario.api.dominio.entidades.Calendario;
import calendario.api.core.servicios.ICalendarioServicio;
import calendario.api.infraestructura.repositorios.ICalendarioRepositorio;

@Service
public class CalendarioServicio implements ICalendarioServicio {

    private final ICalendarioRepositorio repositorio;

    public CalendarioServicio(ICalendarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Calendario> listar() {
        return repositorio.findAll();
    }

    @Override
    public Calendario obtener(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public List<Calendario> listarPorAnio(int anio) {
        Date inicio = java.sql.Date.valueOf(anio + "-01-01");
        Date fin = java.sql.Date.valueOf(anio + "-12-31");
        return repositorio.findByFechaBetween(inicio, fin);
    }

    public List<Calendario> listarFestivosPorAnio(int anio) {
        Date inicio = java.sql.Date.valueOf(anio + "-01-01");
        Date fin = java.sql.Date.valueOf(anio + "-12-31");
        return repositorio.findByFechaBetweenAndTipo_Tipo(inicio, fin, "Día Festivo");
    }
}
