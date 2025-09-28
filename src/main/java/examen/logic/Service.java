package examen.logic;

import examen.logic.entities.Proyecto;
import examen.logic.entities.Tarea;
import examen.logic.entities.User;
import examen.data.Data;
import examen.data.XmlPersister;

import java.util.Collections;
import java.util.List;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        try {
            data = XmlPersister.instance().load();
        } catch (Exception e) {
            data = new Data();
        }
    }

    public void stop(){
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Proyectos
    public void create(Proyecto e) throws Exception {
        Proyecto result = data.getProyectos().stream()
                .filter(i -> i.getCodigo() != null && i.getCodigo().equals(e.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getProyectos().add(e);
        } else {
            throw new Exception("Proyecto con código '" + e.getCodigo() + "' ya existe");
        }
    }

    public Proyecto read(Proyecto e) throws Exception {
        return data.getProyectos().stream()
                .filter(i -> i.getCodigo() != null && i.getCodigo().equals(e.getCodigo()))
                .findFirst()
                .orElseThrow(() -> new Exception("Proyecto con código '" + e.getCodigo() + "' no existe"));
    }

    public List<Proyecto> findAllProyectos() {
        return data.getProyectos();
    }

    public void update(Proyecto e) throws Exception {
        for (int i = 0; i < data.getProyectos().size(); i++) {
            if (data.getProyectos().get(i).getCodigo().equals(e.getCodigo())) {
                data.getProyectos().set(i, e);
                return;
            }
        }
        throw new Exception("Proyecto no encontrado para actualizar");
    }

    public void delete(String codigo) throws Exception {
        boolean removed = data.getProyectos().removeIf(p -> p.getCodigo().equals(codigo));
        if (!removed) {
            throw new Exception("Proyecto no encontrado para eliminar");
        }
    }

    // Users
    public List<User> findAllEncargados() {
        return Collections.unmodifiableList(data.getUsers());
    }

    // Tareas
    public void createTarea(Tarea tarea) throws Exception {
        // Verificar que no exista una tarea con el mismo número
        boolean existe = data.getTareas().stream()
                .anyMatch(t -> t.getNumero().equals(tarea.getNumero()));
        if (existe) {
            throw new Exception("Tarea con número '" + tarea.getNumero() + "' ya existe");
        }

        // Agregar tarea al listado global
        data.getTareas().add(tarea);
    }

    public Tarea read(Tarea t) throws Exception {
        return data.getTareas().stream()
                .filter(i -> i.getNumero() != null && i.getNumero().equals(t.getNumero()))
                .findFirst()
                .orElseThrow(() -> new Exception("Tarea con número '" + t.getNumero() + "' no existe"));
    }

    public List<Tarea> findAllTareas() {
        return data.getTareas();
    }

    public void update(Tarea t) throws Exception {
        for (int i = 0; i < data.getTareas().size(); i++) {
            if (data.getTareas().get(i).getNumero().equals(t.getNumero())) {
                data.getTareas().set(i, t);
                return;
            }
        }
        throw new Exception("Tarea no encontrada para actualizar");
    }

    public void deleteTarea(String numero) throws Exception {
        boolean removed = data.getTareas().removeIf(t -> t.getNumero().equals(numero));
        if (!removed) {
            throw new Exception("Tarea no encontrada para eliminar");
        }
    }

    public List<Tarea> findByResponsable(User user) {
        return data.getTareas().stream()
                .filter(t -> t.getResponsable().equals(user))
                .toList();
    }
}