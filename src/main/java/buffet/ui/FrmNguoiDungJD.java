package buffet.ui;

import buffet.dao.NguoiDungDAO;
import buffet.model.NguoiDung;
import buffet.utils.XAuth;
import buffet.utils.XDialog;
import buffet.utils.XValidate;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author duyplus
 */
public class FrmNguoiDungJD extends javax.swing.JDialog {

    NguoiDungDAO nddao = new NguoiDungDAO();
    int row = -1;

    /**
     * Creates new form QLNguoiDungJD
     *
     * @param parent
     * @param modal
     */
    public FrmNguoiDungJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.fillTable(); // đổ dữ liệu người dùng vào bảng
        this.updateStatus(); // cập nhật trạng thái form

        Font font = new Font("Be Vietnam Pro", Font.PLAIN, 16);
        tblNguoiDung.getTableHeader().setFont(font);
        tblNguoiDung.getTableHeader().setPreferredSize(new Dimension(100, 35));
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        try {
            List<NguoiDung> list = nddao.selectAll();
            for (NguoiDung nd : list) {
                Object[] row = {
                    nd.getTaiKhoan(),
                    hidePass(nd.getMatKhau()),
                    nd.getHoTen(),
                    nd.isVaiTro() ? "Quản lý" : "Nhân viên"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu người dùng!");
            e.printStackTrace();
        }
    }

    private void insert() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chức năng này!");
        } else {
            NguoiDung model = this.getForm();
            try {
                nddao.insert(model); // thêm mới
                this.fillTable(); // đỗ lại bảng
                this.clearForm(); // xóa trắng form
                XDialog.alert(this, "Thêm người dùng mới thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Thêm người dùng mới thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chức năng này!");
        } else {
            NguoiDung model = this.getForm();
            try {
                nddao.update(model); // cập nhật
                this.fillTable(); // đổ lại bảng
                XDialog.alert(this, "Cập nhật người dùng thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Cập nhật người dùng thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chức năng này!");
        } else {
            String mand = txtTK.getText();
            if (mand.equals(XAuth.user.getTaiKhoan())) {
                XDialog.alert(this, "Bạn không được phép xóa chính mình!");
            } else if (XDialog.confirm(this, "Bạn có thực sự muốn xóa người dùng này không?")) {
                try {
                    nddao.delete(mand);
                    this.fillTable();
                    this.clearForm();
                    XDialog.alert(this, "Xóa người dùng thành công!");
                } catch (Exception e) {
                    XDialog.alert(this, "Xóa người dùng thất bại!");
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearForm() {
        NguoiDung model = new NguoiDung();
        this.setForm(model);
        this.row = -1;
        this.updateStatus();
    }

    private void edit() {
        String mand = (String) tblNguoiDung.getValueAt(this.row, 0);
        NguoiDung model = nddao.selectById(mand);
        this.setForm(model);
        this.updateStatus();
    }

    private void setForm(NguoiDung model) {
        txtTK.setText(model.getTaiKhoan());
        txtMK.setText(model.getMatKhau());
        txtHoTen.setText(model.getHoTen());
        boolean vaitro = model.isVaiTro();
        if (vaitro == true) {
            rdoQuanLy.setSelected(true);
        } else {
            rdoNhanVien.setSelected(true);
        }
    }

    private NguoiDung getForm() {
        NguoiDung model = new NguoiDung();
        model.setTaiKhoan(txtTK.getText());
        model.setMatKhau(new String(txtMK.getPassword()));
        model.setHoTen(txtHoTen.getText());
        boolean vaitro = true;
        if (rdoNhanVien.isSelected()) {
            vaitro = false;
        } else if (rdoQuanLy.isSelected()) {
            vaitro = true;
        }
        model.setVaiTro(vaitro);
        return model;
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        // Trạng thái form
        txtTK.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    // Ẩn mật khẩu
    private String hidePass(String pass) {
        String hide = "";
        for (int i = 0; i < pass.length(); i++) {
            hide += "*";
        }
        return hide;
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

        bgrVaiTro = new javax.swing.ButtonGroup();
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
        pnlInfo = new javax.swing.JPanel();
        lblTK = new javax.swing.JLabel();
        pnlTK = new javax.swing.JPanel();
        txtTK = new javax.swing.JTextField();
        lblMK = new javax.swing.JLabel();
        pnlMK = new javax.swing.JPanel();
        txtMK = new javax.swing.JPasswordField();
        lblHoTen = new javax.swing.JLabel();
        pnlHoTen = new javax.swing.JPanel();
        txtHoTen = new javax.swing.JTextField();
        lblVaiTro = new javax.swing.JLabel();
        rdoNhanVien = new javax.swing.JRadioButton();
        rdoQuanLy = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jspNguoiDung = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ NGƯỜI DÙNG");
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pblBan.setBackground(new java.awt.Color(56, 182, 255));
        pblBan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFoot.setBackground(new java.awt.Color(0, 0, 0));
        pnlFoot.setPreferredSize(new java.awt.Dimension(256, 55));

        lblFoot.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        lblFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoot.setText("NGƯỜI DÙNG");

        javax.swing.GroupLayout pnlFootLayout = new javax.swing.GroupLayout(pnlFoot);
        pnlFoot.setLayout(pnlFootLayout);
        pnlFootLayout.setHorizontalGroup(
            pnlFootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFootLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFoot)
                .addGap(94, 94, 94))
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

        pnlInfo.setBackground(new java.awt.Color(56, 182, 255));

        lblTK.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTK.setText("Tài khoản:");

        pnlTK.setBackground(new java.awt.Color(255, 255, 255));
        pnlTK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTK.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtTK.setToolTipText("Tài khoản");
        txtTK.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtTK.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlTKLayout = new javax.swing.GroupLayout(pnlTK);
        pnlTK.setLayout(pnlTKLayout);
        pnlTKLayout.setHorizontalGroup(
            pnlTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTK, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlTKLayout.setVerticalGroup(
            pnlTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTKLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblMK.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMK.setText("Mật khẩu:");

        pnlMK.setBackground(new java.awt.Color(255, 255, 255));
        pnlMK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtMK.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtMK.setToolTipText("Mật khẩu");

        javax.swing.GroupLayout pnlMKLayout = new javax.swing.GroupLayout(pnlMK);
        pnlMK.setLayout(pnlMKLayout);
        pnlMKLayout.setHorizontalGroup(
            pnlMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMK, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
        );
        pnlMKLayout.setVerticalGroup(
            pnlMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMK, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        lblHoTen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblHoTen.setText("Họ tên:");

        pnlHoTen.setBackground(new java.awt.Color(255, 255, 255));
        pnlHoTen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtHoTen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtHoTen.setToolTipText("Họ tên");
        txtHoTen.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtHoTen.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlHoTenLayout = new javax.swing.GroupLayout(pnlHoTen);
        pnlHoTen.setLayout(pnlHoTenLayout);
        pnlHoTenLayout.setHorizontalGroup(
            pnlHoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlHoTenLayout.setVerticalGroup(
            pnlHoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHoTenLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblVaiTro.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblVaiTro.setText("Vai trò:");

        bgrVaiTro.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        rdoNhanVien.setSelected(true);
        rdoNhanVien.setText("Nhân viên");

        bgrVaiTro.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        rdoQuanLy.setText("Quản lý");

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMK)
                    .addComponent(lblTK)
                    .addComponent(lblHoTen)
                    .addComponent(lblVaiTro))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(rdoNhanVien)
                        .addGap(18, 18, 18)
                        .addComponent(rdoQuanLy))
                    .addComponent(pnlTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHoTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVaiTro)
                    .addComponent(rdoNhanVien)
                    .addComponent(rdoQuanLy))
                .addGap(300, 300, 300))
        );

        btnThem.setBackground(new java.awt.Color(0, 80, 143));
        btnThem.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("THÊM");
        btnThem.setBorderPainted(false);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 80, 143));
        btnXoa.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("XÓA");
        btnXoa.setBorderPainted(false);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(0, 80, 143));
        btnCapNhat.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("CẬP NHẬT");
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(0, 80, 143));
        btnMoi.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("MỚI");
        btnMoi.setBorderPainted(false);
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        tblNguoiDung.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "TÀI KHOẢN", "MẬT KHẨU", "HỌ VÀ TÊN", "VAI TRÒ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiDung.setPreferredSize(new java.awt.Dimension(384, 125));
        tblNguoiDung.setRowHeight(30);
        tblNguoiDung.getTableHeader().setReorderingAllowed(false);
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiDungMouseClicked(evt);
            }
        });
        jspNguoiDung.setViewportView(tblNguoiDung);

        javax.swing.GroupLayout pblBanLayout = new javax.swing.GroupLayout(pblBan);
        pblBan.setLayout(pblBanLayout);
        pblBanLayout.setHorizontalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pblBanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pblBanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jspNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pblBanLayout.setVerticalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addComponent(lblExit)
                .addGap(18, 37, Short.MAX_VALUE)
                .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jspNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pblBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pblBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtTK)
                && XValidate.checkNullPass(txtMK)
                && XValidate.checkNullText(txtHoTen)) {
            if (XValidate.checkMa(txtTK)
                    && XValidate.checkPass(txtMK)
                    && XValidate.checkName(txtHoTen)) {
                this.insert();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblNguoiDung.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.showName();
    }//GEN-LAST:event_formWindowOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrVaiTro;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JScrollPane jspNguoiDung;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMK;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JLabel lblTK;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JPanel pblBan;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlHoTen;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlMK;
    private javax.swing.JPanel pnlSideBody;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlTK;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
