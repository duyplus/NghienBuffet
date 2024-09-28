package buffet.ui.ban;

import buffet.dao.BanDAO;
import buffet.dao.KhachHangDAO;
import buffet.dao.MonDaThemDAO;
import buffet.dao.MonThemDAO;
import buffet.dao.NhanVienDAO;
import buffet.model.Ban;
import buffet.model.HoaDon;
import buffet.model.KhachHang;
import buffet.model.MonDaThem;
import buffet.model.MonThem;
import buffet.model.NhanVien;
import static buffet.ui.ban.FrmBanJD.NutBan;
import buffet.utils.XAuth;
import buffet.utils.XDate;
import buffet.utils.XDialog;
import buffet.utils.XHelper;
import buffet.utils.XValidate;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author duyplus
 */
public class FrmBanCTJD extends javax.swing.JFrame {
    
    MonThemDAO mtdao = new MonThemDAO();
    MonDaThemDAO mcdao = new MonDaThemDAO();
    KhachHangDAO khdao = new KhachHangDAO();
    BanDAO bdao = new BanDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    int count = -1;
    boolean c1 = true;

    /**
     * Creates new form MainUI
     */
    public FrmBanCTJD() {
        initComponents();
        init();
        updateStatus();
    }
    
    private void init() {
        setLocationRelativeTo(null);
        String so = FrmBanJD.NutBan;
        lblSoBan.setText(so);
        setForm();
        fillComboBoxNV();
        fillTableMonThem();
        fillTableMonDaThem();
        checkHuyBan();
        
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        
        TableColumnModel mt = tblMT.getColumnModel();
        mt.getColumn(0).setCellRenderer(render);
        mt.getColumn(1).setCellRenderer(render);
        
        TableColumnModel mdt = tblMDT.getColumnModel();
        mdt.getColumn(0).setCellRenderer(render);
        mdt.getColumn(1).setCellRenderer(render);
        mdt.getColumn(2).setCellRenderer(render);
        mdt.getColumn(3).setCellRenderer(render);
        
        Font font = new Font("Be Vietnam Pro", Font.PLAIN, 16);
        tblMT.getTableHeader().setFont(font);
        tblMT.getTableHeader().setPreferredSize(new Dimension(100, 35));
        tblMDT.getTableHeader().setFont(font);
        tblMDT.getTableHeader().setPreferredSize(new Dimension(100, 35));
    }
    
    private void fillTableMonDaThem() {
        DefaultTableModel model = (DefaultTableModel) tblMDT.getModel();
        model.setRowCount(0);
        Ban lists = bdao.selectByMaBan(lblSoBan.getText());
        if (lists != null) {
            List<MonDaThem> list = mcdao.selectByMonThem(lists.getSoBan());
            for (int i = 0; i < list.size(); i++) {
                MonDaThem mdt = list.get(i);
                String tenMon = mtdao.selectById(mdt.getMaMT()).getTenMT();
                int gia = mtdao.selectById(mdt.getMaMT()).getGia();
                model.addRow(new Object[]{
                    mdt.getMaMT(),
                    tenMon,
                    mdt.getSoLuong(),
                    XHelper.commafy(String.valueOf(gia))
                }
                );
            }
            fillTableMonThem();
        }
    }
    
    private void fillTableMonThem() {
        DefaultTableModel model = (DefaultTableModel) tblMT.getModel();
        model.setRowCount(0);
        List<MonThem> list = mtdao.selectAll();
        for (MonThem mt : list) {
            model.addRow(new Object[]{
                mt.getMaMT(),
                mt.getTenMT()
            });
        }
    }
    
    private void insertMon() {
        Ban ban = bdao.selectByMaBan(lblSoBan.getText());
        int[] rows = tblMT.getSelectedRows();
        int rowmdt = tblMDT.getRowCount();
        
        for (int row : rows) {
            for (int i = 0; i < rowmdt; i++) {
                String rowsmdt = (String) tblMDT.getValueAt(i, 0);
                String mamt = (String) tblMT.getValueAt(row, 0);
                if (mamt.equals(rowsmdt)) {
                    XDialog.alert(this, "Bạn đã thêm món này rồi");
                    return;
                }
            }
            String mamt = (String) tblMT.getValueAt(row, 0);
            MonDaThem mdt = new MonDaThem();
            mdt.setMaBan(ban.getSoBan());
            mdt.setMaMT(mamt);
            mdt.setSoLuong(0);
            mcdao.insert(mdt);
            tblMDT.setRowSelectionAllowed(false);
        }
        fillTableMonDaThem();
        tabs.setSelectedIndex(0);
        
    }
    
