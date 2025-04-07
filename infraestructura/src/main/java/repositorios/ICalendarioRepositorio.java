package calendario.api.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import calendario.api.dominio.entidades.Calendario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICalendarioRepositorio extends JpaRepository<Calendario, Long> {

    List<Calendario> findByFechaBetween(Date inicio, Date fin);

    List<Calendario> findByFechaBetweenAndTipo_Tipo(Date inicio, Date fin, String tipo);

}
