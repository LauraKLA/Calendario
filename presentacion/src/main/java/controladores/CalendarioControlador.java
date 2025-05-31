package calendario.api.aplicacion.presentacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import calendario.api.dominio.entidades.Calendario;
import calendario.api.dominio.dto.CalendarioDTO;
import calendario.api.core.servicios.ICalendarioServicio;
import calendario.api.infraestructura.integracion.FestivoCliente;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioControlador {

    @Autowired
    private ICalendarioServicio servicio;

    @Autowired
    private FestivoCliente festivoCliente;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<CalendarioDTO> listar() {
        return servicio.listar().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Genera calendario de un año
    @RequestMapping(value = "/generar/{anio}", method = RequestMethod.GET)
public ResponseEntity<String> generarCalendario(@PathVariable int anio) {
   
    if (!servicio.listarPorAnio(anio).isEmpty()) {
        return ResponseEntity.badRequest().body("El calendario para el año " + anio + " ya está generado.");
    }

    boolean resultado = festivoCliente.poblarCalendario(anio);
    if (resultado) {
        return ResponseEntity.ok("Calendario del año " + anio + " generado correctamente.");
    } else {
        return ResponseEntity.status(500).body("Error al generar el calendario para el año " + anio + ".");
    }
}

    // Obtiene festivos de un año
    @RequestMapping(value = "/festivos/obtener/{anio}", method = RequestMethod.GET)
    public List<CalendarioDTO> obtenerFestivosPorAnio(@PathVariable int anio) {
        return servicio.listarFestivosPorAnio(anio).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/listar/{anio}", method = RequestMethod.GET)
    public ResponseEntity<List<CalendarioDTO>> listarPorAnio(@PathVariable int anio) {
        List<Calendario> calendarios = servicio.listarPorAnio(anio);
        List<CalendarioDTO> dtos = calendarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private CalendarioDTO convertirADTO(Calendario entidad) {
        return new CalendarioDTO(
                entidad.getId(),
                entidad.getFecha(),
                entidad.getTipo().getTipo(),
                entidad.getDescripcion());
    }
}
