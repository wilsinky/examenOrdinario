package examen.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Proyecto {
    @XmlID
    private String codigo;
    private String descripcion;

    @XmlIDREF
    private User encargado;

    private List<Tarea> tareas;

    public Proyecto(String codigo, String descripcion, User encargado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.encargado = encargado;
        tareas = new ArrayList<>();
    }

    public Proyecto() {
        tareas = new ArrayList<>();
    };

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEncargado(User encargado) {
        this.encargado = encargado;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public User getEncargado() {
        return encargado;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}