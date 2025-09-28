package examen.presentation.tablero;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import examen.logic.entities.Tarea;
import examen.presentation.AbstractModel;
import examen.logic.entities.Proyecto;

public class Model extends AbstractModel {
    private Proyecto proyectoActual;
    private List<Proyecto> proyectos;

    private Tarea tareaActual;
    private List<Tarea> tareas;

    public static final String PROYECTO = "proyecto";
    public static final String PROYECTOS = "proyectos";
    public static final String TAREAS = "tareas";
    public static final String TAREA_ACTUAL = "tareaActual";

    public Model() {
        proyectoActual = new Proyecto();
        proyectos = new ArrayList<>();
        tareas = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(PROYECTO);
        firePropertyChange(PROYECTOS);
        firePropertyChange(TAREAS);
    }

    // actualizar current
    public Proyecto getCurrentProyecto() {
        return proyectoActual;
    }

    public void setCurrentProyecto(Proyecto proyecto) {
        this.proyectoActual = proyecto;
        firePropertyChange(PROYECTO);
        firePropertyChange(TAREAS);
    }

    // proyectos
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
        firePropertyChange(PROYECTOS);
    }

    // tareas
    public List<Tarea> getTareas() {
        if (proyectoActual == null || proyectoActual.getTareas() == null || proyectoActual.getTareas().isEmpty()) {
            // No hay proyecto seleccionado o no tiene tareas
            return new ArrayList<>();
        }
        return new ArrayList<>(proyectoActual.getTareas());
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
        firePropertyChange(TAREAS);
    }

    public void addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        firePropertyChange(TAREAS);
    }

    public void removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        firePropertyChange(TAREAS);
    }

    public Tarea getTareaActual() {
        return tareaActual;
    }

    public void setTareaActual(Tarea tarea) {
        this.tareaActual = tarea;
        firePropertyChange(TAREA_ACTUAL);
    }

}