    private void removeMon() {
        int[] rows = tblMDT.getSelectedRows();
        if (rows.length > 0 && XDialog.confirm(this, "Bạn muốn xóa các món được chọn?")) {
            for (int row : rows) {
                String mamt = (String) tblMDT.getValueAt(row, 0);
                mcdao.delete(mamt);
            }
            fillTableMonDaThem();
        }
    }
    
    private void insertBan() {
        Ban ban = getBan();
        try {
            bdao.insert(ban);
            this.setForm();
            XDialog.alert(this, "Thêm khách hàng vào bàn thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm khách hàng vào bàn thất bại!");
        }
        fillTableMonThem();
        fillTableMonDaThem();
    }
    
    private Ban getBan() {
        Ban ban = new Ban();
        ban.setMaBan(lblSoBan.getText());
        ban.setMaNV(txtMaNV.getText());
        ban.setSDT(txtSDT.getText());
        ban.setSoBan(lblSoBan.getText());
        ban.setGioDat(Time.valueOf(txtThoiGian.getText()));
        ban.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        ban.setNguoiLon(Integer.parseInt(txtNguoiLon.getText()));
        ban.setTreEm(Integer.parseInt(txtTreEm.getText()));
        return ban;
    }
    
    private void setForm() {
        Ban lists = bdao.selectByMaBan(lblSoBan.getText());
        if (lists != null) {
            count = 0;
            List<KhachHang> list = khdao.selectByKhachHang(lists.getSDT());
            cboNhanVen.setToolTipText(String.valueOf(lists.getMaNV()));
            for (KhachHang kh : list) {
                txtSDT.setText(kh.getSdt());
                txtTenChuBan.setText(kh.getTenKH());
                txtEmail.setText(kh.getEmail());
            }
            txtThoiGian.setText(String.valueOf(lists.getGioDat()));
            txtNguoiLon.setText(String.valueOf(lists.getNguoiLon()));
            txtTreEm.setText(String.valueOf(lists.getTreEm()));
            txtSoLuong.setText(String.valueOf(lists.getNguoiLon() + lists.getTreEm()));
        } else {
            count = -1;
        }
    }
    
