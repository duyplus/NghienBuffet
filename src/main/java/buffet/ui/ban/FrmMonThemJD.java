package buffet.ui.ban;

import buffet.dao.MonThemDAO;
import buffet.model.MonThem;
import buffet.utils.XAuth;
import buffet.utils.XDialog;
import buffet.utils.XHelper;
import buffet.utils.XValidate;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author duyplus
 */
public class FrmMonThemJD extends javax.swing.JDialog {

    MonThemDAO mtdao = new MonThemDAO();
    int row = -1;

    /**
     * Creates new form QLMonThemJD
     *
     * @param parent
     * @param modal
     */
    public FrmMonThemJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.fillTable();
        this.updateStatus();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel kh = tblMonThem.getColumnModel();
        kh.getColumn(0).setCellRenderer(render);
        kh.getColumn(1).setCellRenderer(render);
        kh.getColumn(2).setCellRenderer(render);

        Font font = new Font("Be Vietnam Pro", Font.PLAIN, 16);
        tblMonThem.getTableHeader().setFont(font);
        tblMonThem.getTableHeader().setPreferredSize(new Dimension(100, 35));
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblMonThem.getModel();
        model.setRowCount(0);
        try {
            List<MonThem> list = mtdao.selectAll();
            for (MonThem mt : list) {
                Object[] row = {
                    mt.getMaMT(),
                    mt.getTenMT(),
                    XHelper.commafy(String.valueOf(mt.getGia()))
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu món!");
            e.printStackTrace();
        }
    }

    private void insert() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chứ năng này!");
        } else {
            MonThem model = this.getForm();
            try {
                mtdao.insert(model); // thêm mới
                this.fillTable(); // đỗ lại bảng
                this.clearForm(); // xóa trắng form
                XDialog.alert(this, "Thêm món mới thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Thêm món mới thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chứ năng này!");
        } else {
            MonThem model = this.getForm();
            try {
                mtdao.update(model); // cập nhật
                this.fillTable(); // đổ lại bảng
                XDialog.alert(this, "Cập nhật món thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Cập nhật món thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ quản lý mới được phép dùng chứ năng này!");
        } else {
            if (XDialog.confirm(this, "Bạn có thực sự muốn xóa món này không?")) {
                String mamt = txtMaMT.getText();
                try {
                    mtdao.delete(String.valueOf(mamt));
                    this.fillTable();
                    this.clearForm();
                    XDialog.alert(this, "Xóa món thành công!");
                } catch (Exception e) {
                    XDialog.alert(this, "Xóa món thất bại!");
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearForm() {
        MonThem model = new MonThem();
        this.setForm(model);
        this.row = -1;
        this.updateStatus();
    }

    private void edit() {
        String mamt = (String) tblMonThem.getValueAt(this.row, 0);
        MonThem model = mtdao.selectById(mamt);
        this.setForm(model);
        this.updateStatus();
    }

    private void setForm(MonThem model) {
        txtMaMT.setText(model.getMaMT());
        txtTenMT.setText(model.getTenMT());
        txtGia.setText(String.valueOf(model.getGia()));
    }

    private MonThem getForm() {
        MonThem model = new MonThem();
        model.setMaMT(txtMaMT.getText());
        model.setTenMT(txtTenMT.getText());
        model.setGia(Integer.valueOf(txtGia.getText()));
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
        lblMaMT = new javax.swing.JLabel();
        pnlMaMT = new javax.swing.JPanel();
        txtMaMT = new javax.swing.JTextField();
        lblTenMT = new javax.swing.JLabel();
        pnlTenMT = new javax.swing.JPanel();
        txtTenMT = new javax.swing.JTextField();
        lblGia = new javax.swing.JLabel();
        pnlGia = new javax.swing.JPanel();
        txtGia = new javax.swing.JTextField();
        jspCa = new javax.swing.JScrollPane();
        tblMonThem = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();

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
                .addContainerGap(118, Short.MAX_VALUE))
        );

        pnlFoot.setBackground(new java.awt.Color(0, 0, 0));
        pnlFoot.setPreferredSize(new java.awt.Dimension(256, 55));

        lblFoot.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        lblFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoot.setText("MÓN THÊM");

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

        lblMaMT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMaMT.setText("Mã món:");

        pnlMaMT.setBackground(new java.awt.Color(255, 255, 255));
        pnlMaMT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtMaMT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtMaMT.setToolTipText("Mã món");
        txtMaMT.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtMaMT.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlMaMTLayout = new javax.swing.GroupLayout(pnlMaMT);
        pnlMaMT.setLayout(pnlMaMTLayout);
        pnlMaMTLayout.setHorizontalGroup(
            pnlMaMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaMT, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        pnlMaMTLayout.setVerticalGroup(
            pnlMaMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaMT, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        lblTenMT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTenMT.setText("Tên món:");

        pnlTenMT.setBackground(new java.awt.Color(255, 255, 255));
        pnlTenMT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTenMT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtTenMT.setToolTipText("Tên món");
        txtTenMT.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtTenMT.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlTenMTLayout = new javax.swing.GroupLayout(pnlTenMT);
        pnlTenMT.setLayout(pnlTenMTLayout);
        pnlTenMTLayout.setHorizontalGroup(
            pnlTenMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenMT, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlTenMTLayout.setVerticalGroup(
            pnlTenMTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenMT)
        );

        lblGia.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblGia.setText("Giá:");

        pnlGia.setBackground(new java.awt.Color(255, 255, 255));
        pnlGia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGia.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtGia.setToolTipText("Giá món");
        txtGia.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtGia.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlGiaLayout = new javax.swing.GroupLayout(pnlGia);
        pnlGia.setLayout(pnlGiaLayout);
        pnlGiaLayout.setHorizontalGroup(
            pnlGiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        pnlGiaLayout.setVerticalGroup(
            pnlGiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        tblMonThem.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        tblMonThem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "MÃ MÓN", "TÊN MÓN", "GIÁ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMonThem.setRowHeight(30);
        tblMonThem.getTableHeader().setReorderingAllowed(false);
        tblMonThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMonThemMouseClicked(evt);
            }
        });
        jspCa.setViewportView(tblMonThem);

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

        javax.swing.GroupLayout pblBanLayout = new javax.swing.GroupLayout(pblBan);
        pblBan.setLayout(pblBanLayout);
        pblBanLayout.setHorizontalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pblBanLayout.createSequentialGroup()
                                .addGap(169, 169, 169)
                                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenMT)
                                    .addComponent(lblMaMT)
                                    .addComponent(lblGia))
                                .addGap(41, 41, 41)
                                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlTenMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlMaMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pblBanLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pblBanLayout.createSequentialGroup()
                        .addContainerGap(12, Short.MAX_VALUE)
                        .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pblBanLayout.createSequentialGroup()
                                .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pblBanLayout.createSequentialGroup()
                                .addComponent(jspCa, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)))))
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pblBanLayout.setVerticalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addComponent(lblExit)
                .addGap(51, 51, 51)
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlMaMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaMT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTenMT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenMT, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGia, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addComponent(jspCa, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
            .addGroup(pblBanLayout.createSequentialGroup()
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(pblBan, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaMT)
                && XValidate.checkNullText(txtTenMT)
                && XValidate.checkNullText(txtGia)) {
            if (XValidate.checkName(txtTenMT)) {
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
        if (XValidate.checkNullText(txtTenMT)
                && XValidate.checkNullText(txtGia)) {
            if (XValidate.checkMa(txtMaMT)
                    && XValidate.checkName(txtGia)) {
                this.update();
            }
        }
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseClicked
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnMoiMouseClicked

    private void tblMonThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMonThemMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblMonThem.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblMonThemMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.showName();
    }//GEN-LAST:event_formWindowOpened

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JScrollPane jspCa;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaMT;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JLabel lblTenMT;
    private javax.swing.JPanel pblBan;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlGia;
    private javax.swing.JPanel pnlMaMT;
    private javax.swing.JPanel pnlSideBody;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlTenMT;
    private javax.swing.JTable tblMonThem;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaMT;
    private javax.swing.JTextField txtTenMT;
    // End of variables declaration//GEN-END:variables
}
