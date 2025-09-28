package examen.presentation.tablero;

import examen.Application;
import examen.logic.Service;
import examen.logic.entities.Proyecto;
import examen.logic.entities.Tarea;
import examen.logic.entities.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class View implements PropertyChangeListener {
    private JPanel panel;

    private JButton crearButton;

    private JTextField codigoTextField;
    private JTextField descripcionField;
    private JComboBox encargadoComboBox;

    private JTable proyectos;

    private JLabel proyectoDescField; // Descripción del proyecto selected

    // ------

    private JTable tareasDelProyecto;

    private JButton crearTarea;
    private JTextField descTareaField;
    private JComboBox prioridTareaComboBox;
    private JTextField vencTareaField; // Me gustaría que fuese un date picker
    private JComboBox estadTareaComboBox; // tienen texto
    private JComboBox ResponTareaComboBox; // objeto
    private JTextField numeroTarea;

    //---

    public JPanel getPanel() { return panel; }

    public View () {
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Proyecto proyecto = take();
                    try {
                        controller.create(proyecto);
                        JOptionPane.showMessageDialog(panel, "Proyecto creado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        proyectos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = proyectos.getSelectedRow();
                if (selectedRow != -1) {
                    ProyectosTableModel modelTabla = (ProyectosTableModel) proyectos.getModel();
                    Proyecto seleccionado = modelTabla.getRowAt(selectedRow);

                    controller.setCurrent(seleccionado);

                    proyectoDescField.setText(seleccionado.getDescripcion());
                }
            }
        });

        // Crear tarea
        crearTarea.addActionListener(e -> {
            if (validateTarea()) {
                Tarea tarea = takeTarea();
                try {
                    controller.createTarea(tarea);
                    JOptionPane.showMessageDialog(panel, "Tarea creada correctamente", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        tareasDelProyecto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && tareasDelProyecto.getSelectedRow() != -1) {
                    int row = tareasDelProyecto.getSelectedRow();
                    TareasTableModel modelTabla = (TareasTableModel) tareasDelProyecto.getModel();
                    Tarea tareaSeleccionada = modelTabla.getRowAt(row);

                    controller.setCurrentTarea(tareaSeleccionada);
                    mostrarDialogoEdicion(tareaSeleccionada);
                }
            }
        });
    }

    private void mostrarDialogoEdicion(Tarea tarea) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Editar Tarea", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(panel);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        JComboBox<String> prioridadCombo = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        prioridadCombo.setSelectedItem(tarea.getPrioridad());

        JComboBox<String> estadoCombo = new JComboBox<>(new String[]{"Pendiente", "En progreso", "Completada"});
        estadoCombo.setSelectedItem(tarea.getEstado());

        JButton guardarBtn = new JButton("Guardar");

        guardarBtn.addActionListener(e -> {
            tarea.setPrioridad(prioridadCombo.getSelectedItem().toString());
            tarea.setEstado(estadoCombo.getSelectedItem().toString());

            try {
                controller.updateTarea(tarea);
                JOptionPane.showMessageDialog(dialog, "Tarea actualizada correctamente", "", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel("Prioridad:"));
        dialog.add(prioridadCombo);
        dialog.add(new JLabel("Estado:"));
        dialog.add(estadoCombo);
        dialog.add(guardarBtn);

        dialog.setVisible(true);
    }

    private boolean validate() {
        boolean valid = true;

        // Validar código
        if (codigoTextField.getText().trim().isEmpty()) {
            valid = false;
            codigoTextField.setBackground(Application.BACKGROUND_ERROR);
            codigoTextField.setToolTipText("Código requerido");
        } else {
            codigoTextField.setBackground(null);
            codigoTextField.setToolTipText(null);
        }

        // Validar descripción
        if (descripcionField.getText().trim().isEmpty()) {
            valid = false;
            descripcionField.setBackground(Application.BACKGROUND_ERROR);
            descripcionField.setToolTipText("Descripción requerida");
        } else {
            descripcionField.setBackground(null);
            descripcionField.setToolTipText(null);
        }

        // Validar encargado
        Object selected = encargadoComboBox.getSelectedItem();
        if (selected == null) {
            valid = false;
            encargadoComboBox.setBackground(Application.BACKGROUND_ERROR);
            encargadoComboBox.setToolTipText("Encargado requerido");
        } else {
            encargadoComboBox.setBackground(null);
            encargadoComboBox.setToolTipText(null);
        }

        return valid;
    }

    public Proyecto take() {
        Proyecto proyecto = new Proyecto();

        proyecto.setCodigo(codigoTextField.getText().trim());
        proyecto.setDescripcion(descripcionField.getText().trim());
        Object encargado = encargadoComboBox.getSelectedItem();
        if (encargado instanceof User) {
            proyecto.setEncargado((User) encargado);
        }

        return proyecto;
    }

    private boolean validateTarea() {
        boolean valid = true;

        // Validar número
        if (numeroTarea.getText().trim().isEmpty()) {
            valid = false;
            numeroTarea.setBackground(Application.BACKGROUND_ERROR);
            numeroTarea.setToolTipText("Número requerido");
        } else {
            numeroTarea.setBackground(null);
            numeroTarea.setToolTipText(null);
        }

        // Validar descripción
        if (descTareaField.getText().trim().isEmpty()) {
            valid = false;
            descTareaField.setBackground(Application.BACKGROUND_ERROR);
            descTareaField.setToolTipText("Descripción requerida");
        } else {
            descTareaField.setBackground(null);
            descTareaField.setToolTipText(null);
        }

        // Validar fecha de vencimiento
        if (vencTareaField.getText().trim().isEmpty()) {
            valid = false;
            vencTareaField.setBackground(Application.BACKGROUND_ERROR);
            vencTareaField.setToolTipText("Fecha requerida");
        } else {
            vencTareaField.setBackground(null);
            vencTareaField.setToolTipText(null);
        }

        // Validar prioridad
        if (prioridTareaComboBox.getSelectedItem() == null) {
            valid = false;
            prioridTareaComboBox.setBackground(Application.BACKGROUND_ERROR);
            prioridTareaComboBox.setToolTipText("Prioridad requerida");
        } else {
            prioridTareaComboBox.setBackground(null);
            prioridTareaComboBox.setToolTipText(null);
        }

        // Validar estado
        if (estadTareaComboBox.getSelectedItem() == null) {
            valid = false;
            estadTareaComboBox.setBackground(Application.BACKGROUND_ERROR);
            estadTareaComboBox.setToolTipText("Estado requerido");
        } else {
            estadTareaComboBox.setBackground(null);
            estadTareaComboBox.setToolTipText(null);
        }

        // Validar responsable
        if (ResponTareaComboBox.getSelectedItem() == null || !(ResponTareaComboBox.getSelectedItem() instanceof User)) {
            valid = false;
            ResponTareaComboBox.setBackground(Application.BACKGROUND_ERROR);
            ResponTareaComboBox.setToolTipText("Responsable requerido");
        } else {
            ResponTareaComboBox.setBackground(null);
            ResponTareaComboBox.setToolTipText(null);
        }

        if (!valid) {
            JOptionPane.showMessageDialog(panel, "Por favor complete todos los campos correctamente", "Validación", JOptionPane.WARNING_MESSAGE);
        }

        return valid;
    }

    // Tomar datos de la tarea
    private Tarea takeTarea() {
        Tarea t = new Tarea();
        t.setNumero(numeroTarea.getText().trim());
        t.setDescripcion(descTareaField.getText().trim());
        t.setFecha(vencTareaField.getText().trim());
        t.setPrioridad(prioridTareaComboBox.getSelectedItem().toString());
        t.setEstado(estadTareaComboBox.getSelectedItem().toString());
        t.setResponsable((User) ResponTareaComboBox.getSelectedItem());
        return t;
    }

    // -- MVC --
    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        List<User> encargados = Service.instance().findAllEncargados();

        for (User e : encargados) {
            encargadoComboBox.addItem(e);
        }

        for (User e : encargados) {
            ResponTareaComboBox.addItem(e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.PROYECTOS:
                int[] colsProy = {ProyectosTableModel.COD, ProyectosTableModel.DESC, ProyectosTableModel.ENC, ProyectosTableModel.TAR};
                proyectos.setModel(new ProyectosTableModel(colsProy, model.getProyectos()));
                proyectos.setRowHeight(30);
                break;
            case Model.TAREAS:
                int[] colsTareas = {TareasTableModel.NUM, TareasTableModel.DESC, TareasTableModel.VENC,
                        TareasTableModel.PRIOR, TareasTableModel.EST, TareasTableModel.ASIG};
                tareasDelProyecto.setModel(new TareasTableModel(colsTareas, model.getTareas()));
                tareasDelProyecto.setRowHeight(30);
                break;

        }
        panel.revalidate();
    }
}