    private KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setSdt(txtSDT.getText());
        kh.setTenKH(txtTenChuBan.getText());
        kh.setEmail(txtEmail.getText());
        return kh;
    }
    
    private void insertKH() {
        KhachHang kh = getForm();
        try {
            khdao.insert(kh);
            setForm();
            //XDialog.alert(this, "Thêm bàn mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm bàn mới thất bại!");
        }
        insertBan();
    }
    
    private void updateKH() {
        KhachHang kh = getForm();
        Ban ban = getBan();
        try {
            khdao.update(kh);
            bdao.update(ban);
            setForm();
            XDialog.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật thất bại!");
        }
        updateSoLuong();
    }
    
    private void updateSoLuong() {
        int n = tblMDT.getRowCount();
        for (int i = 0; i < n; i++) {
            String maBan = lblSoBan.getText();
            String mamdt = (String) tblMDT.getValueAt(i, 0);
            int soLuong = (Integer) tblMDT.getValueAt(i, 2);
            if (soLuong < 0 || soLuong > 21) {
                XDialog.alert(this, "Vui lòng nhập số lượng từ 0-20!");
                return;
            }
            MonDaThem mdt = mcdao.selectById(maBan);
            mdt.setMaMT(mamdt);
            mdt.setSoLuong(soLuong);
            mcdao.update(mdt);
        }
    }
    
    private void thanhToan() {
        try {
            HoaDon hd = new HoaDon();
            hd.setSoBan(lblSoBan.getText());
            hd.setMaNV(txtMaNV.getText());
            hd.setSDT(txtSDT.getText());
            hd.setTenKH(txtTenChuBan.getText());
            LocalTime t = LocalTime.parse(txtThoiGian.getText());
            hd.setGioVao(Time.valueOf(t));
            hd.setNgayXuat(XDate.now());
            hd.setNguoiLon(Integer.parseInt(txtNguoiLon.getText()));
            hd.setTreEm(Integer.parseInt(txtTreEm.getText()));
            TableModel model = tblMDT.getModel();
            dispose();
            new FrmHoaDonJD(new javax.swing.JFrame(), true, hd, model).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private void fillComboBoxNV() {
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
    }
    
    private void updateStatus() {
        if (count == 0) {
            btnThanhToan.setEnabled(true);
            btnTimKiem.setEnabled(false);
        } else {
            btnThanhToan.setEnabled(false);
            btnTimKiem.setEnabled(true);
        }
    }
    
    private void checkInsert(boolean c1) {
        if (c1 == false) {
            insertBan();
        } else {
            insertKH();
        }
    }
    
    private void findKH() {
        String s = XDialog.prompt(this, "Nhập số điện thoại muốn tìm?");
        KhachHang kh = khdao.selectById(s);
        try {
            if (kh != null) {
                if (s.equals(kh.getSdt())) {
                    XDialog.alert(this, "Khách hàng này đã từng đến nhà hàng");
                    c1 = false;
                    txtSDT.setText(kh.getSdt());
                    txtTenChuBan.setText(kh.getTenKH());
                    txtEmail.setText(kh.getEmail());
                }
            } else {
                XDialog.alert(this, "Khách hàng này chưa từng đến nhà hàng!");
                c1 = true;
                txtSDT.setText(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void checkHuyBan() {
        Thread t = new Thread(new Runnable() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = null;
            Date d1 = null;
            Date d2 = null;
            
            @Override
            public void run() {
                while (true) {
                    try {
                        time = sdf.format(XDate.now());
                        Thread.sleep(6000);
                        
                        Ban lists = bdao.selectByMaBan(lblSoBan.getText());
                        String dateStart = String.valueOf(lists.getGioDat());
                        String dateStop = time;
                        
                        d1 = sdf.parse(dateStart);
                        d2 = sdf.parse(dateStop);
                        
                        long diff = d2.getTime() - d1.getTime();
                        long diffMinutes = diff / (60 * 1000);
                        if (diffMinutes >= 0 && diffMinutes <= 10) {
                            btnHuyBan.setEnabled(true);
                        } else {
                            btnHuyBan.setEnabled(false);
                        }
                    } catch (Exception e) {
                        return;
                    }
                }
            }
        }
        );
        t.start();
    }
    
    private void huyBan() {
        if (XDialog.confirm(this, "Bạn có thực sự muốn hủy bàn này không?")) {
            try {
                bdao.delete(lblSoBan.getText());
                this.clearForm();
                XDialog.alert(this, "Hủy bàn thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Hủy bàn thất bại!");
                e.printStackTrace();
            }
        }
        this.dispose();
        new FrmBanCTJD().setVisible(true);
    }
    
    private void clearForm() {
        Ban ban = new Ban();
        KhachHang kh = new KhachHang();
        txtSDT.setText(ban.getSDT());
        txtTenChuBan.setText(kh.getTenKH());
        txtEmail.setText(kh.getEmail());
        txtThoiGian.setText("");
        txtSoLuong.setText(String.valueOf(ban.getSoLuong()));
        txtNguoiLon.setText(String.valueOf(ban.getNguoiLon()));
        txtTreEm.setText(String.valueOf(ban.getTreEm()));
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

        pnlMain = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlSideBody = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        pnlStaff = new javax.swing.JPanel();
        lblStaff = new javax.swing.JLabel();
        pnlFoot = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        pnlSoBan = new javax.swing.JPanel();
        lblSoBan = new javax.swing.JLabel();
        lblNV = new javax.swing.JLabel();
        cboNhanVen = new javax.swing.JComboBox<>();
        lblMaNV = new javax.swing.JLabel();
        pnlMaNV = new javax.swing.JPanel();
        txtMaNV = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        pnlSDT = new javax.swing.JPanel();
        txtSDT = new javax.swing.JTextField();
        lblTenChuBan = new javax.swing.JLabel();
        pnlTenChuBan = new javax.swing.JPanel();
        txtTenChuBan = new javax.swing.JTextField();
        lblEMail = new javax.swing.JLabel();
        pnlEmail = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        lblThoiGian = new javax.swing.JLabel();
        pnlThoiGian = new javax.swing.JPanel();
        txtThoiGian = new javax.swing.JTextField();
        lblSoLuong = new javax.swing.JLabel();
        pnlSoLuong = new javax.swing.JPanel();
        txtSoLuong = new javax.swing.JTextField();
        lblNguoiLon = new javax.swing.JLabel();
        pnlNguoiLon = new javax.swing.JPanel();
        txtNguoiLon = new javax.swing.JTextField();
        lblTreEm = new javax.swing.JLabel();
        pnlTreEm = new javax.swing.JPanel();
        txtTreEm = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        pnlMonDaChon = new javax.swing.JPanel();
        jspMonDaChon = new javax.swing.JScrollPane();
        tblMDT = new javax.swing.JTable();
        btnBoMon = new javax.swing.JButton();
        pnlChonMon = new javax.swing.JPanel();
        jspChonMon = new javax.swing.JScrollPane();
        tblMT = new javax.swing.JTable();
        btnChonMon = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblExit = new javax.swing.JLabel();
        btnHuyBan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ CHI TIẾT BÀN");
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(54, 180, 252));
        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlMain.setPreferredSize(new java.awt.Dimension(1028, 600));

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
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pnlFoot.setBackground(new java.awt.Color(0, 0, 0));
        pnlFoot.setPreferredSize(new java.awt.Dimension(256, 55));

        lblFoot.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        lblFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoot.setText("CHI TIẾT BÀN");

        javax.swing.GroupLayout pnlFootLayout = new javax.swing.GroupLayout(pnlFoot);
        pnlFoot.setLayout(pnlFootLayout);
        pnlFootLayout.setHorizontalGroup(
            pnlFootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFootLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFoot)
                .addGap(93, 93, 93))
        );
        pnlFootLayout.setVerticalGroup(
            pnlFootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFoot, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlFoot, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSideBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(pnlFoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlSoBan.setBackground(new java.awt.Color(0, 0, 0));

        lblSoBan.setFont(new java.awt.Font("Be Vietnam Pro", 1, 36)); // NOI18N
        lblSoBan.setForeground(new java.awt.Color(255, 255, 255));
        lblSoBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlSoBanLayout = new javax.swing.GroupLayout(pnlSoBan);
        pnlSoBan.setLayout(pnlSoBanLayout);
        pnlSoBanLayout.setHorizontalGroup(
            pnlSoBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSoBan, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );
        pnlSoBanLayout.setVerticalGroup(
            pnlSoBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSoBan, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        lblNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNV.setText("DS nhân viên:");

        cboNhanVen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        cboNhanVen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboNhanVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNhanVenActionPerformed(evt);
            }
        });

        lblMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMaNV.setText("Mã NV:");

        pnlMaNV.setBackground(new java.awt.Color(255, 255, 255));
        pnlMaNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlMaNV.setPreferredSize(new java.awt.Dimension(222, 40));

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

        lblSDT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblSDT.setText("Số điện thoại:");

        pnlSDT.setBackground(new java.awt.Color(255, 255, 255));
        pnlSDT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlSDT.setPreferredSize(new java.awt.Dimension(222, 40));

        txtSDT.setEditable(false);
        txtSDT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtSDT.setToolTipText("Số điện thoại");
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

        lblTenChuBan.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTenChuBan.setText("Tên chủ bàn:");

        pnlTenChuBan.setBackground(new java.awt.Color(255, 255, 255));
        pnlTenChuBan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlTenChuBan.setPreferredSize(new java.awt.Dimension(222, 40));

        txtTenChuBan.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtTenChuBan.setToolTipText("Tên chủ bàn");
        txtTenChuBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtTenChuBan.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlTenChuBanLayout = new javax.swing.GroupLayout(pnlTenChuBan);
        pnlTenChuBan.setLayout(pnlTenChuBanLayout);
        pnlTenChuBanLayout.setHorizontalGroup(
            pnlTenChuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenChuBan)
        );
        pnlTenChuBanLayout.setVerticalGroup(
            pnlTenChuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenChuBan)
        );

        lblEMail.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblEMail.setText("Email:");

        pnlEmail.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlEmail.setPreferredSize(new java.awt.Dimension(222, 40));

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

        lblThoiGian.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblThoiGian.setText("Đặt giờ lúc:");

        pnlThoiGian.setBackground(new java.awt.Color(255, 255, 255));
        pnlThoiGian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlThoiGian.setPreferredSize(new java.awt.Dimension(222, 40));

        txtThoiGian.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtThoiGian.setToolTipText("Thời gian");
        txtThoiGian.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtThoiGian.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txtThoiGian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtThoiGianMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlThoiGianLayout = new javax.swing.GroupLayout(pnlThoiGian);
        pnlThoiGian.setLayout(pnlThoiGianLayout);
        pnlThoiGianLayout.setHorizontalGroup(
            pnlThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtThoiGian)
        );
        pnlThoiGianLayout.setVerticalGroup(
            pnlThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtThoiGian)
        );

        lblSoLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblSoLuong.setText("Số lượng:");

        pnlSoLuong.setBackground(new java.awt.Color(255, 255, 255));
        pnlSoLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlSoLuong.setPreferredSize(new java.awt.Dimension(222, 40));

        txtSoLuong.setEditable(false);
        txtSoLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtSoLuong.setText("0");
        txtSoLuong.setToolTipText("Số lượng người");
        txtSoLuong.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtSoLuong.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout pnlSoLuongLayout = new javax.swing.GroupLayout(pnlSoLuong);
        pnlSoLuong.setLayout(pnlSoLuongLayout);
        pnlSoLuongLayout.setHorizontalGroup(
            pnlSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );
        pnlSoLuongLayout.setVerticalGroup(
            pnlSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        lblNguoiLon.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNguoiLon.setText("Người lớn:");

        pnlNguoiLon.setBackground(new java.awt.Color(255, 255, 255));
        pnlNguoiLon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlNguoiLon.setPreferredSize(new java.awt.Dimension(222, 40));

        txtNguoiLon.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtNguoiLon.setToolTipText("Số lượng người lớn");
        txtNguoiLon.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtNguoiLon.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txtNguoiLon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNguoiLonKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlNguoiLonLayout = new javax.swing.GroupLayout(pnlNguoiLon);
        pnlNguoiLon.setLayout(pnlNguoiLonLayout);
        pnlNguoiLonLayout.setHorizontalGroup(
            pnlNguoiLonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNguoiLon, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        pnlNguoiLonLayout.setVerticalGroup(
            pnlNguoiLonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNguoiLon, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        lblTreEm.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTreEm.setText("Trẻ em:");

        pnlTreEm.setBackground(new java.awt.Color(255, 255, 255));
        pnlTreEm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlTreEm.setPreferredSize(new java.awt.Dimension(222, 40));

        txtTreEm.setFont(new java.awt.Font("Be Vietnam Pro", 0, 17)); // NOI18N
        txtTreEm.setToolTipText("Số lượng trẻ em");
        txtTreEm.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        txtTreEm.setMargin(new java.awt.Insets(2, 10, 2, 10));
        txtTreEm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTreEmKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlTreEmLayout = new javax.swing.GroupLayout(pnlTreEm);
        pnlTreEm.setLayout(pnlTreEmLayout);
        pnlTreEmLayout.setHorizontalGroup(
            pnlTreEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTreEm, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        pnlTreEmLayout.setVerticalGroup(
            pnlTreEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTreEm, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        btnTimKiem.setBackground(new java.awt.Color(0, 80, 143));
        btnTimKiem.setFont(new java.awt.Font("Be Vietnam Pro", 1, 22)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(0, 80, 143));
        btnCapNhat.setFont(new java.awt.Font("Be Vietnam Pro", 1, 22)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("CẬP NHẬT");
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 80, 143));
        btnThanhToan.setFont(new java.awt.Font("Be Vietnam Pro", 1, 22)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        tabs.setFont(new java.awt.Font("Be Vietnam Pro", 0, 18)); // NOI18N

        pnlMonDaChon.setBackground(new java.awt.Color(0, 204, 255));

        tblMDT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        tblMDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MÃ MÓN", "MÓN ĐÃ CHỌN", "SỐ LƯỢNG", "GIÁ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMDT.setPreferredSize(new java.awt.Dimension(384, 125));
        tblMDT.setRowHeight(30);
        tblMDT.getTableHeader().setReorderingAllowed(false);
        jspMonDaChon.setViewportView(tblMDT);

        btnBoMon.setBackground(new java.awt.Color(0, 80, 143));
        btnBoMon.setFont(new java.awt.Font("Be Vietnam Pro", 1, 18)); // NOI18N
        btnBoMon.setForeground(new java.awt.Color(255, 255, 255));
        btnBoMon.setText("BỎ CHỌN");
        btnBoMon.setBorderPainted(false);
        btnBoMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMonDaChonLayout = new javax.swing.GroupLayout(pnlMonDaChon);
        pnlMonDaChon.setLayout(pnlMonDaChonLayout);
        pnlMonDaChonLayout.setHorizontalGroup(
            pnlMonDaChonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMonDaChon, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMonDaChonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlMonDaChonLayout.setVerticalGroup(
            pnlMonDaChonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonDaChonLayout.createSequentialGroup()
                .addComponent(jspMonDaChon, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("MÓN ĐÃ CHỌN", new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-full-shopping-basket.png")), pnlMonDaChon); // NOI18N

        pnlChonMon.setBackground(new java.awt.Color(0, 204, 255));

        tblMT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        tblMT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "MÃ MÓN", "MÓN ĂN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMT.setRowHeight(30);
        tblMT.getTableHeader().setReorderingAllowed(false);
        jspChonMon.setViewportView(tblMT);

        btnChonMon.setBackground(new java.awt.Color(0, 80, 143));
        btnChonMon.setFont(new java.awt.Font("Be Vietnam Pro", 1, 18)); // NOI18N
        btnChonMon.setForeground(new java.awt.Color(255, 255, 255));
        btnChonMon.setText("CHỌN");
        btnChonMon.setBorderPainted(false);
        btnChonMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChonMonLayout = new javax.swing.GroupLayout(pnlChonMon);
        pnlChonMon.setLayout(pnlChonMonLayout);
        pnlChonMonLayout.setHorizontalGroup(
            pnlChonMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspChonMon, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChonMonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnChonMon, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlChonMonLayout.setVerticalGroup(
            pnlChonMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChonMonLayout.createSequentialGroup()
                .addComponent(jspChonMon, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChonMon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("CHỌN MÓN", new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-add-basket.png")), pnlChonMon); // NOI18N

        lblExit.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setText("x");
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        btnHuyBan.setBackground(new java.awt.Color(0, 80, 143));
        btnHuyBan.setFont(new java.awt.Font("Be Vietnam Pro", 1, 22)); // NOI18N
        btnHuyBan.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyBan.setText("HUỶ BÀN");
        btnHuyBan.setBorderPainted(false);
        btnHuyBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tabs)
                            .addComponent(jSeparator1)))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEMail)
                            .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTenChuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNV)
                            .addComponent(lblThoiGian)
                            .addComponent(lblSoLuong)
                            .addComponent(lblMaNV))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(pnlSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTreEm)
                                    .addComponent(lblNguoiLon))
                                .addGap(18, 18, 18)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlNguoiLon, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlTreEm, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(pnlThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(pnlEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(pnlTenChuBan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(pnlSDT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(pnlMaNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(cboNhanVen, javax.swing.GroupLayout.Alignment.LEADING, 0, 300, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                        .addComponent(btnCapNhat, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                        .addComponent(btnHuyBan, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                                .addGap(25, 25, 25))
                            .addComponent(lblExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlMainLayout.createSequentialGroup()
                                        .addComponent(lblExit)
                                        .addGap(41, 41, 41)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(pnlSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnHuyBan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNV)
                                    .addComponent(cboNhanVen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pnlMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pnlSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pnlTenChuBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTenChuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pnlEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblEMail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pnlThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlNguoiLon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNguoiLon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlTreEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTreEm, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.showName();
    }//GEN-LAST:event_formWindowOpened

    private void txtTreEmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTreEmKeyReleased
        // TODO add your handling code here:
        try {
            int so1 = Integer.parseInt(txtNguoiLon.getText());
            int so2 = Integer.parseInt(txtTreEm.getText());
            txtSoLuong.setText(String.valueOf(so2 + so1));
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_txtTreEmKeyReleased

    private void cboNhanVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNhanVenActionPerformed
        // TODO add your handling code here:
        this.selectMaNV();
    }//GEN-LAST:event_cboNhanVenActionPerformed

    private void txtNguoiLonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNguoiLonKeyReleased
        // TODO add your handling code here:
        try {
            int so1 = Integer.parseInt(txtNguoiLon.getText());
            int so2 = Integer.parseInt(txtTreEm.getText());
            txtSoLuong.setText(String.valueOf(so2 + so1));
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_txtNguoiLonKeyReleased

    private void btnBoMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoMonActionPerformed
        // TODO add your handling code here:
        this.removeMon();
    }//GEN-LAST:event_btnBoMonActionPerformed

    private void btnChonMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonMonActionPerformed
        // TODO add your handling code here:
        this.insertMon();
    }//GEN-LAST:event_btnChonMonActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        this.thanhToan();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtThoiGianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtThoiGianMouseClicked
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        txtThoiGian.setText(sdf.format(date));
    }//GEN-LAST:event_txtThoiGianMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if (count == 0) {
            if (XValidate.checkNullText(txtTenChuBan)
                    && XValidate.checkNullText(txtMaNV)
                    && XValidate.checkNullText(txtEmail)
                    && XValidate.checkNullText(txtThoiGian)
                    && XValidate.checkNullText(txtSoLuong)
                    && XValidate.checkNullText(txtNguoiLon)
                    && XValidate.checkNullText(txtTreEm)) {
                if (XValidate.checkSDT(txtSDT)
                        && XValidate.checkNumber(txtSoLuong)) {
                    this.updateKH();
                }
            }
        } else {
            if (XValidate.checkNullText(txtSDT)
                    && XValidate.checkNullText(txtMaNV)
                    && XValidate.checkNullText(txtTenChuBan)
                    && XValidate.checkNullText(txtEmail)
                    && XValidate.checkNullText(txtThoiGian)
                    && XValidate.checkNullText(txtSoLuong)
                    && XValidate.checkNullText(txtNguoiLon)
                    && XValidate.checkNullText(txtTreEm)) {
                if (XValidate.checkSDT(txtSDT)
                        && XValidate.checkNumber(txtSoLuong)) {
                    this.checkInsert(c1);
                    btnThanhToan.setEnabled(true);
                    XHelper.alert(lblSoBan.getText());
                }
            }
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        findKH();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        NutBan = null;
        this.dispose();
        new FrmBanJD(new javax.swing.JFrame(), true).setVisible(true);
    }//GEN-LAST:event_lblExitMouseClicked

    private void btnHuyBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyBanActionPerformed
        // TODO add your handling code here:
        if (count == 0) {
            this.huyBan();
        } else {
            XDialog.alert(this, "Vui lòng thêm bàn trước khi huỷ!");
        }

    }//GEN-LAST:event_btnHuyBanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBoMon;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChonMon;
    private javax.swing.JButton btnHuyBan;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboNhanVen;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JScrollPane jspChonMon;
    private javax.swing.JScrollPane jspMonDaChon;
    private javax.swing.JLabel lblEMail;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNV;
    private javax.swing.JLabel lblNguoiLon;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSoBan;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JLabel lblTenChuBan;
    private javax.swing.JLabel lblThoiGian;
    private javax.swing.JLabel lblTreEm;
    private javax.swing.JPanel pnlChonMon;
    private javax.swing.JPanel pnlEmail;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlMaNV;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMonDaChon;
    private javax.swing.JPanel pnlNguoiLon;
    private javax.swing.JPanel pnlSDT;
    private javax.swing.JPanel pnlSideBody;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlSoBan;
    private javax.swing.JPanel pnlSoLuong;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlTenChuBan;
    private javax.swing.JPanel pnlThoiGian;
    private javax.swing.JPanel pnlTreEm;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblMDT;
    private javax.swing.JTable tblMT;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNguoiLon;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenChuBan;
    private javax.swing.JTextField txtThoiGian;
    private javax.swing.JTextField txtTreEm;
    // End of variables declaration//GEN-END:variables
}
