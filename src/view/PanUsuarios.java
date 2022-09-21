
package view;

import Utilidades.GestionCeldas;
import beans.Administrador;
import beans.Profesor;
import beans.Sede;
import Utilidades.Utilitarios;
import beans.Ciclo;
import beans.Curso;
import beans.PlanCurricular;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import log.AdministradorLog;
import log.CicloLog;
import log.CursoLog;
import log.PlanCurricularLog;
import log.ProfesorLog;

/**
 *
 * @author alfie
 */
public class PanUsuarios extends javax.swing.JPanel {
    AdministradorLog objAdm = new AdministradorLog();
    ProfesorLog objProf = new ProfesorLog();
    CursoLog objCursos = new CursoLog();
    PlanCurricularLog objPlan = new PlanCurricularLog();
    CicloLog objCiclo = new CicloLog();
    
    LinkedList<Sede> listSede = DashboardAdmin.listSede;
    LinkedList<Profesor> listProfesor = objProf.lista();
    LinkedList<PlanCurricular> listPlanes = objPlan.lista();
    LinkedList<Ciclo> listCiclos = objCiclo.lista();
    LinkedList<Curso> listCursos;
    
    
    //guarda la lista de cursos seleccionados
    LinkedList<Curso> cursosAgregados = new LinkedList<Curso>();
    
    
    Administrador auxAdministrador;
    Profesor auxProfesor;
    boolean privilegio = DashboardAdmin.admin.isPrivilegios();
    byte sedeId = DashboardAdmin.admin.getSedeId();
    boolean bandAdmin = false; // bandera para guardar o actualizar un registros
    boolean bandProf = false;
    boolean bandCursos = false;

    /**
     * Creates new form PanUsuariosPrueba
     */
    public PanUsuarios() {
        initComponents();
        cargarcmbSedes();
        cargarTabla();
    }
  
