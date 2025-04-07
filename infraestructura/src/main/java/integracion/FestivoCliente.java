package calendario.api.infraestructura.integracion;

import calendario.api.dominio.dto.FestivoExternoDTO;
import calendario.api.dominio.entidades.Calendario;
import calendario.api.dominio.entidades.Tipo;
import calendario.api.infraestructura.repositorios.ICalendarioRepositorio;
import calendario.api.infraestructura.repositorios.ITipoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FestivoCliente {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ICalendarioRepositorio calendarioRepositorio;

    @Autowired
    private ITipoRepositorio tipoRepositorio;

    public boolean poblarCalendario(int anio) {
        try {
            // Obtiene festivos desde la API externa
            String url = "http://localhost:3030/festivos/anio/" + anio;
            ResponseEntity<List<FestivoExternoDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<FestivoExternoDTO>>() {
                    });

            List<FestivoExternoDTO> externos = response.getBody();
            if (externos == null)
                externos = new ArrayList<>();

            // Busca tipos desde la BD
            Tipo tipoFestivo = tipoRepositorio.findByTipoContainingIgnoreCase("festivo")
                    .stream().findFirst().orElse(null);
            Tipo tipoLaboral = tipoRepositorio.findByTipoContainingIgnoreCase("laboral")
                    .stream().findFirst().orElse(null);
            Tipo tipoFinSemana = tipoRepositorio.findByTipoContainingIgnoreCase("fin de semana")
                    .stream().findFirst().orElse(null);

            if (tipoFestivo == null || tipoLaboral == null || tipoFinSemana == null) {
                System.err.println("Faltan tipos 'festivo', 'laboral' o 'fin de semana' en la tabla 'tipo'.");
                return false;
            }

            // Converte lista de festivos en Set de fechas
            List<Calendario> calendarios = new ArrayList<>();
            Set<String> fechasFestivas = new HashSet<>();
            for (FestivoExternoDTO externo : externos) {
                Calendar c = Calendar.getInstance();
                c.set(externo.getAnio(), externo.getMes() - 1, externo.getDia());
                Date fecha = c.getTime();
                fechasFestivas.add(formatoFecha(fecha));

                // Crea y guarda cada festivo
                Calendario cal = new Calendario();
                cal.setFecha(fecha);
                cal.setDescripcion(externo.getNombre());
                cal.setTipo(tipoFestivo);
                calendarios.add(cal);
            }

            // Recorre los días del año y clasifica aquellos que no son festivos
            Calendar calendario = Calendar.getInstance();
            calendario.set(anio, Calendar.JANUARY, 1);

            while (calendario.get(Calendar.YEAR) == anio) {
                Date fecha = calendario.getTime();
                String clave = formatoFecha(fecha);

                // Si no es festivo, los clasifica como laboral o fin de semana
                if (!fechasFestivas.contains(clave)) {
                    int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
                    Calendario cal = new Calendario();
                    cal.setFecha(fecha);

                    if (diaSemana == Calendar.SATURDAY || diaSemana == Calendar.SUNDAY) {
                        cal.setTipo(tipoFinSemana);
                        cal.setDescripcion("Fin de semana");
                    } else {
                        cal.setTipo(tipoLaboral);
                        cal.setDescripcion("Día laboral");
                    }

                    calendarios.add(cal);
                }

                calendario.add(Calendar.DAY_OF_YEAR, 1);
            }

            calendarioRepositorio.saveAll(calendarios);
            return true;

        } catch (Exception e) {
            System.err.println("Error al poblar el calendario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private String formatoFecha(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);
        return y + "-" + m + "-" + d;
    }
}
