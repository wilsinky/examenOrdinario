package examen.data;

import java.util.ArrayList;
import java.util.List;

import examen.logic.entities.Proyecto;
import examen.logic.entities.Tarea;
import examen.logic.entities.User;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    @XmlElementWrapper(name = "proyectos")
    @XmlElement(name = "proyecto")
    private List<Proyecto> proyectos;

    @XmlElementWrapper(name = "tareas")
    @XmlElement(name = "tarea")
    private List<Tarea> tareas;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users;

    public Data() {
        proyectos = new ArrayList<>();
        tareas = new ArrayList<>();
        users = new ArrayList<>();
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public List<User> getUsers() {
        return users;
    }
}