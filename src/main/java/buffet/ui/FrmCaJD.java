package buffet.ui;

import buffet.dao.CaDAO;
import buffet.dao.NhanVienDAO;
import buffet.model.Ca;
import buffet.model.NhanVien;
import buffet.utils.XAuth;
import buffet.utils.XDialog;
import buffet.utils.XDate;
import buffet.utils.XValidate;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author duyplus
 */
public class FrmCaJD extends javax.swing.JDialog {

    CaDAO cadao = new CaDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    int row = -1;

    /**
     * Creates new form QLCaJD
     *
     * @param parent
     * @param modal
     */
    public FrmCaJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.fillTable(); // đổ dữ liệu người dùng vào bảng
        this.updateStatus(); // cập nhật trạng thái form
        this.fillComboBox(); // đổ dữ liệu lên combobox

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel kh = tblCa.getColumnModel();
        kh.getColumn(0).setCellRenderer(render);
        kh.getColumn(0).setMaxWidth(100);
        kh.getColumn(1).setCellRenderer(render);
        kh.getColumn(2).setCellRenderer(render);
        kh.getColumn(3).setCellRenderer(render);
        kh.getColumn(4).setCellRenderer(render);
        kh.getColumn(5).setCellRenderer(render);
        kh.getColumn(5).setMaxWidth(100);

