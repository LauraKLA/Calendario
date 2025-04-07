package calendario.api.aplicacion.presentacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import calendario.api.dominio.entidades.*;
import calendario.api.core.servicios.*;

@RestController
@RequestMapping("/api/tipo")

public class TipoControlador {

    @Autowired
    private ITipoServicio servicio;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Tipo> listar() {
        return servicio.listar();
    }

    @RequestMapping(value = "/obtener/{id}", method = RequestMethod.GET)
    public Tipo obtener(@PathVariable long id) {
        return servicio.obtener(id);
    }

    @RequestMapping(value = "/buscar/{tipo}", method = RequestMethod.GET)
    public List<Tipo> buscar(@PathVariable String tipo) {
        return servicio.buscar(tipo);
    }

}

