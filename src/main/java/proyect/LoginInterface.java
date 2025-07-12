package proyect;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginInterface extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginInterface().setVisible(true));
    }

    public LoginInterface() {
        SistemaGestion sistema = new SistemaGestion();
        
        setTitle("Acceso al Sistema de Trámites");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Ingresar");

        constraints.gridx = 0; constraints.gridy = 0; panel.add(new JLabel("Usuario:"), constraints);
        constraints.gridx = 1; constraints.gridy = 0; panel.add(userField, constraints);
        constraints.gridx = 0; constraints.gridy = 1; panel.add(new JLabel("Contraseña:"), constraints);
        constraints.gridx = 1; constraints.gridy = 1; panel.add(passField, constraints);
        constraints.gridx = 0; constraints.gridy = 2; constraints.gridwidth = 2; 
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);
        
        add(panel);

        loginButton.addActionListener(e -> {
            String usuario = userField.getText();
            String contrasena = new String(passField.getPassword());
            
            if (sistema.validarCredenciales(usuario, contrasena)) {
                dispose();
                new VentanaPrincipal(sistema).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

class VentanaPrincipal extends JFrame {
    private final SistemaGestion sistema;
    private JTextArea areaDeRegistros;
    private JTextArea areaDeResultadoBusqueda;
    private String idTramiteActualEnBusqueda; 
    private JButton finalizarBtn; 

    public VentanaPrincipal(SistemaGestion sistema) {
        this.sistema = sistema;
        
        setTitle("Sistema de Gestión de Trámite Documentario - ULima");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("📥 Registrar Nuevo Trámite", crearPanelNuevoTramite());
        pestanas.addTab("📋 Ver Todos los Registros", crearPanelVerRegistros());
        pestanas.addTab("🔍 Gestionar Trámite por ID", crearPanelBuscarTramite());
        add(pestanas, BorderLayout.CENTER);

        add(crearPanelDeAlertas(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelDeAlertas() {
        JPanel panelAlertas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAlertas.setBorder(BorderFactory.createTitledBorder("Alertas de Trámites Urgentes"));
        
        JLabel alertaLabel = new JLabel("Inicializando alertas...");
        alertaLabel.setForeground(Color.RED);
        panelAlertas.add(alertaLabel);
        
        Timer timer = new Timer(3000, e -> {
            Tramite tramiteEnAlerta = sistema.obtenerSiguienteAlerta();
            
            int conteoPendientes = sistema.carruselDeAlertas.getTamaño();
            if (tramiteEnAlerta != null) {
                alertaLabel.setText(String.format("(%d pendientes) PENDIENTE: %s", conteoPendientes, tramiteEnAlerta));
            } else {
                alertaLabel.setText("No hay trámites pendientes en el carrusel de alertas.");
            }
        });
        timer.start();
        
        return panelAlertas;
    }

    private JPanel crearPanelNuevoTramite() {
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridy = 0; panel.add(new JLabel("DNI:"), c);
        c.gridy = 1; panel.add(new JLabel("Nombres Completos:"), c);
        c.gridy = 2; panel.add(new JLabel("Teléfono:"), c);
        c.gridy = 3; panel.add(new JLabel("Email:"), c);
        c.gridy = 5; panel.add(new JLabel("Prioridad:"), c);
        c.gridy = 6; panel.add(new JLabel("Asunto del Trámite:"), c);
        
        JTextField dniField = new JTextField(20);
        JTextField nombreField = new JTextField(20);
        JTextField telField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JComboBox<String> prioridadCombo = new JComboBox<>(new String[]{"Normal", "Urgente"});
        JTextField asuntoField = new JTextField(20);
        JButton guardarBtn = new JButton("Guardar Trámite");

        c.gridx = 1;
        c.gridy = 0; panel.add(dniField, c);
        c.gridy = 1; panel.add(nombreField, c);
        c.gridy = 2; panel.add(telField, c);
        c.gridy = 3; panel.add(emailField, c);
        c.gridy = 5; panel.add(prioridadCombo, c);
        c.gridy = 6; panel.add(asuntoField, c);
        
        c.gridx = 0; c.gridy = 7; c.gridwidth = 2; c.insets = new Insets(20, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;
        panel.add(guardarBtn, c);
        
        guardarBtn.addActionListener(e -> {
            try {
                Interesado interesado = new Interesado(dniField.getText(), nombreField.getText(), telField.getText(), emailField.getText());
                String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                
                Tramite nuevoTramite = new Tramite(
                    prioridadCombo.getSelectedItem().toString(), interesado, asuntoField.getText(),
                    "Trámite General", "Pendiente", fechaActual, ""
                );
                
                sistema.registrarNuevoTramite(nuevoTramite);
                JOptionPane.showMessageDialog(this, "Trámite registrado con éxito. ID: " + nuevoTramite.getIdTramite());
                
                dniField.setText(""); nombreField.setText(""); telField.setText(""); emailField.setText(""); asuntoField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }

    private JPanel crearPanelVerRegistros() {
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        areaDeRegistros = new JTextArea();
        areaDeRegistros.setEditable(false);
        areaDeRegistros.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JButton refrescarBtn = new JButton("Actualizar Lista");
        refrescarBtn.addActionListener(e -> actualizarListaDeRegistros());
        
        panel.add(new JScrollPane(areaDeRegistros), BorderLayout.CENTER);
        panel.add(refrescarBtn, BorderLayout.SOUTH);
        
        actualizarListaDeRegistros();
        
        return panel;
    }
    
    private void actualizarListaDeRegistros() {
        // Este método no cambia.
        Tramite[] todos = sistema.obtenerTodosLosTramites();
        StringBuilder sb = new StringBuilder();
        sb.append("--- TOTAL DE TRÁMITES REGISTRADOS: ").append(todos.length).append(" ---\n\n");
        for(Tramite t : todos) {
            sb.append(t.toString()).append("\n");
        }
        areaDeRegistros.setText(sb.toString());
    }
    
    
    private JPanel crearPanelBuscarTramite() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idField = new JTextField(15);
        JButton buscarBtn = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Ingrese ID del Trámite:"));
        panelBusqueda.add(idField);
        panelBusqueda.add(buscarBtn);
        
        areaDeResultadoBusqueda = new JTextArea();
        areaDeResultadoBusqueda.setEditable(false);
        areaDeResultadoBusqueda.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        
        finalizarBtn = new JButton("✅ Finalizar este Trámite");
        finalizarBtn.setEnabled(false);
        JPanel panelSur = new JPanel();
        panelSur.add(finalizarBtn);
        
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaDeResultadoBusqueda), BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);
        
        buscarBtn.addActionListener(e -> {
            String idBuscado = idField.getText().trim().toUpperCase();
            Tramite encontrado = sistema.buscarTramitePorId(idBuscado);
            
            if (encontrado != null) {
                mostrarDetalleDeTramite(encontrado);
                idTramiteActualEnBusqueda = encontrado.getIdTramite(); 
                
                finalizarBtn.setEnabled("Pendiente".equalsIgnoreCase(encontrado.getEstado()));
            } else {
                areaDeResultadoBusqueda.setText("\n   No se encontró ningún trámite con el ID: " + idBuscado);
                idTramiteActualEnBusqueda = null;
                finalizarBtn.setEnabled(false); 
            }
        });

        finalizarBtn.addActionListener(e -> {
            if (idTramiteActualEnBusqueda != null) {
                int confirmacion = JOptionPane.showConfirmDialog(this, 
                    "¿Está seguro de que desea finalizar el trámite " + idTramiteActualEnBusqueda + "?\nEsta acción no se puede deshacer.",
                    "Confirmar Finalización",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    sistema.resolverTramite(idTramiteActualEnBusqueda);
                    finalizarBtn.setEnabled(false);
                    Tramite actualizado = sistema.buscarTramitePorId(idTramiteActualEnBusqueda);
                    mostrarDetalleDeTramite(actualizado);
                    JOptionPane.showMessageDialog(this, "El trámite ha sido finalizado con éxito.");
                }
            }
        });
        
        return panel;
    }
    
    private void mostrarDetalleDeTramite(Tramite tramite) {
        StringBuilder detalle = new StringBuilder();
        detalle.append("FICHA DE SEGUIMIENTO DE TRÁMITE\n");
        detalle.append("===================================\n");
        detalle.append(String.format("%-20s: %s\n", "ID Trámite", tramite.getIdTramite()));
        detalle.append(String.format("%-20s: %s\n", "Asunto", tramite.getAsunto()));
        detalle.append(String.format("%-20s: %s\n", "Estado Actual", tramite.getEstado()));
        if (tramite.getFechaFinal() != null && !tramite.getFechaFinal().isEmpty()) {
            detalle.append(String.format("%-20s: %s\n", "Fecha Finalización", tramite.getFechaFinal()));
        }
        detalle.append(String.format("%-20s: %s\n", "Prioridad", tramite.getPrioridad()));
        detalle.append("\n--- Datos del Interesado ---\n");
        detalle.append(String.format("%-20s: %s\n", "DNI", tramite.getInteresado().getDni()));
        detalle.append(String.format("%-20s: %s\n", "Nombre", tramite.getInteresado().getNombres()));
        detalle.append("\n--- Historial de Movimientos ---\n");

        String[] historial = tramite.getHistorialDeMovimientos().obtenerTodosComoArray();
        for(String evento : historial) {
            detalle.append("  -> ").append(evento).append("\n");
        }
        
        areaDeResultadoBusqueda.setText(detalle.toString());
    }
}