        Font font = new Font("Be Vietnam Pro", Font.PLAIN, 16);
        tblCa.getTableHeader().setFont(font);
        tblCa.getTableHeader().setPreferredSize(new Dimension(100, 35));
    }

    @SuppressWarnings("unchecked")
    private void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNhanVen.getModel();
        model.removeAllElements();
        List<NhanVien> list = nvdao.selectAll();
        for (NhanVien nv : list) {
            model.addElement(nv);
        }
    }

    private void selectMaNV() {
        NhanVien model = (NhanVien) cboNhanVen.getSelectedItem();
        txtMaNV.setText(String.valueOf(model.getMaNV()));
        this.fillTable();
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblCa.getModel();
        model.setRowCount(0);
        try {
            List<Ca> list = cadao.selectAll();
            for (Ca ca : list) {
                Object[] row = {
                    ca.getMaCa(),
                    XDate.toString(ca.getNgayLam()),
                    ca.getCaLam(),
                    XDate.toStringTime(ca.getBatDau()),
                    XDate.toStringTime(ca.getKetThuc()),
                    ca.getMaNV()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu ca làm!");
            e.printStackTrace();
        }
    }

    private void insert() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chứ năng này!");
        } else {
            Ca model = this.getForm();
            try {
                cadao.insert(model); // thêm mới
                this.fillTable(); // đỗ lại bảng
                this.clearForm(); // xóa trắng form
                XDialog.alert(this, "Thêm ca mới thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Thêm ca mới thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chứ năng này!");
        } else {
            Ca model = this.getForm();
            int maca = (Integer) tblCa.getValueAt(this.row, 0);
            model.setMaCa(maca);
            try {
                cadao.update(model); // cập nhật
                this.fillTable(); // đổ lại bảng
                XDialog.alert(this, "Cập nhật ca thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Cập nhật ca thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chứ năng này!");
        } else if (XDialog.confirm(this, "Bạn có thực sự muốn xóa ca làm này không?")) {
            Integer maca = (Integer) tblCa.getValueAt(this.row, 0);
            try {
                cadao.delete(String.valueOf(maca)); // xoá
                this.fillTable();
                this.clearForm();
                XDialog.alert(this, "Xóa ca thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Xóa ca thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void clearForm() {
        Ca model = new Ca();
        this.setForm(model);
        this.row = -1;
        this.updateStatus();
    }

    private void edit() {
        Integer maca = (Integer) tblCa.getValueAt(this.row, 0);
        Ca model = cadao.selectById(String.valueOf(maca));
        this.setForm(model);
        this.updateStatus();
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    private void setForm(Ca model) {
        txtNgay.setDate(model.getNgayLam());
        txtCa.setText(model.getCaLam());
        cboHoursBD.setSelectedIndex(model.getBatDau().getHours());
        if (model.getBatDau().getMinutes() == 0) {
            cboMinuteBD.setSelectedIndex(0);
        } else {
            cboMinuteBD.setSelectedItem(String.valueOf(model.getBatDau().getMinutes()));
        }
        cboHoursKT.setSelectedIndex(model.getKetThuc().getHours());
        if (model.getKetThuc().getMinutes() == 0) {
            cboMinuteKT.setSelectedIndex(0);
        } else {
            cboMinuteKT.setSelectedItem(String.valueOf(model.getKetThuc().getMinutes()));
        }
        txtMaNV.setText(model.getMaNV());
    }

    private Ca getForm() {
        Ca model = new Ca();
        model.setNgayLam(txtNgay.getDate());
        model.setCaLam(txtCa.getText());
        model.setBatDau(XDate.toTime(cboHoursBD.getSelectedItem().toString() + ":" + cboMinuteBD.getSelectedItem()));
        model.setKetThuc(XDate.toTime(cboHoursKT.getSelectedItem().toString() + ":" + cboMinuteKT.getSelectedItem()));
        model.setMaNV(txtMaNV.getText());
        return model;
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        // Trạng thái form
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    private void showName() {
        lblStaff.setText(XAuth.user.getTaiKhoan());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pblBan = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlSideBody = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        pnlStaff = new javax.swing.JPanel();
        lblStaff = new javax.swing.JLabel();
        pnlFoot = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        lblNV = new javax.swing.JLabel();
        cboNhanVen = new javax.swing.JComboBox<>();
        lblNgay = new javax.swing.JLabel();
        lblCa = new javax.swing.JLabel();
        txtCa = new javax.swing.JTextField();
        lblBatDau = new javax.swing.JLabel();
        lblKetThuc = new javax.swing.JLabel();
        jspCa = new javax.swing.JScrollPane();
        tblCa = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtNgay = new com.toedter.calendar.JDateChooser();
        cboHoursBD = new javax.swing.JComboBox<>();
        cboMinuteBD = new javax.swing.JComboBox<>();
        cboHoursKT = new javax.swing.JComboBox<>();
        cboMinuteKT = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ CA");
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pblBan.setBackground(new java.awt.Color(56, 182, 255));
        pblBan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pblBan.setForeground(new java.awt.Color(255, 255, 255));

        pnlSidebar.setBackground(new java.awt.Color(255, 255, 255));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo-app.png"))); // NOI18N

        pnlSideBody.setBackground(new java.awt.Color(255, 255, 255));

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-user.png"))); // NOI18N

        pnlStaff.setBackground(new java.awt.Color(255, 255, 255));
        pnlStaff.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        lblStaff.setFont(new java.awt.Font("Be Vietnam Pro", 1, 30)); // NOI18N
        lblStaff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlStaffLayout = new javax.swing.GroupLayout(pnlStaff);
        pnlStaff.setLayout(pnlStaffLayout);
        pnlStaffLayout.setHorizontalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStaff, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlStaffLayout.setVerticalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStaff, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlSideBodyLayout = new javax.swing.GroupLayout(pnlSideBody);
        pnlSideBody.setLayout(pnlSideBodyLayout);
        pnlSideBodyLayout.setHorizontalGroup(
            pnlSideBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSideBodyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSideBodyLayout.setVerticalGroup(
            pnlSideBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideBodyLayout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        pnlFoot.setBackground(new java.awt.Color(0, 0, 0));
        pnlFoot.setPreferredSize(new java.awt.Dimension(256, 55));

        lblFoot.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        lblFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoot.setText("CA");

        javax.swing.GroupLayout pnlFootLayout = new javax.swing.GroupLayout(pnlFoot);
        pnlFoot.setLayout(pnlFootLayout);
        pnlFootLayout.setHorizontalGroup(
            pnlFootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFootLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(lblFoot, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFootLayout.setVerticalGroup(
            pnlFootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFoot, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlSideBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(pnlFoot, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addComponent(lblLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSideBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblExit.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setText("x");
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        lblNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNV.setText("DS nhân viên:");

        cboNhanVen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboNhanVen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboNhanVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNhanVenActionPerformed(evt);
            }
        });

        lblNgay.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNgay.setText("Ngày:");

        lblCa.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblCa.setText("Ca:");

        txtCa.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtCa.setToolTipText("Ca");

        lblBatDau.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblBatDau.setText("Giờ bắt đầu:");

        lblKetThuc.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblKetThuc.setText("Giờ kết thúc:");

        tblCa.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        tblCa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ CA", "NGÀY", "CA", "TG BẮT ĐẦU", "TG KẾT THÚC", "MÃ NV"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCa.setRowHeight(30);
        tblCa.getTableHeader().setReorderingAllowed(false);
        tblCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCaMouseClicked(evt);
            }
        });
        jspCa.setViewportView(tblCa);

        btnThem.setBackground(new java.awt.Color(0, 80, 143));
        btnThem.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("THÊM");
        btnThem.setBorderPainted(false);
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 80, 143));
        btnXoa.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("XOÁ");
        btnXoa.setBorderPainted(false);
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(0, 80, 143));
        btnCapNhat.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("CẬP NHẬT");
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapNhatMouseClicked(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(0, 80, 143));
        btnMoi.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("MỚI");
        btnMoi.setBorderPainted(false);
        btnMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMoiMouseClicked(evt);
            }
        });

        lblMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMaNV.setText("Mã NV:");

        txtMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtMaNV.setToolTipText("Mã NV");

        txtNgay.setToolTipText("Ngày");
        txtNgay.setDateFormatString("dd/MM/yyyy");
        txtNgay.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N

        cboHoursBD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboHoursBD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));

        cboMinuteBD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboMinuteBD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30" }));

        cboHoursKT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboHoursKT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));

        cboMinuteKT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboMinuteKT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30" }));

        javax.swing.GroupLayout pblBanLayout = new javax.swing.GroupLayout(pblBan);
        pblBan.setLayout(pblBanLayout);
        pblBanLayout.setHorizontalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pblBanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jspCa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pblBanLayout.createSequentialGroup()
                                .addComponent(lblNV)
                                .addGap(22, 22, 22)
                                .addComponent(cboNhanVen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pblBanLayout.createSequentialGroup()
                                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgay)
                                    .addComponent(lblCa)
                                    .addComponent(lblBatDau)
                                    .addComponent(lblMaNV))
                                .addGap(29, 29, 29)
                                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaNV)
                                    .addComponent(txtCa)
                                    .addComponent(txtNgay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pblBanLayout.createSequentialGroup()
                                        .addComponent(cboHoursBD, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboMinuteBD, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblKetThuc)
                                        .addGap(26, 26, 26)
                                        .addComponent(cboHoursKT, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboMinuteKT, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)))
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pblBanLayout.setVerticalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addComponent(lblExit)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pblBanLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboNhanVen, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(lblNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNgay, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(lblNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCa, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(lblCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboMinuteKT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboHoursKT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboHoursBD)
                            .addComponent(cboMinuteBD, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                        .addGap(72, 72, 72)))
                .addComponent(jspCa, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pblBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pblBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaNV)
                && XValidate.checkNullDate(txtNgay)
                && XValidate.checkNullText(txtCa)) {
            if (XValidate.checkName(txtCa)) {
                this.insert();
            }
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnXoaMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaNV)
                && XValidate.checkNullDate(txtNgay)
                && XValidate.checkNullText(txtCa)) {
            if (XValidate.checkMa(txtMaNV)
                    && XValidate.checkName(txtCa)) {
                this.update();
            }
        }
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseClicked
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnMoiMouseClicked

    private void tblCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblCa.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblCaMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.showName();
    }//GEN-LAST:event_formWindowOpened

    private void cboNhanVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNhanVenActionPerformed
        // TODO add your handling code here:
        this.selectMaNV();
    }//GEN-LAST:event_cboNhanVenActionPerformed

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboHoursBD;
    private javax.swing.JComboBox<String> cboHoursKT;
    private javax.swing.JComboBox<String> cboMinuteBD;
    private javax.swing.JComboBox<String> cboMinuteKT;
    private javax.swing.JComboBox<String> cboNhanVen;
    private javax.swing.JScrollPane jspCa;
    private javax.swing.JLabel lblBatDau;
    private javax.swing.JLabel lblCa;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblKetThuc;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNV;
    private javax.swing.JLabel lblNgay;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JPanel pblBan;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlSideBody;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JTable tblCa;
    private javax.swing.JTextField txtCa;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgay;
    // End of variables declaration//GEN-END:variables
}
