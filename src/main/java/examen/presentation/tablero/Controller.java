package examen.presentation.tablero;

import examen.logic.Service;
import examen.logic.entities.Proyecto;
import examen.logic.entities.Tarea;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        // Inicializar
        model.setProyectos(Service.instance().findAllProyectos());
        model.setTareas(model.getProyectos().stream()
                .flatMap(p -> p.getTareas().stream())
                .toList());
    }

    //proyectos
    public void setCurrent(Proyecto p) {
        model.setCurrentProyecto(p);
    }

    public void create(Proyecto proyecto) throws Exception {
        Service.instance().create(proyecto);
        refreshProyectos();
    }

    //tareas
    public void createTarea(Tarea tarea) throws Exception {
        Proyecto actual = model.getCurrentProyecto();
        if (actual == null) throw new Exception("Seleccione un proyecto primero");

        Service.instance().createTarea(tarea);

        actual.getTareas().add(tarea);

        model.setTareas(actual.getTareas());
        model.setCurrentProyecto(actual);
        model.setProyectos(Service.instance().findAllProyectos());
    }


    public void setCurrentTarea(Tarea tarea) {
        model.setTareaActual(tarea);
    }

    public void updateTarea(Tarea tareaActualizada) throws Exception {
        Proyecto actual = model.getCurrentProyecto();
        if (actual == null) throw new Exception("No hay proyecto seleccionado");

        for (int i = 0; i < actual.getTareas().size(); i++) {
            Tarea t = actual.getTareas().get(i);
            if (t.getNumero().equals(tareaActualizada.getNumero())) {
                actual.getTareas().set(i, tareaActualizada);
                break;
            }
        }

        Service.instance().update(tareaActualizada);
        model.setTareas(actual.getTareas());
        model.setCurrentProyecto(actual);
    }

    public void removeTarea(Tarea tarea) {
        Proyecto actual = model.getCurrentProyecto();
        if (actual != null && actual.getTareas() != null) {
            actual.getTareas().remove(tarea);
            model.removeTarea(tarea);
            model.setCurrentProyecto(actual);
        }
    }

    // refresh
    private void refreshProyectos() {
        model.setProyectos(Service.instance().findAllProyectos());
        model.setCurrentProyecto(new Proyecto()); // limpia selección
    }
}