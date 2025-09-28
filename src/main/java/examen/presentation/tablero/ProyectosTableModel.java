package examen.presentation.tablero;

import java.util.List;

import examen.presentation.AbstractTableModel;

import examen.logic.entities.Proyecto;

public class ProyectosTableModel extends AbstractTableModel<Proyecto> implements javax.swing.table.TableModel {
    public ProyectosTableModel(int[] cols, List<Proyecto> rows) {
        super(cols, rows);
    }

    public static final int COD = 0;
    public static final int DESC = 1;
    public static final int ENC = 2;
    public static final int TAR = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[COD] = "Código";
        colNames[DESC] = "Descripción";
        colNames[ENC] = "Encargado";
        colNames[TAR] = "# Tareas";
    }

    public Proyecto getRowAt(int row) {
        return rows.get(row);
    }

    @Override
    protected Object getPropetyAt(Proyecto e, int col) {
        switch (cols[col]) {
            case COD:
                return e.getCodigo();
            case DESC:
                return e.getDescripcion();
            case ENC:
                return e.getEncargado().getName();
            case TAR:
                return e.getTareas().size();
            default:
                return "";
        }
    }
}