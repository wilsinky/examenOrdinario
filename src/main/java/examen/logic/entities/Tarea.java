package examen.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tarea {
    @XmlID
    private String numero;
    private String descripcion;
    private String fecha;
    private String prioridad; // Alta, Media o Baja
    private String estado; // Abierta, En-progreso, En-revisión, Resuelta

    @XmlIDREF
    private User responsable;

    public Tarea(String numero, String descripcion, String fecha, String prioridad, String estado, User responsable) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.prioridad = prioridad;
        this.estado = estado;
        this.responsable = responsable;
    }

    public Tarea() {}

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }

    public String getNumero() {
        return numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public User getResponsable() {
        return responsable;
    }
}