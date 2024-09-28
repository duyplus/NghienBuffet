package buffet.ui;

import buffet.dao.NhanVienDAO;
import buffet.model.NhanVien;
import buffet.utils.XAuth;
import buffet.utils.XDate;
import buffet.utils.XDialog;
import buffet.utils.XHelper;
import buffet.utils.XValidate;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author duyplus
 */
public class FrmNhanVienJD extends javax.swing.JDialog {

    NhanVienDAO nvdao = new NhanVienDAO();
    int row = -1;

    /**
     * Creates new form QLNhanVienJD
     *
     * @param parent
     * @param modal
     */
    public FrmNhanVienJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.updateStatus();
        this.fillTable();
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel nv = tblNhanVien.getColumnModel();
        nv.getColumn(0).setMaxWidth(100);
        nv.getColumn(2).setMinWidth(5);
        nv.getColumn(0).setCellRenderer(render);
        nv.getColumn(2).setCellRenderer(render);

        Font font = new Font("Be Vietnam Pro", Font.PLAIN, 16);
        tblNhanVien.getTableHeader().setFont(font);
        tblNhanVien.getTableHeader().setPreferredSize(new Dimension(100, 35));
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = nvdao.selectByKeyword(txtTimKiem.getText());
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.isGioiTinh() ? "Nam" : "Nữ",
                    XDate.toString(nv.getNgaySinh()),
                    nv.getSdt(),
                    nv.getEmail(),
                    nv.getDiaChi()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu nhân viên!");
        }
    }

    private void insert() {
        NhanVien model = getForm();
        try {
            nvdao.insert(model);
            this.fillTable();
            this.clearForm();
            XDialog.alert(this, "Thêm nhân viên mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm nhân viên mới thất bại!");
        }

    }

    private void update() {
        NhanVien model = getForm();
        try {
            nvdao.update(model);
            this.fillTable();
            XDialog.alert(this, "Cập nhật nhân viên thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật nhân viên thất bại!");
        }

    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chứ năng này!");
        } else {
            if (XDialog.confirm(this, "Bạn có thực sự muốn xóa nhân viên này không?")) {
                String manv = txtMaNV.getText();
                try {
                    nvdao.delete(manv);
                    this.fillTable();
                    this.clearForm();
                    XDialog.alert(this, "Xóa nhân viên thành công!");
                } catch (Exception e) {
                    XDialog.alert(this, "Xóa nhân viên thất bại! ");
                }
            }
        }
    }

    private void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        this.row = -1;
        this.updateStatus();
    }

    private void edit() {
        String manv = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nv = nvdao.selectById(String.valueOf(manv));
        this.setForm(nv);
        this.updateStatus();
    }

    private void setForm(NhanVien model) {
        txtMaNV.setText(model.getMaNV());
        txtHoTen.setText(model.getHoTen());
        if (model.isGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtNgaySinh.setDate(model.getNgaySinh());
        txtSDT.setText(model.getSdt());
        txtEmail.setText(model.getEmail());
        txtDiaChi.setText(model.getDiaChi());
        lblAnh.setIcon(XHelper.readIcon("NoImage.png"));
//        if (model.getHinh() != null) {
//            lblAnh.setIcon(XHelper.readIcon(model.getHinh()));
//            lblAnh.setToolTipText(model.getHinh());
//        }
    }

    private NhanVien getForm() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNV.getText());
        model.setHoTen(txtHoTen.getText());
        boolean gt = true;
        if (rdoNam.isSelected()) {
            gt = true;
        } else if (rdoNu.isSelected()) {
            gt = false;
        }
        model.setGioiTinh(gt);
        model.setNgaySinh(txtNgaySinh.getDate());
        model.setSdt(txtSDT.getText());
        model.setEmail(txtEmail.getText());
        model.setDiaChi(txtDiaChi.getText());
        model.setHinh(lblAnh.getToolTipText());
        return model;
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        // Trạng thái form
        txtMaNV.setEnabled(!edit);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    private void selectImage() {
        FileFilter filter = new FileNameExtensionFilter("Image Files", "gif", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            XHelper.saveIcon(file);
            // Hiển thị hình lên form
            lblAnh.setIcon(XHelper.readIcon(file.getName()));
            lblAnh.setToolTipText(file.getName());
        }
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

        bgrGT = new javax.swing.ButtonGroup();
        fc = new javax.swing.JFileChooser();
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
        jtpNhanVien = new javax.swing.JTabbedPane();
        pnlInfo = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblMaNV = new javax.swing.JLabel();
        pnlMaNV = new javax.swing.JPanel();
        txtMaNV = new javax.swing.JTextField();
        lblHoTen = new javax.swing.JLabel();
        pnlHoTen = new javax.swing.JPanel();
        txtHoTen = new javax.swing.JTextField();
        lblGioiTinh = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        lblNgaySinh = new javax.swing.JLabel();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        lblSDT = new javax.swing.JLabel();
        pnlSDT = new javax.swing.JPanel();
        txtSDT = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        pnlEmail = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        lblDiaChi = new javax.swing.JLabel();
        pnlDiaChi = new javax.swing.JPanel();
        txtDiaChi = new javax.swing.JTextField();
        lblHinh = new javax.swing.JLabel();
        pnlHinh = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        jspDanhSach = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        lblTimKiem = new javax.swing.JLabel();
        pnlTimKiem = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ NHÂN VIÊN");
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
        lblFoot.setText("NHÂN VIÊN");

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

        jtpNhanVien.setFont(new java.awt.Font("Be Vietnam Pro", 0, 18)); // NOI18N

        pnlInfo.setBackground(new java.awt.Color(0, 204, 255));

        lblMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMaNV.setText("Mã NV:");

        pnlMaNV.setBackground(new java.awt.Color(255, 255, 255));
        pnlMaNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtMaNV.setToolTipText("Mã NV");
        txtMaNV.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtMaNV.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlMaNVLayout = new javax.swing.GroupLayout(pnlMaNV);
        pnlMaNV.setLayout(pnlMaNVLayout);
        pnlMaNVLayout.setHorizontalGroup(
            pnlMaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNV)
        );
        pnlMaNVLayout.setVerticalGroup(
            pnlMaNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNV)
        );

        lblHoTen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblHoTen.setText("Họ và tên:");

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
            .addComponent(txtHoTen)
        );
        pnlHoTenLayout.setVerticalGroup(
            pnlHoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtHoTen)
        );

        lblGioiTinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblGioiTinh.setText("Giới tính:");

        rdoNam.setBackground(new java.awt.Color(0, 204, 255));
        bgrGT.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(0, 204, 255));
        bgrGT.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        rdoNu.setSelected(true);
        rdoNu.setText("Nữ");

        lblNgaySinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNgaySinh.setText("Ngày sinh:");

        txtNgaySinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNgaySinh.setToolTipText("Ngày sinh");
        txtNgaySinh.setDateFormatString("dd/MM/yyyy");
        txtNgaySinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N

        lblSDT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblSDT.setText("SĐT:");

        pnlSDT.setBackground(new java.awt.Color(255, 255, 255));
        pnlSDT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtSDT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtSDT.setToolTipText("SĐT");
        txtSDT.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtSDT.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlSDTLayout = new javax.swing.GroupLayout(pnlSDT);
        pnlSDT.setLayout(pnlSDTLayout);
        pnlSDTLayout.setHorizontalGroup(
            pnlSDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSDT)
        );
        pnlSDTLayout.setVerticalGroup(
            pnlSDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSDT)
        );

        lblEmail.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblEmail.setText("Email:");

        pnlEmail.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtEmail.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtEmail.setToolTipText("Email");
        txtEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtEmail.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlEmailLayout = new javax.swing.GroupLayout(pnlEmail);
        pnlEmail.setLayout(pnlEmailLayout);
        pnlEmailLayout.setHorizontalGroup(
            pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEmail)
        );
        pnlEmailLayout.setVerticalGroup(
            pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEmail)
        );

        lblDiaChi.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblDiaChi.setText("Địa chỉ:");

        pnlDiaChi.setBackground(new java.awt.Color(255, 255, 255));
        pnlDiaChi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlDiaChi.setPreferredSize(new java.awt.Dimension(283, 42));

        txtDiaChi.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtDiaChi.setToolTipText("Địa chỉ");
        txtDiaChi.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtDiaChi.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlDiaChiLayout = new javax.swing.GroupLayout(pnlDiaChi);
        pnlDiaChi.setLayout(pnlDiaChiLayout);
        pnlDiaChiLayout.setHorizontalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDiaChi)
        );
        pnlDiaChiLayout.setVerticalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDiaChi)
        );

        lblHinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblHinh.setText("Hình:");

        pnlHinh.setBackground(new java.awt.Color(255, 255, 255));
        pnlHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblAnh.setBackground(new java.awt.Color(255, 255, 255));
        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setToolTipText("Hình ảnh");
        lblAnh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlHinhLayout = new javax.swing.GroupLayout(pnlHinh);
        pnlHinh.setLayout(pnlHinhLayout);
        pnlHinhLayout.setHorizontalGroup(
            pnlHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        pnlHinhLayout.setVerticalGroup(
            pnlHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
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

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHinh)
                            .addComponent(pnlHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblGioiTinh)
                                    .addComponent(lblHoTen)
                                    .addComponent(lblMaNV))
                                .addGap(38, 38, 38)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(50, 50, 50)
                                        .addComponent(rdoNu)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmail)
                                    .addComponent(lblNgaySinh)
                                    .addComponent(lblSDT))
                                .addGap(36, 36, 36)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 73, Short.MAX_VALUE))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lblDiaChi)
                        .addGap(18, 18, 18)
                        .addComponent(pnlDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblHinh))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoNu)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdoNam)
                                .addComponent(lblGioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(pnlHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(107, 107, 107)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jtpNhanVien.addTab("THÔNG TIN", new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-info.png")), pnlInfo); // NOI18N

        pnlList.setBackground(new java.awt.Color(0, 204, 255));

        tblNhanVien.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NV", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "SĐT", "EMAIL", "ĐỊA CHỈ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(30);
        tblNhanVien.getTableHeader().setReorderingAllowed(false);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jspDanhSach.setViewportView(tblNhanVien);

        lblTimKiem.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTimKiem.setText("TÌM KIẾM:");

        pnlTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTimKiem.setFont(new java.awt.Font("Be Vietnam Pro", 0, 18)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtTimKiem.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlTimKiemLayout = new javax.swing.GroupLayout(pnlTimKiem);
        pnlTimKiem.setLayout(pnlTimKiemLayout);
        pnlTimKiemLayout.setHorizontalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
        );
        pnlTimKiemLayout.setVerticalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiem)
        );

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(lblTimKiem)
                .addGap(18, 18, 18)
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspDanhSach))
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jspDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jtpNhanVien.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-bulleted-list.png")), pnlList); // NOI18N

        javax.swing.GroupLayout pblBanLayout = new javax.swing.GroupLayout(pblBan);
        pblBan.setLayout(pblBanLayout);
        pblBanLayout.setHorizontalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addComponent(jtpNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)))
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pblBanLayout.setVerticalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addComponent(lblExit)
                .addGap(2, 2, 2)
                .addComponent(jtpNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
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

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        this.selectImage();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaNV)
                && XValidate.checkNullText(txtHoTen)
                && XValidate.checkNullText(txtSDT)
                && XValidate.checkNullDate(txtNgaySinh)
                && XValidate.checkNullText(txtEmail)
                && XValidate.checkNullText(txtDiaChi)
                && XValidate.checkNullHinh(lblAnh)) {
            if (XValidate.checkMa(txtMaNV)
                    && XValidate.checkName(txtHoTen)
                    && XValidate.checkSDT(txtSDT)
                    && XValidate.checkEmail(txtEmail)) {
                if (XValidate.checkTrungMaNV(txtMaNV)) {
                    this.insert();
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtHoTen)
                && XValidate.checkNullText(txtSDT)
                && XValidate.checkNullDate(txtNgaySinh)
                && XValidate.checkEmail(txtEmail)
                && XValidate.checkNullText(txtDiaChi)
                && XValidate.checkNullHinh(lblAnh)) {
            if (XValidate.checkName(txtHoTen)
                    && XValidate.checkSDT(txtSDT)
                    && XValidate.checkEmail(txtEmail)) {
                this.update();
            }
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblNhanVien.getSelectedRow();
            this.edit();
            jtpNhanVien.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.showName();
        lblAnh.setIcon(XHelper.readIcon("NoImage.png"));
    }//GEN-LAST:event_formWindowOpened

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        this.fillTable();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrGT;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JFileChooser fc;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JScrollPane jspDanhSach;
    private javax.swing.JTabbedPane jtpNhanVien;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pblBan;
    private javax.swing.JPanel pnlDiaChi;
    private javax.swing.JPanel pnlEmail;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlHinh;
    private javax.swing.JPanel pnlHoTen;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlMaNV;
    private javax.swing.JPanel pnlSDT;
    private javax.swing.JPanel pnlSideBody;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
