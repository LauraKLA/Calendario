package calendario.api.core.servicios;

import java.util.Date;
import java.util.List;

import calendario.api.dominio.entidades.Calendario;

public interface ICalendarioServicio {

    public List<Calendario> listar();

    public Calendario obtener(Long id);

    List<Calendario> listarPorAnio(int anio);

    List<Calendario> listarFestivosPorAnio(int anio);

}




