package calendario.api.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import calendario.api.dominio.entidades.Tipo;

import java.util.List;

@Repository
public interface ITipoRepositorio extends JpaRepository<Tipo, Long> {

      List<Tipo> findByTipoContainingIgnoreCase(String tipo);

      boolean existsByTipo(String tipo);
}

