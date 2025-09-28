package examen.presentation.tablero;

import examen.logic.entities.Tarea;
import examen.presentation.AbstractTableModel;

import java.util.List;

public class TareasTableModel extends AbstractTableModel<Tarea> implements javax.swing.table.TableModel {
    public TareasTableModel(int[] cols, List<Tarea> rows) {
        super(cols, rows);
    }

    public static final int NUM = 0;
    public static final int DESC = 1;
    public static final int VENC = 2;
    public static final int PRIOR = 3;
    public static final int EST = 4;
    public static final int ASIG = 5;

    @Override
    protected void initColNames() {
        colNames = new String[6];
        colNames[NUM] = "Número";
        colNames[DESC] = "Descripción";
        colNames[VENC] = "Vencimiento";
        colNames[PRIOR] = "Prioridad";
        colNames[EST] = "Estado";
        colNames[ASIG] = "Asignado";
    }

    public Tarea getRowAt(int row) {
        return rows.get(row);
    }

    @Override
    protected Object getPropetyAt(Tarea e, int col) {
        switch (cols[col]) {
            case NUM:
                return e.getNumero();
            case DESC:
                return e.getDescripcion();
            case VENC:
                return e.getFecha();
            case PRIOR:
                return e.getPrioridad();
            case EST:
                return e.getEstado();
            case ASIG:
                return e.getResponsable() != null ? e.getResponsable().getName() : "";
            default:
                return "";
        }
    }
}