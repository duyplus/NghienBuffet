package buffet.ui.kho;

import buffet.dao.DoiTacDAO;
import buffet.dao.KhoDAO;
import buffet.dao.NguyenLieuDAO;
import buffet.dao.NhanVienDAO;
import buffet.model.DoiTac;
import buffet.model.Kho;
import buffet.model.NguyenLieu;
import buffet.model.NhanVien;
import buffet.utils.XAuth;
import buffet.utils.XDialog;
import buffet.utils.XValidate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author duyplus
 */
public class FrmNhapKhoJD extends javax.swing.JDialog {

    DoiTacDAO dtdao = new DoiTacDAO();
    NguyenLieuDAO nldao = new NguyenLieuDAO();
    KhoDAO kdao = new KhoDAO();
    NhanVienDAO nvdao = new NhanVienDAO();

    /**
     * Creates new form QLNhapKhoJD
     *
     * @param parent
     * @param modal
     */
    public FrmNhapKhoJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        fillComboBoxNV();
        fillComboBoxNL();
        fillComboBoxDT();
    }

    private void selectMaNV() {
        NhanVien model = (NhanVien) cboNhanVien.getSelectedItem();
        txtMaNV.setText(String.valueOf(model.getMaNV()));
    }

    @SuppressWarnings("unchecked")
    private void fillComboBoxNV() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNhanVien.getModel();
        model.removeAllElements();
        List<NhanVien> list = nvdao.selectAll();
        for (NhanVien nv : list) {
            model.addElement(nv);
        }
    }

    private void selectMaNL() {
        NguyenLieu model = (NguyenLieu) cboNguyenLieu.getSelectedItem();
        txtMaNL.setText(String.valueOf(model.getMaNL()));
        txtTenNL.setText(String.valueOf(model.getTenNL()));
        txtDVT.setText(model.getdVT());
    }

    @SuppressWarnings("unchecked")
    private void fillComboBoxNL() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNguyenLieu.getModel();
        model.removeAllElements();
        List<NguyenLieu> list = nldao.selectAll();
        for (NguyenLieu nl : list) {
            model.addElement(nl);
        }
    }

    @SuppressWarnings("unchecked")
    private void fillComboBoxDT() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDoiTac.getModel();
        model.removeAllElements();
        List<DoiTac> list = dtdao.selectAll();
        for (DoiTac dt : list) {
            model.addElement(dt);
        }
    }

    private void update(String manl) {
        try {
            NguyenLieu nl = nldao.selectById(manl);
            int sl = nl.getSoLuong() + Integer.parseInt(txtSoLuong.getText());
            nl.setSoLuong(sl);
            nldao.update(nl);
            XDialog.alert(this, "Thêm biên lai nhập kho mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm biên lai nhập kho mới thất bại!");
        }
    }

    private void insert() {
        NguyenLieu nlieu = getFormNguyenLieu();
        Kho kho = getFormKho();
        try {
            nldao.insert(nlieu);
            kdao.insert(kho);
            clearForm();
            XDialog.alert(this, "Thêm biên lai nhập kho mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm biên lai nhập kho mới thất bại!");
        }
    }

    private void setFormKho(Kho model) {
        txtNgayNhap.setDate(model.getNgayXN());
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
        txtTiGia.setText(String.valueOf(model.getTiGia()));
        txtTongGia.setText(String.valueOf(model.getTongGia()));
        txtMaNL.setText(model.getMaNL());
        txtMaNV.setText(model.getMaNV());
    }

    private Kho getFormKho() {
        Kho kho = new Kho();
        kho.setNgayXN(txtNgayNhap.getDate());
        kho.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        kho.setTrangThai("Nhập");
        kho.setTiGia(Integer.valueOf(txtTiGia.getText()));
        kho.setTongGia(Integer.valueOf(txtTongGia.getText()));
        kho.setMaNL(txtMaNL.getText());
        kho.setMaNV(txtMaNV.getText());
        return kho;
    }

    private void setFormNguyenLieu(NguyenLieu model) {
        txtMaNL.setText(model.getMaNL());
        txtTenNL.setText(model.getTenNL());
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
        txtDVT.setText(String.valueOf(model.getdVT()));
        txtHSD.setDate(model.getHsd());
        cboDoiTac.setSelectedIndex(0);
    }

    private NguyenLieu getFormNguyenLieu() {
        NguyenLieu nl = new NguyenLieu();
        nl.setMaNL(txtMaNL.getText());
        nl.setTenNL(txtTenNL.getText());
        nl.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        nl.setdVT(txtDVT.getText());
        nl.setHsd(txtHSD.getDate());
        nl.setMaDT(cboDoiTac.getSelectedItem().toString());
        return nl;
    }

    private void clearForm() {
        Kho kho = new Kho();
        NguyenLieu nl = new NguyenLieu();
        this.setFormKho(kho);
        this.setFormNguyenLieu(nl);
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
        pnlInfo = new javax.swing.JPanel();
        lblNL = new javax.swing.JLabel();
        cboNguyenLieu = new javax.swing.JComboBox<>();
        lblMaNV = new javax.swing.JLabel();
        pnlMaNV = new javax.swing.JPanel();
        txtMaNV = new javax.swing.JTextField();
        lblDoiTac = new javax.swing.JLabel();
        cboDoiTac = new javax.swing.JComboBox<>();
        lblMaNL = new javax.swing.JLabel();
        pnlMaNL = new javax.swing.JPanel();
        txtMaNL = new javax.swing.JTextField();
        lblTenNL = new javax.swing.JLabel();
        pnlTenNL = new javax.swing.JPanel();
        txtTenNL = new javax.swing.JTextField();
        lblNgayNhap = new javax.swing.JLabel();
        txtNgayNhap = new com.toedter.calendar.JDateChooser();
        lblHSD = new javax.swing.JLabel();
        txtHSD = new com.toedter.calendar.JDateChooser();
        lblSoLuong = new javax.swing.JLabel();
        pnlSoLuong = new javax.swing.JPanel();
        txtSoLuong = new javax.swing.JTextField();
        lblTiGia = new javax.swing.JLabel();
        pnlTiGia = new javax.swing.JPanel();
        txtTiGia = new javax.swing.JTextField();
        lblTongGia = new javax.swing.JLabel();
        txtTongGia = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblNV = new javax.swing.JLabel();
        cboNhanVien = new javax.swing.JComboBox<>();
        lblDVT = new javax.swing.JLabel();
        pnlDVT = new javax.swing.JPanel();
        txtDVT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
                .addGap(73, 73, 73))
            .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSideBodyLayout.setVerticalGroup(
            pnlSideBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideBodyLayout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
        );

        pnlFoot.setBackground(new java.awt.Color(0, 0, 0));
        pnlFoot.setPreferredSize(new java.awt.Dimension(256, 55));

        lblFoot.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        lblFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoot.setText("NHẬP KHO");

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

        pnlInfo.setBackground(new java.awt.Color(56, 182, 255));

        lblNL.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNL.setText("DS nguyên liệu:");

        cboNguyenLieu.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboNguyenLieu.setToolTipText("DS nguyên liệu");
        cboNguyenLieu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        cboNguyenLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguyenLieuActionPerformed(evt);
            }
        });

        lblMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMaNV.setText("Mã NV:");

        pnlMaNV.setBackground(new java.awt.Color(255, 255, 255));
        pnlMaNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtMaNV.setToolTipText("Mã NV");
        txtMaNV.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtMaNV.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlMaNVLayout = new javax.swing.GroupLayout(pnlMaNV);
        pnlMaNV.setLayout(pnlMaNVLayout);
        pnlMaNVLayout.setHorizontalGroup(
            pnlMaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlMaNVLayout.setVerticalGroup(
            pnlMaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblDoiTac.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblDoiTac.setText("Đối tác:");

        cboDoiTac.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboDoiTac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lblMaNL.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMaNL.setText("Mã NL:");

        pnlMaNL.setBackground(new java.awt.Color(255, 255, 255));
        pnlMaNL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtMaNL.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtMaNL.setToolTipText("Mã nguyên liệu");
        txtMaNL.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtMaNL.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlMaNLLayout = new javax.swing.GroupLayout(pnlMaNL);
        pnlMaNL.setLayout(pnlMaNLLayout);
        pnlMaNLLayout.setHorizontalGroup(
            pnlMaNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNL, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlMaNLLayout.setVerticalGroup(
            pnlMaNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNL, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblTenNL.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTenNL.setText("Tên NL:");

        pnlTenNL.setBackground(new java.awt.Color(255, 255, 255));
        pnlTenNL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtTenNL.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtTenNL.setToolTipText("Tên nguyên liệu");
        txtTenNL.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtTenNL.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlTenNLLayout = new javax.swing.GroupLayout(pnlTenNL);
        pnlTenNL.setLayout(pnlTenNLLayout);
        pnlTenNLLayout.setHorizontalGroup(
            pnlTenNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenNL, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlTenNLLayout.setVerticalGroup(
            pnlTenNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenNL, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblNgayNhap.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNgayNhap.setText("Ngày nhập:");

        txtNgayNhap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtNgayNhap.setToolTipText("Ngày nhập");
        txtNgayNhap.setDateFormatString("dd/MM/yyyy");
        txtNgayNhap.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N

        lblHSD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblHSD.setText("Hạn sử dụng:");

        txtHSD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtHSD.setToolTipText("Hạn sử dụng");
        txtHSD.setDateFormatString("dd/MM/yyyy");
        txtHSD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N

        lblSoLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblSoLuong.setText("Số lượng:");

        pnlSoLuong.setBackground(new java.awt.Color(255, 255, 255));
        pnlSoLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtSoLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtSoLuong.setToolTipText("Số lượng");
        txtSoLuong.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtSoLuong.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlSoLuongLayout = new javax.swing.GroupLayout(pnlSoLuong);
        pnlSoLuong.setLayout(pnlSoLuongLayout);
        pnlSoLuongLayout.setHorizontalGroup(
            pnlSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlSoLuongLayout.setVerticalGroup(
            pnlSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblTiGia.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTiGia.setText("Tỉ giá:");

        pnlTiGia.setBackground(new java.awt.Color(255, 255, 255));
        pnlTiGia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtTiGia.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtTiGia.setToolTipText("Tỉ giá");
        txtTiGia.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtTiGia.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txtTiGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTiGiaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlTiGiaLayout = new javax.swing.GroupLayout(pnlTiGia);
        pnlTiGia.setLayout(pnlTiGiaLayout);
        pnlTiGiaLayout.setHorizontalGroup(
            pnlTiGiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTiGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        pnlTiGiaLayout.setVerticalGroup(
            pnlTiGiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTiGia, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblTongGia.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTongGia.setText("Tổng giá:");

        txtTongGia.setEditable(false);
        txtTongGia.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtTongGia.setToolTipText("Tổng giá");
        txtTongGia.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        jSeparator1.setForeground(new java.awt.Color(255, 51, 51));

        lblNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNV.setText("Nhân viên");

        cboNhanVien.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboNhanVien.setToolTipText("DS Nhân viên");
        cboNhanVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        cboNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNhanVienActionPerformed(evt);
            }
        });

        lblDVT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblDVT.setText("Đơn vị tính:");

        pnlDVT.setBackground(new java.awt.Color(255, 255, 255));
        pnlDVT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtDVT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtDVT.setToolTipText("Số lượng");
        txtDVT.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtDVT.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txtDVT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDVTKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlDVTLayout = new javax.swing.GroupLayout(pnlDVT);
        pnlDVT.setLayout(pnlDVTLayout);
        pnlDVTLayout.setHorizontalGroup(
            pnlDVTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDVT, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlDVTLayout.setVerticalGroup(
            pnlDVTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDVT, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlInfoLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(lblMaNV)
                                .addGap(105, 105, 105)
                                .addComponent(pnlMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDoiTac)
                                    .addComponent(lblHSD)
                                    .addComponent(lblSoLuong)
                                    .addComponent(lblMaNL)
                                    .addComponent(lblTenNL)
                                    .addComponent(lblNgayNhap)
                                    .addComponent(lblNL)
                                    .addComponent(lblNV)
                                    .addComponent(lblTiGia)
                                    .addComponent(lblDVT)
                                    .addComponent(lblTongGia))
                                .addGap(28, 28, 28)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTongGia)
                                    .addComponent(pnlDVT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboNguyenLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtHSD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboDoiTac, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlMaNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlTenNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlTiGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNL, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboDoiTac)
                    .addComponent(lblDoiTac, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMaNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaNL, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTenNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenNL, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lblNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHSD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHSD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDVT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTiGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTiGia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongGia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lblTongGia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addContainerGap())
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

        javax.swing.GroupLayout pblBanLayout = new javax.swing.GroupLayout(pblBan);
        pblBan.setLayout(pblBanLayout);
        pblBanLayout.setHorizontalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pblBanLayout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(250, 250, 250)))))
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pblBanLayout.setVerticalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addComponent(lblExit)
                .addGap(17, 17, 17)
                .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        dispose();
        new FrmKhoJD(new javax.swing.JFrame(), true).setVisible(true);
    }//GEN-LAST:event_lblExitMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        NguyenLieu nl = nldao.selectById(txtMaNL.getText());
        if (XValidate.checkNullText(txtMaNL)
                && XValidate.checkNullText(txtTenNL)
                && XValidate.checkNullDate(txtNgayNhap)
                && XValidate.checkNullDate(txtHSD)
                && XValidate.checkNullText(txtTiGia)
                && XValidate.checkNullText(txtSoLuong)) {
            if (XValidate.checkMa(txtMaNL)
                    && XValidate.checkRate(txtSoLuong)) {
                if (nldao.selectById(txtMaNL.getText()) != null) {
                    this.update(txtMaNL.getText());
                } else {
                    this.insert();
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void cboNguyenLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguyenLieuActionPerformed
        // TODO add your handling code here:
        this.selectMaNL();
    }//GEN-LAST:event_cboNguyenLieuActionPerformed

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        // TODO add your handling code here:
        try {
            int soLuong = Integer.valueOf(txtSoLuong.getText());
            int tiGia = Integer.valueOf(txtTiGia.getText());
            txtTongGia.setText(String.valueOf(soLuong * tiGia));
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtTiGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiGiaKeyReleased
        // TODO add your handling code here:
        try {
            int soLuong = Integer.valueOf(txtSoLuong.getText());
            int tiGia = Integer.valueOf(txtTiGia.getText());
            txtTongGia.setText(String.valueOf(soLuong * tiGia));
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_txtTiGiaKeyReleased

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.showName();
    }//GEN-LAST:event_formWindowOpened

    private void cboNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNhanVienActionPerformed
        // TODO add your handling code here:
        this.selectMaNV();
    }//GEN-LAST:event_cboNhanVienActionPerformed

    private void txtDVTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDVTKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDVTKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cboDoiTac;
    private javax.swing.JComboBox<String> cboNguyenLieu;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDVT;
    private javax.swing.JLabel lblDoiTac;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHSD;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaNL;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNL;
    private javax.swing.JLabel lblNV;
    private javax.swing.JLabel lblNgayNhap;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JLabel lblTenNL;
    private javax.swing.JLabel lblTiGia;
    private javax.swing.JLabel lblTongGia;
    private javax.swing.JPanel pblBan;
    private javax.swing.JPanel pnlDVT;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlMaNL;
    private javax.swing.JPanel pnlMaNV;
    private javax.swing.JPanel pnlSideBody;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlSoLuong;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlTenNL;
    private javax.swing.JPanel pnlTiGia;
    private javax.swing.JTextField txtDVT;
    private com.toedter.calendar.JDateChooser txtHSD;
    private javax.swing.JTextField txtMaNL;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgayNhap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenNL;
    private javax.swing.JTextField txtTiGia;
    private javax.swing.JTextField txtTongGia;
    // End of variables declaration//GEN-END:variables
}