    private void configTblUsuarios(boolean estado) {
        tblUsuarios.setModel(objAdm.lista(estado, privilegio, sedeId));
        //cargamos la tabla de usuarios
        this.tblUsuarios.getTableHeader().setReorderingAllowed(false); //captura el encabezado
        this.tblUsuarios.getTableHeader().setFont(new Font("JetBrainsMonoMedium Nerd Font", Font.PLAIN, 12));
        tblUsuarios.setRowHeight(20); //configura el tamaño de las celdas
        //Se define el tamaño de largo para cada columna y su contenido
        this.tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(20); //Dni
        this.tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150); //Nombres y Apellidos
        this.tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(35); //Teléfono
        this.tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(50); //Usuario
        this.tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(50); //Password
        this.tblUsuarios.getColumnModel().getColumn(5).setPreferredWidth(60); //fecha
        this.tblUsuarios.getColumnModel().getColumn(6).setPreferredWidth(25); //privilegios
        this.tblUsuarios.getColumnModel().getColumn(7).setPreferredWidth(20); //sede
    }
    
    private void configTblProfesores(boolean estado) {
        tblProfesores.setModel(objProf.listTable(estado, privilegio, sedeId));
        //cargamos la tabla de usuarios
        this.tblProfesores.getTableHeader().setReorderingAllowed(false); //captura el encabezado
        this.tblProfesores.getTableHeader().setFont(new Font("JetBrainsMonoMedium Nerd Font", Font.PLAIN, 12));
        tblProfesores.setRowHeight(20); //configura el tamaño de las celdas
        //Se define el tamaño de largo para cada columna y su contenido
        this.tblProfesores.getColumnModel().getColumn(0).setPreferredWidth(30); //Dni
        this.tblProfesores.getColumnModel().getColumn(1).setPreferredWidth(100); //Nombres y Apellidos
        this.tblProfesores.getColumnModel().getColumn(2).setPreferredWidth(35); //correo
        this.tblProfesores.getColumnModel().getColumn(3).setPreferredWidth(40); //telefono
        this.tblProfesores.getColumnModel().getColumn(4).setPreferredWidth(40); //Usuario
        this.tblProfesores.getColumnModel().getColumn(5).setPreferredWidth(40); //Password
        this.tblProfesores.getColumnModel().getColumn(6).setPreferredWidth(25); //fecha
        this.tblProfesores.getColumnModel().getColumn(7).setPreferredWidth(70); //sede
        
    }
    
    private void configTblCursos() {
        if (privilegio)
            tblProfesorHasCursos.setModel(objProf.listTableCursos(true, sedeId));
        else
            tblProfesorHasCursos.setModel(objProf.listTableCursos(false, sedeId));
        
        //cargamos la tabla de niveles de logro
        this.tblProfesorHasCursos.getTableHeader().setReorderingAllowed(false); //captura el encabezado
        this.tblProfesorHasCursos.getTableHeader().setFont(new Font("JetBrainsMonoMedium Nerd Font", Font.PLAIN, 12));
        this.tblProfesorHasCursos.setRowHeight(20); //configura el tamaño de las celdas
        //Se define el tamaño de largo para cada columna y su contenido
        this.tblProfesorHasCursos.getColumnModel().getColumn(0).setPreferredWidth(10); //id
        this.tblProfesorHasCursos.getColumnModel().getColumn(1).setPreferredWidth(50); //Código
        this.tblProfesorHasCursos.getColumnModel().getColumn(2).setPreferredWidth(50); //Nombre
        this.tblProfesorHasCursos.getColumnModel().getColumn(3).setPreferredWidth(150); //Creditos
        this.tblProfesorHasCursos.getColumnModel().getColumn(4).setPreferredWidth(30); //Plan Curricular
    }
    
  
     
     
    private void cargarTabla() { 
        configTblUsuarios(true);
        configTblProfesores(true);
        configTblCursos();
        mCajasAdmin(false, false, false);
        mCajasProf(false, false);
        mCajasCursos(false, false);
        
        
        mBotonesAdmin(true, false, false, false, true);
        mBotonesProf(true, false, false, false, true);
        mBotonesCursos(true, false, false, false, true, false);
        //cargamos la tabla de profesores
        //carga el jcombobox de profesores
        cargarListProf();
        //carca el jcombobox de sede
        cargarListSede();
        //carga el jcombobox de planes curriculares
        cargarListPlan();
        //carga el jcombobox de ciclo
        cargarListCiclo();
        //carga los cursos en el jcombobox
        cargarListCurso();
    }
    
    private void cargarcmbSedes() {
        Iterator<Sede> it = listSede.iterator();
        Sede aux = null;
        while (it.hasNext()) {            
            aux = it.next();
            if (privilegio == true) {//superusuario
                cmbSedeAdmin.addItem(aux.getDescripcion());
            }else if(sedeId == aux.getSedeId()) {
                cmbSedeAdmin.addItem(aux.getDescripcion());
            }
        }
    }
    
    
    private void cargarListProf() {
        Iterator<Profesor> it = listProfesor.iterator();
        Profesor aux = null;
        cmbListProf.removeAllItems();
        while (it.hasNext()) {            
            aux = it.next();
            cmbListProf.addItem(aux.getNombre() + " " + aux.getApellido());
        }
    }
    
    private void cargarListSede() {
        Iterator<Sede> it = listSede.iterator();
        Sede aux = null;
        cmbListSede.removeAllItems();
        while (it.hasNext()) {            
            aux = it.next();
            cmbListSede.addItem(aux.getDescripcion());
        }
    }
    
    private void cargarListPlan() {
        Iterator<PlanCurricular> it = listPlanes.iterator();
        PlanCurricular aux = null;
        cmbListPlan.removeAllItems();
        while (it.hasNext()) {            
            aux = it.next();
            cmbListPlan.addItem(aux.getPlanEstudios());
        }
    }
    
    private void cargarListCiclo() {
        Iterator<Ciclo> it = listCiclos.iterator();
        Ciclo aux = null;
        cmbListCiclo.removeAllItems();
        while (it.hasNext()) {            
            aux = it.next();
            cmbListCiclo.addItem(aux.getDescripcion());
        }
    }
    
    
    private void cargarListCurso() {
        String plan = (cmbListPlan.getItemCount() == 0)? "2019" : cmbListPlan.getSelectedItem().toString();
        String ciclo = (cmbListCiclo.getItemCount() == 0)? "I" : cmbListCiclo.getSelectedItem().toString();

        listCursos = objCursos.lista(plan, ciclo);
        Iterator<Curso> it = listCursos.iterator();
        Curso aux = null;
        cmbListCurso.removeAllItems();
        while (it.hasNext()) {            
            aux = it.next();
            cmbListCurso.addItem(aux.getDescripcion());
        }
    }
    
    private boolean validarEmail(String correo){
        String regex = "([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+";
        Pattern pattern = Pattern.compile(regex);
        
        Matcher comparar = pattern.matcher(correo);
        return comparar.find();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgAdministrador = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panAdmin = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDniAdmin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNomAdmin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefAdmin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApeAdmin = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUsuarioAdmin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        pwdPasswordAdmin = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        cmbEstadoAdmin = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jrbSi = new javax.swing.JRadioButton();
        jrbNo = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cmbSedeAdmin = new javax.swing.JComboBox<>();
        txtFechaAdmin = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnNuevoAdmin = new javax.swing.JButton();
        btnGuardarAdmin = new javax.swing.JButton();
        btnBuscarAdmin = new javax.swing.JButton();
        btnEliminarAdmin = new javax.swing.JButton();
        btnEditarAdmin = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cmbEstadoBuscarAdmin = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        btnVisualizarAdmin = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        panProf = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtDniProf = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNomProf = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTelefonoProf = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtApeProf = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoProf = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbEstadoProf = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtUsuarioProf = new javax.swing.JTextField();
        pwdProf = new javax.swing.JPasswordField();
        txtFechaProf = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnNuevoProf = new javax.swing.JButton();
        btnGuardarProf = new javax.swing.JButton();
        btnBuscarProf = new javax.swing.JButton();
        btnEliminarProf = new javax.swing.JButton();
        btnEditarProf = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblProfesores = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        cmbBuscarProf = new javax.swing.JComboBox<>();
        btnBuscarProfesores = new javax.swing.JButton();
        panCursos = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        cmbListProf = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbListSede = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cmbListPlan = new javax.swing.JComboBox<>();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblCursosSeleccionados = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        cmbListCiclo = new javax.swing.JComboBox<>();
        btnAddCurso = new javax.swing.JButton();
        btnDeleteCurso = new javax.swing.JButton();
        btnLimpiarCurso = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        cmbListCurso = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        btnNuevoCurso = new javax.swing.JButton();
        btnGuardarCurso = new javax.swing.JButton();
        btnBuscarCurso = new javax.swing.JButton();
        btnEliminarCurso = new javax.swing.JButton();
        btnEditarCurso = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblProfesorHasCursos = new javax.swing.JTable();

        setBackground(new java.awt.Color(214, 217, 223));
        setPreferredSize(new java.awt.Dimension(842, 640));

        jTabbedPane1.setBackground(new java.awt.Color(214, 217, 223));
        jTabbedPane1.setForeground(new java.awt.Color(29, 32, 33));
        jTabbedPane1.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 2, 13)); // NOI18N

        panAdmin.setBackground(new java.awt.Color(214, 217, 223));
        panAdmin.setPreferredSize(new java.awt.Dimension(842, 613));

        jPanel3.setBackground(new java.awt.Color(214, 217, 223));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        jLabel1.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(29, 32, 33));
        jLabel1.setText("Dni:");

        txtDniAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtDniAdmin.setForeground(new java.awt.Color(29, 32, 33));
        txtDniAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniAdminKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(29, 32, 33));
        jLabel2.setText("Nombres:");

        txtNomAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtNomAdmin.setForeground(new java.awt.Color(29, 32, 33));
        txtNomAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomAdminKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(29, 32, 33));
        jLabel3.setText("Telefono:");

        txtTelefAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtTelefAdmin.setForeground(new java.awt.Color(29, 32, 33));
        txtTelefAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefAdminKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(29, 32, 33));
        jLabel4.setText("Apellidos:");

        txtApeAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtApeAdmin.setForeground(new java.awt.Color(29, 32, 33));
        txtApeAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApeAdminKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(29, 32, 33));
        jLabel5.setText("Usuario:");

        txtUsuarioAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtUsuarioAdmin.setForeground(new java.awt.Color(29, 32, 33));

        jLabel6.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(29, 32, 33));
        jLabel6.setText("Contraseña:");

        pwdPasswordAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        pwdPasswordAdmin.setForeground(new java.awt.Color(29, 32, 33));

        jLabel7.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(29, 32, 33));
        jLabel7.setText("Estado:");

        cmbEstadoAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbEstadoAdmin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        jLabel8.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(29, 32, 33));
        jLabel8.setText("Administrador:");

        btgAdministrador.add(jrbSi);
        jrbSi.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jrbSi.setSelected(true);
        jrbSi.setText("Si");

        btgAdministrador.add(jrbNo);
        jrbNo.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jrbNo.setText("No");

        jLabel9.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(29, 32, 33));
        jLabel9.setText("Sede:");

        jLabel22.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(29, 32, 33));
        jLabel22.setText("Fecha de actualización:");

        cmbSedeAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N

        txtFechaAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 12)); // NOI18N
        txtFechaAdmin.setForeground(new java.awt.Color(29, 32, 33));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(txtDniAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtNomAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtApeAdmin)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtUsuarioAdmin)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(pwdPasswordAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(txtFechaAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbEstadoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrbSi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrbNo)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)))
                        .addGap(18, 18, 18)
                        .addComponent(cmbSedeAdmin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDniAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApeAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelefAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsuarioAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(pwdPasswordAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbEstadoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jrbSi)
                    .addComponent(jrbNo)
                    .addComponent(jLabel9)
                    .addComponent(cmbSedeAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtFechaAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(214, 217, 223));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        btnNuevoAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnNuevoAdmin.setForeground(new java.awt.Color(29, 32, 33));
        btnNuevoAdmin.setText("Nuevo");
        btnNuevoAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAdminActionPerformed(evt);
            }
        });

        btnGuardarAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnGuardarAdmin.setForeground(new java.awt.Color(29, 32, 33));
        btnGuardarAdmin.setText("Guardar");
        btnGuardarAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarAdminActionPerformed(evt);
            }
        });

        btnBuscarAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnBuscarAdmin.setForeground(new java.awt.Color(29, 32, 33));
        btnBuscarAdmin.setText("Buscar");
        btnBuscarAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAdminActionPerformed(evt);
            }
        });

        btnEliminarAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnEliminarAdmin.setForeground(new java.awt.Color(29, 32, 33));
        btnEliminarAdmin.setText("Eliminar");
        btnEliminarAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAdminActionPerformed(evt);
            }
        });

        btnEditarAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnEditarAdmin.setForeground(new java.awt.Color(29, 32, 33));
        btnEditarAdmin.setText("Editar");
        btnEditarAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(btnBuscarAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnNuevoAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarAdmin)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(214, 217, 223));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visualización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        cmbEstadoBuscarAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbEstadoBuscarAdmin.setForeground(new java.awt.Color(29, 32, 33));
        cmbEstadoBuscarAdmin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        jLabel28.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(29, 32, 33));
        jLabel28.setText("Estado:");

        btnVisualizarAdmin.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnVisualizarAdmin.setForeground(new java.awt.Color(29, 32, 33));
        btnVisualizarAdmin.setText("Buscar");
        btnVisualizarAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarAdminActionPerformed(evt);
            }
        });

        tblUsuarios.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 12)); // NOI18N
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblUsuarios.setGridColor(new java.awt.Color(153, 153, 153));
        tblUsuarios.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblUsuarios);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(505, 505, 505)
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addComponent(cmbEstadoBuscarAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVisualizarAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVisualizarAdmin)
                    .addComponent(jLabel28)
                    .addComponent(cmbEstadoBuscarAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panAdminLayout = new javax.swing.GroupLayout(panAdmin);
        panAdmin.setLayout(panAdminLayout);
        panAdminLayout.setHorizontalGroup(
            panAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panAdminLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        panAdminLayout.setVerticalGroup(
            panAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Administradores", panAdmin);

        panProf.setBackground(new java.awt.Color(214, 217, 223));

        jPanel6.setBackground(new java.awt.Color(214, 217, 223));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        jLabel10.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(29, 32, 33));
        jLabel10.setText("Dni:");

        txtDniProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtDniProf.setForeground(new java.awt.Color(29, 32, 33));
        txtDniProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniProfKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(29, 32, 33));
        jLabel11.setText("Nombres:");

        txtNomProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtNomProf.setForeground(new java.awt.Color(29, 32, 33));
        txtNomProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomProfKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(29, 32, 33));
        jLabel12.setText("Telefono:");

        txtTelefonoProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtTelefonoProf.setForeground(new java.awt.Color(29, 32, 33));
        txtTelefonoProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProfKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(29, 32, 33));
        jLabel13.setText("Apellidos:");

        txtApeProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtApeProf.setForeground(new java.awt.Color(29, 32, 33));
        txtApeProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApeProfKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(29, 32, 33));
        jLabel14.setText("Correo:");

        txtCorreoProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtCorreoProf.setForeground(new java.awt.Color(29, 32, 33));
        txtCorreoProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoProfKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(29, 32, 33));
        jLabel15.setText("Usuario:");

        jLabel16.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(29, 32, 33));
        jLabel16.setText("Contraseña:");

        cmbEstadoProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbEstadoProf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        jLabel17.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(29, 32, 33));
        jLabel17.setText("Estado:");

        jLabel18.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(29, 32, 33));
        jLabel18.setText("Fecha:");

        txtUsuarioProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        txtUsuarioProf.setForeground(new java.awt.Color(29, 32, 33));

        pwdProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        pwdProf.setForeground(new java.awt.Color(29, 32, 33));

        txtFechaProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 12)); // NOI18N
        txtFechaProf.setForeground(new java.awt.Color(29, 32, 33));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(txtDniProf, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtNomProf, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txtApeProf)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefonoProf, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(pwdProf)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(txtCorreoProf)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(txtUsuarioProf, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(cmbEstadoProf, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFechaProf, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDniProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtNomProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtApeProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtTelefonoProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtUsuarioProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaProf, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(cmbEstadoProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(jLabel18)
                        .addComponent(pwdProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(214, 217, 223));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        btnNuevoProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnNuevoProf.setForeground(new java.awt.Color(29, 32, 33));
        btnNuevoProf.setText("Nuevo");
        btnNuevoProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProfActionPerformed(evt);
            }
        });

        btnGuardarProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnGuardarProf.setForeground(new java.awt.Color(29, 32, 33));
        btnGuardarProf.setText("Guardar");
        btnGuardarProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProfActionPerformed(evt);
            }
        });

        btnBuscarProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnBuscarProf.setForeground(new java.awt.Color(29, 32, 33));
        btnBuscarProf.setText("Buscar");
        btnBuscarProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProfActionPerformed(evt);
            }
        });

        btnEliminarProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnEliminarProf.setForeground(new java.awt.Color(29, 32, 33));
        btnEliminarProf.setText("Eliminar");
        btnEliminarProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProfActionPerformed(evt);
            }
        });

        btnEditarProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnEditarProf.setForeground(new java.awt.Color(29, 32, 33));
        btnEditarProf.setText("Editar");
        btnEditarProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoProf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarProf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarProf, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(btnBuscarProf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarProf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(btnNuevoProf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarProf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarProf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarProf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarProf)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(214, 217, 223));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visualización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        tblProfesores.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        tblProfesores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblProfesores.setGridColor(new java.awt.Color(153, 153, 153));
        jScrollPane10.setViewportView(tblProfesores);

        jLabel29.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(29, 32, 33));
        jLabel29.setText("Estado:");

        cmbBuscarProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbBuscarProf.setForeground(new java.awt.Color(29, 32, 33));
        cmbBuscarProf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        btnBuscarProfesores.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnBuscarProfesores.setForeground(new java.awt.Color(29, 32, 33));
        btnBuscarProfesores.setText("Buscar");
        btnBuscarProfesores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProfesoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(cmbBuscarProf, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarProfesores)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarProfesores)
                    .addComponent(jLabel29)
                    .addComponent(cmbBuscarProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panProfLayout = new javax.swing.GroupLayout(panProf);
        panProf.setLayout(panProfLayout);
        panProfLayout.setHorizontalGroup(
            panProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panProfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panProfLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        panProfLayout.setVerticalGroup(
            panProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panProfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Profesores", panProf);

        panCursos.setBackground(new java.awt.Color(214, 217, 223));

        jPanel9.setBackground(new java.awt.Color(214, 217, 223));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        jLabel21.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(29, 32, 33));
        jLabel21.setText("Profesor:");

        cmbListProf.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbListProf.setForeground(new java.awt.Color(29, 32, 33));

        jLabel19.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(29, 32, 33));
        jLabel19.setText("Sede:");

        cmbListSede.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbListSede.setForeground(new java.awt.Color(29, 32, 33));

        jLabel20.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(29, 32, 33));
        jLabel20.setText("Plan:");

        cmbListPlan.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbListPlan.setForeground(new java.awt.Color(29, 32, 33));
        cmbListPlan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbListPlanItemStateChanged(evt);
            }
        });

        tblCursosSeleccionados.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 1, 12)); // NOI18N
        tblCursosSeleccionados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Descripción", "Créditos"
            }
        ));
        tblCursosSeleccionados.setGridColor(new java.awt.Color(153, 153, 153));
        jScrollPane13.setViewportView(tblCursosSeleccionados);
        if (tblCursosSeleccionados.getColumnModel().getColumnCount() > 0) {
            tblCursosSeleccionados.getColumnModel().getColumn(0).setMinWidth(70);
            tblCursosSeleccionados.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblCursosSeleccionados.getColumnModel().getColumn(0).setMaxWidth(70);
            tblCursosSeleccionados.getColumnModel().getColumn(1).setMinWidth(100);
            tblCursosSeleccionados.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblCursosSeleccionados.getColumnModel().getColumn(1).setMaxWidth(100);
            tblCursosSeleccionados.getColumnModel().getColumn(3).setMinWidth(70);
            tblCursosSeleccionados.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblCursosSeleccionados.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        jLabel23.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(29, 32, 33));
        jLabel23.setText("Ciclo:");

        cmbListCiclo.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbListCiclo.setForeground(new java.awt.Color(29, 32, 33));
        cmbListCiclo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbListCicloItemStateChanged(evt);
            }
        });

        btnAddCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 2, 14)); // NOI18N
        btnAddCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnAddCurso.setText("Agregar");
        btnAddCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCursoActionPerformed(evt);
            }
        });

        btnDeleteCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 2, 14)); // NOI18N
        btnDeleteCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnDeleteCurso.setText("Borrar");
        btnDeleteCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCursoActionPerformed(evt);
            }
        });

        btnLimpiarCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 2, 14)); // NOI18N
        btnLimpiarCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnLimpiarCurso.setText("Limpiar");
        btnLimpiarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarCursoActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(29, 32, 33));
        jLabel24.setText("Curso:");

        cmbListCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        cmbListCurso.setForeground(new java.awt.Color(29, 32, 33));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(cmbListProf, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(cmbListSede, 0, 182, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAddCurso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDeleteCurso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiarCurso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbListPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbListCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbListCurso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cmbListProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(cmbListSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(cmbListPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(cmbListCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(cmbListCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddCurso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCurso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiarCurso)))
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(214, 217, 223));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        btnNuevoCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnNuevoCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnNuevoCurso.setText("Nuevo");
        btnNuevoCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCursoActionPerformed(evt);
            }
        });

        btnGuardarCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnGuardarCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnGuardarCurso.setText("Guardar");
        btnGuardarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCursoActionPerformed(evt);
            }
        });

        btnBuscarCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnBuscarCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnBuscarCurso.setText("Buscar");
        btnBuscarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCursoActionPerformed(evt);
            }
        });

        btnEliminarCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnEliminarCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnEliminarCurso.setText("Eliminar");
        btnEliminarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCursoActionPerformed(evt);
            }
        });

        btnEditarCurso.setFont(new java.awt.Font("JetBrainsMono Nerd Font", 0, 14)); // NOI18N
        btnEditarCurso.setForeground(new java.awt.Color(29, 32, 33));
        btnEditarCurso.setText("Editar");
        btnEditarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCursoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoCurso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarCurso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarCurso, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(btnBuscarCurso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarCurso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(btnNuevoCurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardarCurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarCurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarCurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarCurso)
                .addGap(58, 58, 58))
        );

        jPanel14.setBackground(new java.awt.Color(214, 217, 223));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Visualización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("JetBrainsMono Nerd Font", 2, 12), new java.awt.Color(175, 58, 3))); // NOI18N

        tblProfesorHasCursos.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        tblProfesorHasCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblProfesorHasCursos.setGridColor(new java.awt.Color(153, 153, 153));
        jScrollPane12.setViewportView(tblProfesorHasCursos);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout panCursosLayout = new javax.swing.GroupLayout(panCursos);
        panCursos.setLayout(panCursosLayout);
        panCursosLayout.setHorizontalGroup(
            panCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCursosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panCursosLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        panCursosLayout.setVerticalGroup(
            panCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCursosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cursos", panCursos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents


    private void btnGuardarAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarAdminActionPerformed
        String dni = txtDniAdmin.getText();
        String nombre = txtNomAdmin.getText().trim();
        String apellido = txtApeAdmin.getText().trim();
        int telefono = (txtTelefAdmin.getText().equals("")? 0 : Integer.parseInt(txtTelefAdmin.getText()));
        boolean estado = (cmbEstadoAdmin.getSelectedItem() == "Activo" ? true : false);
        Date fechaActual = Calendar.getInstance().getTime();
        String usuario = txtUsuarioAdmin.getText().trim();
        String password = String.valueOf(pwdPasswordAdmin.getPassword());
        boolean privilegios = (jrbSi.isSelected() ? true : false);
        byte sede = 0;
        Iterator<Sede> it = listSede.iterator();
        Sede aux = null;
        while (it.hasNext()) {            
            aux = it.next();
            if (cmbSedeAdmin.getSelectedItem().equals(aux.getDescripcion()))
                sede = aux.getSedeId();
        }
        int rptaNom = -1;
        int rptaApe = -1;
        //omitimos el telefono ya q puede que usuario no tenga
        if (!dni.equals("") && dni.length()==8 && !nombre.equals("") && !apellido.equals("")  && !usuario.equals("") && !password.equals("")) {
            if (bandAdmin) {//band = true guarda un admin
                objAdm.agregar(dni, nombre, apellido, telefono, fechaActual, usuario, password, privilegios, estado, sede);
            } else { //band = false actualiza un admin
                mEditable(true);
                if (!nombre.equals(auxAdministrador.getNombre()))
                    rptaNom =  Utilitarios.confirmacion("El nombre es un dato sensible, desea modificarlo?", 0);
                if (!apellido.equals(auxAdministrador.getApellido()))
                    rptaApe =  Utilitarios.confirmacion("El apellido es un dato sensible, desea modificarlo?", 0);
                
                if (rptaNom == 0 && rptaApe == 0)
                    objAdm.actualizar(auxAdministrador.getDni(), nombre, apellido, telefono, fechaActual, usuario, password, privilegios, estado, sede);
                else if (rptaNom == 0 && rptaApe == -1)
                    objAdm.actualizar(auxAdministrador.getDni(), nombre, auxAdministrador.getApellido(), telefono, fechaActual, usuario, password, privilegios, estado, sede);
                else if (rptaNom == -1 && rptaApe == 0)
                    objAdm.actualizar(auxAdministrador.getDni(), auxAdministrador.getNombre(), apellido, telefono, fechaActual, usuario, password, privilegios, estado, sede);
                else 
                    objAdm.actualizar(auxAdministrador.getDni(), auxAdministrador.getNombre(), auxAdministrador.getApellido(), telefono, fechaActual, usuario, password, privilegios, estado, sede);
            }
            tblUsuarios.setModel(objAdm.lista(true, privilegio, sedeId));
            configTblUsuarios(true);
            cmbEstadoBuscarAdmin.setSelectedIndex(0);
            mCajasAdmin(false, false, false);
            mBotonesAdmin(true, false, false, false, true);
            mLimpiarAdmin();
        } else {
            if (dni.length() != 8)
                Utilitarios.mensaje("Ingrese el dni de 8 dígitos!!!", 2);
            else
                Utilitarios.mensaje("Ingrese todos los datos del formulario!!!", 2);
        }
    }//GEN-LAST:event_btnGuardarAdminActionPerformed

    private void btnNuevoAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAdminActionPerformed
        bandAdmin = true; //activa para guardar un nuevo elemento
        mLimpiarAdmin();
        if (privilegio == false)
            jrbNo.setSelected(true);
        mCajasAdmin(true, true, privilegio);
        mBotonesAdmin(false, true, false, false, false);
    }//GEN-LAST:event_btnNuevoAdminActionPerformed

    private void btnEditarAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAdminActionPerformed
        bandAdmin = false;
        txtDniAdmin.setEnabled(true);
        mBotonesAdmin(false, false, true, false, false);
    }//GEN-LAST:event_btnEditarAdminActionPerformed

    private void btnBuscarAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAdminActionPerformed
        if (txtDniAdmin.getText().length() == 8) {
            auxAdministrador = new Administrador(txtDniAdmin.getText());
            boolean bande = objAdm.buscar(auxAdministrador);
            if (bande) { //true fue encontrado
                txtNomAdmin.setText(auxAdministrador.getNombre());
                txtApeAdmin.setText(auxAdministrador.getApellido());
                txtTelefAdmin.setText(String.valueOf(auxAdministrador.getTelefono()));
                txtUsuarioAdmin.setText(auxAdministrador.getUsuario());
                pwdPasswordAdmin.setText(auxAdministrador.getPassword());
                cmbEstadoAdmin.setSelectedIndex(auxAdministrador.isEstado() == false? 1 : 0);
                txtFechaAdmin.setText(String.valueOf(auxAdministrador.getLastUpdate()));
                boolean privilegios = auxAdministrador.isPrivilegios();
                if (privilegios == true)
                    jrbSi.setSelected(true);
                else 
                    jrbNo.setSelected(true);
                mEditable(false);
                mCajasAdmin(true, true, privilegio);
                mBotonesAdmin(false, true, false, true, false);
            } else {
                mLimpiarAdmin();
                mCajasAdmin(false, false, false);
                mBotonesAdmin(true, false, false, false, true);
            }
        } else {
            Utilitarios.mensaje("Ingrese el dni de 8 dígitos que desea buscar", 2);
        }
    }//GEN-LAST:event_btnBuscarAdminActionPerformed

    private void btnEliminarAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAdminActionPerformed
        int respuesta = Utilitarios.confirmacion("Desea eliminar el administrador: "+txtNomAdmin.getText()+" "+txtApeAdmin.getText(), 0);
        if (respuesta == 0) {
            objAdm.eliminar(txtDniAdmin.getText());
            configTblUsuarios(true);
            cmbEstadoBuscarAdmin.setSelectedIndex(0);
            mLimpiarAdmin();
            mEditable(true);
            mCajasAdmin(false, false, false);
            mBotonesAdmin(true, false, false, false, true);
        }
    }//GEN-LAST:event_btnEliminarAdminActionPerformed

    private void txtDniAdminKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniAdminKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
            evt.consume();
        if (txtDniAdmin.getText().length() >= 8) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtDniAdminKeyTyped

    private void txtNomAdminKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomAdminKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c!=' ') 
                && (c!='á') && (c!='é')&& (c!='í')&& (c!='ó')&& (c!='ú') 
                && (c!='Á')&& (c!='É')&& (c!='Í')&& (c!='Ó')&& (c!='Ú'))
            evt.consume();
    }//GEN-LAST:event_txtNomAdminKeyTyped

    private void txtApeAdminKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApeAdminKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c!=' ') 
                && (c!='á') && (c!='é')&& (c!='í')&& (c!='ó')&& (c!='ú') 
                && (c!='Á')&& (c!='É')&& (c!='Í')&& (c!='Ó')&& (c!='Ú'))
            evt.consume();                      
    }//GEN-LAST:event_txtApeAdminKeyTyped

    private void txtTelefAdminKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefAdminKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
            evt.consume();
        if (txtTelefAdmin.getText().length() >= 9) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtTelefAdminKeyTyped

    private void btnVisualizarAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarAdminActionPerformed
        if (cmbEstadoBuscarAdmin.getSelectedItem() == "Activo") //default cero
            configTblUsuarios(true);
        else
            configTblUsuarios(false);
        
    }//GEN-LAST:event_btnVisualizarAdminActionPerformed

    private void btnNuevoProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProfActionPerformed
        bandProf = true; //activa para guardar un nuevo elemento
        mLimpiarProf();
        mCajasProf(true, true);
        mBotonesProf(false, true, false, false, false);
    }//GEN-LAST:event_btnNuevoProfActionPerformed

    private void btnGuardarProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProfActionPerformed
        String dni = txtDniProf.getText();
        String nombre = txtNomProf.getText().trim();
        String apellido = txtApeProf.getText().trim();
        int telefono = (txtTelefonoProf.getText().equals("")? 0 : Integer.parseInt(txtTelefonoProf.getText()));
        String correo = txtCorreoProf.getText().trim();
        boolean estado = (cmbEstadoProf.getSelectedItem() == "Activo" ? true : false);
        Date fechaActual = Calendar.getInstance().getTime();
        String usuario = txtUsuarioProf.getText().trim();
        String password = String.valueOf(pwdProf.getPassword());
         
        int rptaNom = -1;
        int rptaApe = -1;
        //omitimos el telefono ya q puede que usuario no tenga
        if (!dni.equals("") && dni.length()==8 && !nombre.equals("") && !apellido.equals("")  && !usuario.equals("") && !password.equals("") && !correo.equals("") && validarEmail(correo)) {
            if (bandProf) {//band = true guarda un profesor
                objProf.agregar(dni, nombre, apellido, telefono, fechaActual, correo, usuario, password, estado);
            } else { //band = false actualiza un admin
                mEditable(true);
                if (!nombre.equals(auxProfesor.getNombre()))
                    rptaNom =  Utilitarios.confirmacion("El nombre es un dato sensible, desea modificarlo?", 0);
                if (!apellido.equals(auxProfesor.getApellido()))
                    rptaApe =  Utilitarios.confirmacion("El apellido es un dato sensible, desea modificarlo?", 0);
                
                if (rptaNom == 0 && rptaApe == 0)
                    objProf.actualizar(auxProfesor.getDni(), nombre, apellido, telefono, fechaActual, correo, usuario, password, estado);
                else if (rptaNom == 0 && rptaApe == -1)
                    objProf.actualizar(auxProfesor.getDni(), nombre, auxProfesor.getApellido(), telefono, fechaActual, correo, usuario, password, estado);
                else if (rptaNom == -1 && rptaApe == 0)
                    objProf.actualizar(auxProfesor.getDni(), auxProfesor.getNombre(), apellido, telefono, fechaActual, correo, usuario, password, estado);
                else 
                    objProf.actualizar(auxProfesor.getDni(), auxProfesor.getNombre(), auxProfesor.getApellido(), telefono, fechaActual, correo, usuario, password, estado);
            }
            configTblProfesores(true);
            cmbBuscarProf.setSelectedIndex(0);
            mCajasProf(false, false);
            mBotonesProf(true, false, false, false, true);
            mLimpiarProf();
        } else {
            if (dni.length() != 8) {
                Utilitarios.mensaje("Ingrese el dni de 8 dígitos!!!", 2);
            }
            else if (!validarEmail(txtCorreoProf.getText())) {
                Utilitarios.mensaje( "¡Debe ingresar un email valid!",2);
            } else 
                Utilitarios.mensaje("Ingrese todos los datos del formulario!!!", 2);
        }
    }//GEN-LAST:event_btnGuardarProfActionPerformed

    private void btnBuscarProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProfActionPerformed
        if (txtDniProf.getText().length() == 8) {
            auxProfesor = new Profesor(txtDniProf.getText());
            if (objProf.buscar(auxProfesor)) { //true fue encontrado
                txtNomProf.setText(auxProfesor.getNombre());
                txtApeProf.setText(auxProfesor.getApellido());
                txtTelefonoProf.setText(String.valueOf(auxProfesor.getTelefono()));
                txtCorreoProf.setText(auxProfesor.getCorreo());
                txtUsuarioProf.setText(auxProfesor.getUsuario());
                pwdProf.setText(auxProfesor.getPassword());
                cmbEstadoProf.setSelectedIndex(auxProfesor.isEstado() == false? 1 : 0);
                txtFechaProf.setText(String.valueOf(auxProfesor.getLastUpdate()));
                
                mEditableProf(false);
                mCajasProf(true, true);
                mBotonesProf(false, true, false, true, false);
            } else {
                mLimpiarAdmin();
                mCajasAdmin(false, false, false);
                mBotonesAdmin(true, false, false, false, true);
            }
        } else {
            Utilitarios.mensaje("Ingrese el dni de 8 dígitos que desea buscar", 2);
        }
    }//GEN-LAST:event_btnBuscarProfActionPerformed

    private void btnEliminarProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProfActionPerformed
        int respuesta = Utilitarios.confirmacion("Desea eliminar el profesor con Dni:  "+txtDniProf.getText(), 0);
        if (respuesta == 0) {
            objProf.eliminar(txtDniProf.getText());
            configTblProfesores(true);
            cmbBuscarProf.setSelectedIndex(0);
            mLimpiarProf();
            mEditableProf(true);
            mCajasProf(false, false);
            mBotonesProf(true, false, false, false, true);
        }
    }//GEN-LAST:event_btnEliminarProfActionPerformed

    private void btnEditarProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProfActionPerformed
        bandProf = false;
        mCajasProf(true, false);
        mEditableProf(true);
        mBotonesProf(false, false, true, false, false);
    }//GEN-LAST:event_btnEditarProfActionPerformed

    private void btnBuscarProfesoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProfesoresActionPerformed
        if (cmbBuscarProf.getSelectedItem() == "Activo") //default cero
            configTblProfesores(true);
        else
            configTblProfesores(false);
    }//GEN-LAST:event_btnBuscarProfesoresActionPerformed

    private void txtDniProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniProfKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
            evt.consume();
        if (txtDniProf.getText().length() >= 8) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtDniProfKeyTyped

    private void txtNomProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomProfKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c!=' ') 
                && (c!='á') && (c!='é')&& (c!='í')&& (c!='ó')&& (c!='ú') 
                && (c!='Á')&& (c!='É')&& (c!='Í')&& (c!='Ó')&& (c!='Ú'))
            evt.consume();
    }//GEN-LAST:event_txtNomProfKeyTyped

    private void txtApeProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApeProfKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c!=' ') 
                && (c!='á') && (c!='é')&& (c!='í')&& (c!='ó')&& (c!='ú') 
                && (c!='Á')&& (c!='É')&& (c!='Í')&& (c!='Ó')&& (c!='Ú'))
            evt.consume();    
    }//GEN-LAST:event_txtApeProfKeyTyped

    private void txtTelefonoProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProfKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
            evt.consume();
        if (txtTelefonoProf.getText().length() >= 9) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtTelefonoProfKeyTyped

    private void txtCorreoProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoProfKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9') && (c!='_') 
                && (c!='.') && (c!='-') && (c!='@'))
            evt.consume();  
   
        if(txtCorreoProf.getText().length() >= 50){
                evt.consume(); //hace que esa pulsación de tecla se rechace.
                Toolkit.getDefaultToolkit().beep(); //sonido de no aceptar más caracteres.
            }
        
    }//GEN-LAST:event_txtCorreoProfKeyTyped

    /***************************************************************************
    ******************* RELACION DE CURSOS CON PROFESORES **********************
    ****************************************************************************/
    
    /**
     * Retorna el profesor seleccionado en el JComboBox
     * @return 
     */
    private Profesor selectProfesor(){
        Iterator<Profesor> itP = listProfesor.iterator();
        Profesor p = null;
        while (itP.hasNext()) {            
            p = itP.next();
            if (cmbListProf.getSelectedItem().equals(p.getNombre()+" "+p.getApellido())) 
                break;
        }
        return p;
    }
    
    /**
     * Retorna la sede seleccionada en el JComboBox
     * @return 
     */
    private Sede selectSede(){
        Iterator<Sede> itS = listSede.iterator();
        Sede s = null;
        while (itS.hasNext()) {            
            s = itS.next();
            if (cmbListSede.getSelectedItem().equals(s.getDescripcion())) 
                break;
        }
        return s;
    }
    
    /**
     * Retorna el curso seleccionado en el JComboBox para ser agregadfo a la tabla de seleccionados
     * @return 
     */
    private Curso selectCurso(){
        Iterator<Curso> itCu = listCursos.iterator();
        Curso c = null;
        while (itCu.hasNext()) {            
            c = itCu.next();
            if (cmbListCurso.getSelectedItem().equals(c.getDescripcion())) 
                break;
        }
        return c;
    }
    
    
    
    private void btnNuevoCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCursoActionPerformed
        bandCursos = true; //activa para guardar un nuevo elemento
        //carga el jcombobox de profesores
        cargarListProf();
        //carca el jcombobox de sede
        cargarListSede();
        //carga el jcombobox de planes curriculares
        cargarListPlan();
        //carga el jcombobox de ciclo
        cargarListCiclo();
        //carga los cursos en el jcombobox
        cargarListCurso();
        
        mResetCursos();
        mCajasCursos(true, true);
        mBotonesCursos(false, true, false, false, false, true);
    }//GEN-LAST:event_btnNuevoCursoActionPerformed

    private void btnGuardarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCursoActionPerformed
        DefaultTableModel modelo = (DefaultTableModel)tblCursosSeleccionados.getModel();
             
        if (modelo.getRowCount() > 0) {
            Profesor profesor = selectProfesor(); //capturamos el profesor seleccionado
            Sede sede = selectSede(); //capturamos la sede seleccionada
            if (bandCursos) {//band = true relaciona el profesor con la sede y los cursos                
                //relacionamos el profesor con la sede
                String nombre = profesor.getNombre() +" "+ profesor.getApellido();
                objProf.agregar(profesor.getDni(), nombre, sede.getSedeId());
                //agregamos los cursos q estan en la tabla
                //recorremos la lista de cursos agregados
                Iterator<Curso> itC = cursosAgregados.iterator();
                Curso c;
                while (itC.hasNext()) {
                    c = itC.next();
                    objProf.agregar(profesor.getDni(), c.getCursoId(), sede.getSedeId(), c.getDescripcion());
                }
                
            } else { //band = false actualiza un admin
//                mEditable(true);
//                if (!nombre.equals(auxProfesor.getNombre()))
//                    rptaNom =  Utilitarios.confirmacion("El nombre es un dato sensible, desea modificarlo?", 0);
//                if (!apellido.equals(auxProfesor.getApellido()))
//                    rptaApe =  Utilitarios.confirmacion("El apellido es un dato sensible, desea modificarlo?", 0);
//                
//                if (rptaNom == 0 && rptaApe == 0)
//                    objProf.actualizar(auxProfesor.getDni(), nombre, apellido, telefono, fechaActual, correo, usuario, password, estado);
//                else if (rptaNom == 0 && rptaApe == -1)
//                    objProf.actualizar(auxProfesor.getDni(), nombre, auxProfesor.getApellido(), telefono, fechaActual, correo, usuario, password, estado);
//                else if (rptaNom == -1 && rptaApe == 0)
//                    objProf.actualizar(auxProfesor.getDni(), auxProfesor.getNombre(), apellido, telefono, fechaActual, correo, usuario, password, estado);
//                else 
//                    objProf.actualizar(auxProfesor.getDni(), auxProfesor.getNombre(), auxProfesor.getApellido(), telefono, fechaActual, correo, usuario, password, estado);
            }
            configTblCursos();
            configTblProfesores(true); //actualiza la tabla de profesores
            mCajasCursos(false, false);
            limpiarTabla();//limpia la tabla
            mBotonesCursos(true, false, false, false, true, false);
            mLimpiarCursos();
        } else {
            Utilitarios.mensaje("Agregue cursos a la tabla!!!", 2);
        }
    }//GEN-LAST:event_btnGuardarCursoActionPerformed

    private void btnBuscarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarCursoActionPerformed

    private void btnEliminarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarCursoActionPerformed

    private void btnEditarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarCursoActionPerformed

    private void btnAddCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCursoActionPerformed
        DefaultTableModel modelo = (DefaultTableModel)tblCursosSeleccionados.getModel();
        boolean encontrado = false;
        String descripcion = "";
        for (int i = 0; i < tblCursosSeleccionados.getRowCount(); i++) {
            descripcion = tblCursosSeleccionados.getValueAt(i, 2).toString();
            if (descripcion.equals(cmbListCurso.getSelectedItem().toString()))
                encontrado = true;
        }
        if (!encontrado) {
            Curso curso = selectCurso();
            cursosAgregados.add(curso); //agregamos a una lista local
            Object[] fila = new Object[4];
            fila[0] = curso.getCursoId();
            fila[1] = curso.getCodigo();
            fila[2] = curso.getDescripcion();
            fila[3] = curso.getCreditos();
            modelo.addRow(fila);
            tblCursosSeleccionados.setModel(modelo); //agregamos a la tabla
        }

    }//GEN-LAST:event_btnAddCursoActionPerformed

  
    private void btnDeleteCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCursoActionPerformed
        DefaultTableModel modelo = (DefaultTableModel)tblCursosSeleccionados.getModel();
        int confirmacion = -1;
        int fila = tblCursosSeleccionados.getSelectedRow();
        
        if (fila < 0) 
            Utilitarios.mensaje("Debe seleccionar una fila de la tabla", 1);
        else 
            confirmacion = Utilitarios.confirmacion("Esta seguro que desea eliminar el registro?", 0);
        
        //verificamos
        if (confirmacion == 0) {
            modelo.removeRow(fila);
            cursosAgregados.remove(fila);
            Utilitarios.mensaje("Registro Eliminado", 1);
        }
    }//GEN-LAST:event_btnDeleteCursoActionPerformed

    private void btnLimpiarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCursoActionPerformed
        int confirmacion = Utilitarios.confirmacion("Esta seguro que desea eliminar los datos de la tabla?", 0);
        
        //verificamos
        if (confirmacion == 0) {
            limpiarTabla();
        }
        
    }//GEN-LAST:event_btnLimpiarCursoActionPerformed

    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel)tblCursosSeleccionados.getModel();
        while(modelo.getRowCount() > 0)
            modelo.removeRow(0);
        cursosAgregados.remove();
    }
    
    private void cmbListPlanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbListPlanItemStateChanged
        //carga los cursos en el jcombobox
        cargarListCurso();
    }//GEN-LAST:event_cmbListPlanItemStateChanged

    private void cmbListCicloItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbListCicloItemStateChanged
        //carga los cursos en el jcombobox
        cargarListCurso();
    }//GEN-LAST:event_cmbListCicloItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgAdministrador;
    private javax.swing.JButton btnAddCurso;
    private javax.swing.JButton btnBuscarAdmin;
    private javax.swing.JButton btnBuscarCurso;
    private javax.swing.JButton btnBuscarProf;
    private javax.swing.JButton btnBuscarProfesores;
    private javax.swing.JButton btnDeleteCurso;
    private javax.swing.JButton btnEditarAdmin;
    private javax.swing.JButton btnEditarCurso;
    private javax.swing.JButton btnEditarProf;
    private javax.swing.JButton btnEliminarAdmin;
    private javax.swing.JButton btnEliminarCurso;
    private javax.swing.JButton btnEliminarProf;
    private javax.swing.JButton btnGuardarAdmin;
    private javax.swing.JButton btnGuardarCurso;
    private javax.swing.JButton btnGuardarProf;
    private javax.swing.JButton btnLimpiarCurso;
    private javax.swing.JButton btnNuevoAdmin;
    private javax.swing.JButton btnNuevoCurso;
    private javax.swing.JButton btnNuevoProf;
    private javax.swing.JButton btnVisualizarAdmin;
    private javax.swing.JComboBox<String> cmbBuscarProf;
    private javax.swing.JComboBox<String> cmbEstadoAdmin;
    private javax.swing.JComboBox<String> cmbEstadoBuscarAdmin;
    private javax.swing.JComboBox<String> cmbEstadoProf;
    private javax.swing.JComboBox<String> cmbListCiclo;
    private javax.swing.JComboBox<String> cmbListCurso;
    private javax.swing.JComboBox<String> cmbListPlan;
    private javax.swing.JComboBox<String> cmbListProf;
    private javax.swing.JComboBox<String> cmbListSede;
    private javax.swing.JComboBox<String> cmbSedeAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton jrbNo;
    private javax.swing.JRadioButton jrbSi;
    private javax.swing.JPanel panAdmin;
    private javax.swing.JPanel panCursos;
    private javax.swing.JPanel panProf;
    private javax.swing.JPasswordField pwdPasswordAdmin;
    private javax.swing.JPasswordField pwdProf;
    private javax.swing.JTable tblCursosSeleccionados;
    private javax.swing.JTable tblProfesorHasCursos;
    private javax.swing.JTable tblProfesores;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtApeAdmin;
    private javax.swing.JTextField txtApeProf;
    private javax.swing.JTextField txtCorreoProf;
    private javax.swing.JTextField txtDniAdmin;
    private javax.swing.JTextField txtDniProf;
    private javax.swing.JLabel txtFechaAdmin;
    private javax.swing.JLabel txtFechaProf;
    private javax.swing.JTextField txtNomAdmin;
    private javax.swing.JTextField txtNomProf;
    private javax.swing.JTextField txtTelefAdmin;
    private javax.swing.JTextField txtTelefonoProf;
    private javax.swing.JTextField txtUsuarioAdmin;
    private javax.swing.JTextField txtUsuarioProf;
    // End of variables declaration//GEN-END:variables

    /**
     * cambia el estado del formulario segun el boton selecionado
     *
     * @param b bandera para controlar el estado
     */
    private void mCajasAdmin(boolean a, boolean b, boolean priv) {
        txtDniAdmin.setEnabled(a);
        txtNomAdmin.setEnabled(b);
        txtApeAdmin.setEnabled(b);
        txtTelefAdmin.setEnabled(b);
        txtUsuarioAdmin.setEnabled(b);
        pwdPasswordAdmin.setEnabled(b);
        cmbEstadoAdmin.setEnabled(b);
        jrbSi.setEnabled(priv);
        jrbNo.setEnabled(priv);
        cmbSedeAdmin.setEnabled(b);
        txtFechaAdmin.setEnabled(b);
    }
    
    private void mEditable(boolean b) {
        txtDniAdmin.setEditable(b);
    }

    private void mBotonesAdmin(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNuevoAdmin.setEnabled(a);
        btnGuardarAdmin.setEnabled(b);
        btnBuscarAdmin.setEnabled(c);
        btnEliminarAdmin.setEnabled(d);
        btnEditarAdmin.setEnabled(e);
    }

    private void mLimpiarAdmin() {
        txtDniAdmin.setText("");
        txtNomAdmin.setText("");
        txtApeAdmin.setText("");
        txtTelefAdmin.setText("");
        txtUsuarioAdmin.setText("");
        pwdPasswordAdmin.setText("");
        cmbEstadoAdmin.setSelectedIndex(0);
        cmbSedeAdmin.setSelectedIndex(0);
        jrbSi.setSelected(true);
        txtFechaAdmin.setText("");
    }
    
    /**
     * cambia el estado del formulario segun el boton selecionado
     *
     * @param b bandera para controlar el estado
     */
    private void mCajasProf(boolean a, boolean b) {
        txtDniProf.setEnabled(a);
        txtNomProf.setEnabled(b);
        txtApeProf.setEnabled(b);
        txtTelefonoProf.setEnabled(b);
        txtCorreoProf.setEnabled(b);
        txtUsuarioProf.setEnabled(b);
        pwdProf.setEnabled(b);
        cmbEstadoProf.setEnabled(b);
        txtFechaProf.setEnabled(b);
    }
    
    private void mEditableProf(boolean b) {
        txtDniProf.setEditable(b);
    }

    private void mBotonesProf(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNuevoProf.setEnabled(a);
        btnGuardarProf.setEnabled(b);
        btnBuscarProf.setEnabled(c);
        btnEliminarProf.setEnabled(d);
        btnEditarProf.setEnabled(e);
    }

    private void mLimpiarProf() {
        txtDniProf.setText("");
        txtNomProf.setText("");
        txtApeProf.setText("");
        txtTelefonoProf.setText("");
        txtCorreoProf.setText("");
        txtUsuarioProf.setText("");
        pwdProf.setText("");
        cmbEstadoProf.setSelectedIndex(0);
        txtFechaProf.setText("");
    }
    
    /**
     * cambia el estado del formulario segun el boton selecionado
     *
     * @param b bandera para controlar el estado
     */
    private void mCajasCursos(boolean a, boolean b) {
        cmbListProf.setEnabled(a);
        cmbListSede.setEnabled(a);
        cmbListPlan.setEnabled(b);
        cmbListCiclo.setEnabled(b);
        cmbListCurso.setEnabled(b);
    }
    
    private void mEditableCursos(boolean b) {
        txtDniProf.setEditable(b);
    }

    private void mBotonesCursos(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f) {
        btnNuevoCurso.setEnabled(a);
        btnGuardarCurso.setEnabled(b);
        btnBuscarCurso.setEnabled(c);
        btnEliminarCurso.setEnabled(d);
        btnEditarCurso.setEnabled(e);
        btnAddCurso.setEnabled(f);
        btnDeleteCurso.setEnabled(f);
        btnLimpiarCurso.setEnabled(f);
    }

    private void mLimpiarCursos() {
        cmbListPlan.removeAllItems();
        cmbListProf.removeAllItems();
        cmbListSede.removeAllItems();
        cmbListPlan.removeAllItems();
        cmbListCiclo.removeAllItems();
        
//        cmbListProf.setSelectedIndex(0);
//        cmbListSede.setSelectedIndex(0);
//        cmbListPlan.setSelectedIndex(0);
//        cmbListCiclo.setSelectedIndex(0);
//        cmbListCurso.setSelectedIndex(0);
    }
    
    private void mResetCursos() {
        cmbListProf.setSelectedIndex(0);
        cmbListSede.setSelectedIndex(0);
        cmbListPlan.setSelectedIndex(0);
        cmbListCiclo.setSelectedIndex(0);
//        cmbListCurso.setSelectedIndex(0);
    }
}
