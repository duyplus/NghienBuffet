package buffet.ui.ban;

import buffet.dao.BanDAO;
import buffet.dao.HoaDonDAO;
import buffet.dao.MonDaThemDAO;
import buffet.dao.MonThemDAO;
import buffet.dao.VeDAO;
import buffet.model.Ban;
import buffet.model.HoaDon;
import buffet.model.MonDaThem;
import buffet.model.TienMT;
import buffet.model.Ve;
import buffet.utils.XAuth;
import buffet.utils.XDate;
import buffet.utils.XDialog;
import buffet.utils.XHelper;
import buffet.utils.XValidate;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author duyplus
 */
public class FrmHoaDonJD extends javax.swing.JDialog {

    BanDAO bdao = new BanDAO();
    HoaDonDAO hddao = new HoaDonDAO();
    MonDaThemDAO mdtdao = new MonDaThemDAO();
    MonThemDAO mtdao = new MonThemDAO();
    VeDAO vdao = new VeDAO();

    /**
     * Creates new form QLHoaDonCTJD
     *
     * @param parent
     * @param modal
     * @param hd
     * @param model
     */
    public FrmHoaDonJD(java.awt.Frame parent, boolean modal, HoaDon hd, TableModel model) {
        super(parent, modal);
        initComponents();
        setForm(hd, model);
        fillTableMonDaThem(model);
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        fillTableHD();
        setFormHD();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel hd = tblHoaDon.getColumnModel();
        hd.getColumn(0).setCellRenderer(render);
        hd.getColumn(1).setCellRenderer(render);
        hd.getColumn(2).setCellRenderer(render);
        hd.getColumn(4).setCellRenderer(render);
        hd.getColumn(7).setCellRenderer(render);

        hd.getColumn(0).setMaxWidth(100);
        hd.getColumn(1).setMaxWidth(100);
        hd.getColumn(7).setMaxWidth(100);

        Font font = new Font("Be Vietnam Pro", Font.PLAIN, 16);
        tblHoaDon.getTableHeader().setFont(font);
        tblHoaDon.getTableHeader().setPreferredSize(new Dimension(100, 35));

        //set from khi không có dữ kiệu
        if (txtSoBan.getText().equals("") && txtSDT.getText().equals("") && txtTenChuBan.getText().equals("")) {
            txtGioVao.setText("");
            txtNgayXuat.setText("");
            txtTongVe.setText("");
            txtNguoiLon.setText("");
            txtTreEm.setText("");
            txtTienVe.setText("");
            txtTienThem.setText("");
            txtMaNV.setText("");
            txtTienPhat.setText("");
            txtGiamGia.setText("");
            btnInHoaDon.setVisible(false);
        }
    }

    private void fillTableMonDaThem(TableModel model) {
        tblMDT.setModel(model);
    }

    private void fillTableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        List<HoaDon> list = hddao.selectByKeyword(txtTimKiem.getText());
        for (HoaDon hd : list) {
            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getSoBan(),
                XDate.toStringTime(hd.getGioVao()),
                XDate.toString(hd.getNgayXuat()),
                hd.getTongVe(),
                XHelper.commafy(String.valueOf(hd.getTongTien())),
                hd.getSDT(),
                String.valueOf(hd.getMaNV())
            });
        }
    }

    private int tienVe(int nl, int te) {
        int gianl = 0;
        int giate = 0;
        List<Ve> list = vdao.selectgia("1");
        for (Ve ve : list) {
            gianl = ve.getGia();
        }
        List<Ve> list1 = vdao.selectgia("2");
        for (Ve ve : list1) {
            giate = ve.getGia();
        }
        int tong = (gianl * nl) + (giate * te);
        return tong;
    }

    @SuppressWarnings("unchecked")
    private int tienMT(String key) {
        List<TienMT> list = mdtdao.selectgia(key);
        int tienthem = 0;
        for (TienMT tmt : list) {
            tienthem += tmt.getGia() * tmt.getSoLuong();
        }
        return tienthem;
    }

    private HoaDon getForm() {
        HoaDon hd = new HoaDon();
        hd.setSoBan(txtSoBan.getText());
        hd.setGioVao(Time.valueOf(txtGioVao.getText()));
        hd.setNgayXuat(XDate.toDate(txtNgayXuat.getText()));
        hd.setNguoiLon(Integer.parseInt(txtNguoiLon.getText()));
        hd.setTreEm(Integer.parseInt(txtTreEm.getText()));
        hd.setTongVe(Integer.parseInt(txtTongVe.getText()));
        hd.setTongTien(Double.parseDouble(txtTongTien.getToolTipText()));
        hd.setSDT(txtSDT.getText());
        hd.setMaNV(txtMaNV.getText());
        return hd;
    }

    private void setForm(HoaDon hd, TableModel model) {
        txtSoBan.setText(hd.getSoBan());
        txtTenChuBan.setText(hd.getTenKH());
        txtSDT.setText(hd.getSDT());
        txtGioVao.setText(String.valueOf(hd.getGioVao()));
        txtMaNV.setText(hd.getMaNV());
        txtNguoiLon.setText(String.valueOf(hd.getNguoiLon()));
        txtTreEm.setText(String.valueOf(hd.getTreEm()));
        // Tính tổng số người
        int sl = hd.getNguoiLon() + hd.getTreEm();
        txtTongVe.setText(String.valueOf(sl));
        // Tính tiền vé
        int tv = tienVe(hd.getNguoiLon(), hd.getTreEm());
        txtTienVe.setText(XHelper.commafy(String.valueOf(tv)));
        //Tính tiền món thêm
        int tt = tienMT(hd.getSoBan());
        txtTienThem.setText(XHelper.commafy(String.valueOf(tt)));
        double tong = (tv + tt) - ((tv + tt) * checkLanDen(txtSDT.getText()));
        txtTongTien.setText(XHelper.commafy(Double.toString(tong)));
        txtTongTien.setToolTipText(Double.toString(tong));
    }

    private void insertHDdeleteBan() {
        HoaDon hd = getForm();
        if (XDialog.confirm(this, "Bạn có thực sự muốn thanh toán hoá đơn này?")) {
            String ban = txtSoBan.getText();
            try {
                hddao.insert(hd);
                this.inHoaDon();
                bdao.delete(ban);
                XDialog.alert(this, "Thanh toán và xóa bàn thành công!");
                dispose();
                new FrmBanCTJD().dispose();
                new FrmBanJD(new javax.swing.JFrame(), true).setVisible(true);
            } catch (Exception e) {
                XDialog.alert(this, "Thanh toán và xóa bàn thất bại! ");
            }
        }
    }

    private void showName() {
        lblStaff.setText(XAuth.user.getTaiKhoan());
    }

    private double checkLanDen(String key) {
        int sld = hddao.selectSoLanDen(key);
        Float giam;
        if (sld == 5) {
            lblLanDen.setText("Khách hàng đến lần 6 được giảm 5%");
            txtGiamGia.setText("5%");
            giam = 0.05f;
        } else if (sld == 10) {
            lblLanDen.setText("Khách hàng đến lần 11 được giảm 10%");
            txtGiamGia.setText("10%");
            giam = 0.1f;
        } else if (sld == 15) {
            lblLanDen.setText("Khách hàng đến lần 16 được giảm 20%");
            txtGiamGia.setText("20%");
            giam = 0.2f;
        } else {
            lblLanDen.setText("Chúc quý khách vui vẻ, hẹn gặp lại");
            txtGiamGia.setText("0%");
            giam = 0f;
        }
        return giam;
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
        pnlHoaDon = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        pnlInfo = new javax.swing.JPanel();
        lblBanSo = new javax.swing.JLabel();
        txtSoBan = new javax.swing.JTextField();
        lblTenChuBan = new javax.swing.JLabel();
        txtTenChuBan = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblGioVao = new javax.swing.JLabel();
        txtGioVao = new javax.swing.JTextField();
        lblSoNguoi = new javax.swing.JLabel();
        txtTongVe = new javax.swing.JTextField();
        lblNguoiLon = new javax.swing.JLabel();
        txtNguoiLon = new javax.swing.JTextField();
        lblTreEm = new javax.swing.JLabel();
        txtTreEm = new javax.swing.JTextField();
        lblTienVe = new javax.swing.JLabel();
        txtTienVe = new javax.swing.JTextField();
        lblMonThem = new javax.swing.JLabel();
        lblTienThem = new javax.swing.JLabel();
        txtTienThem = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblTongTien = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        btnInHoaDon = new javax.swing.JButton();
        lblNgayXuat = new javax.swing.JLabel();
        txtNgayXuat = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMDT = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        lblTienphat = new javax.swing.JLabel();
        txtTienPhat = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        lblGiamGia = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        lblThongBao = new javax.swing.JLabel();
        lblLanDen = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        pnlList = new javax.swing.JPanel();
        lblTimKiem = new javax.swing.JLabel();
        pnlTimKiem = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jspHoaDon = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ HOÁ ĐƠN");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFoot.setBackground(new java.awt.Color(0, 0, 0));
        pnlFoot.setPreferredSize(new java.awt.Dimension(256, 55));

        lblFoot.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        lblFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoot.setText("HOÁ ĐƠN");

        javax.swing.GroupLayout pnlFootLayout = new javax.swing.GroupLayout(pnlFoot);
        pnlFoot.setLayout(pnlFootLayout);
        pnlFootLayout.setHorizontalGroup(
            pnlFootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFootLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFoot)
                .addGap(115, 115, 115))
        );
        pnlFootLayout.setVerticalGroup(
            pnlFootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFoot, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(pnlSideBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
            .addComponent(pnlFoot, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
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

        pnlHoaDon.setBackground(new java.awt.Color(56, 182, 255));

        tabs.setFont(new java.awt.Font("Be Vietnam Pro", 0, 18)); // NOI18N

        pnlInfo.setBackground(new java.awt.Color(94, 229, 243));

        lblBanSo.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblBanSo.setText("Bàn số:");

        txtSoBan.setEditable(false);
        txtSoBan.setBackground(new java.awt.Color(94, 229, 243));
        txtSoBan.setFont(new java.awt.Font("Be Vietnam Pro", 1, 22)); // NOI18N
        txtSoBan.setForeground(new java.awt.Color(255, 51, 51));
        txtSoBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblTenChuBan.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTenChuBan.setText("Tên chủ bàn:");

        txtTenChuBan.setEditable(false);
        txtTenChuBan.setBackground(new java.awt.Color(94, 229, 243));
        txtTenChuBan.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtTenChuBan.setForeground(new java.awt.Color(255, 51, 51));
        txtTenChuBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblSDT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblSDT.setText("Số điện thoại:");

        txtSDT.setEditable(false);
        txtSDT.setBackground(new java.awt.Color(94, 229, 243));
        txtSDT.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(255, 51, 51));
        txtSDT.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblGioVao.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblGioVao.setText("Giờ vào:");

        txtGioVao.setEditable(false);
        txtGioVao.setBackground(new java.awt.Color(94, 229, 243));
        txtGioVao.setFont(new java.awt.Font("Be Vietnam Pro", 1, 22)); // NOI18N
        txtGioVao.setForeground(new java.awt.Color(255, 51, 51));
        txtGioVao.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblSoNguoi.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblSoNguoi.setText("Tổng vé:");

        txtTongVe.setEditable(false);
        txtTongVe.setBackground(new java.awt.Color(94, 229, 243));
        txtTongVe.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtTongVe.setForeground(new java.awt.Color(255, 51, 51));
        txtTongVe.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTongVe.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblNguoiLon.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNguoiLon.setText("Người lớn:");

        txtNguoiLon.setEditable(false);
        txtNguoiLon.setBackground(new java.awt.Color(94, 229, 243));
        txtNguoiLon.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtNguoiLon.setForeground(new java.awt.Color(255, 51, 51));
        txtNguoiLon.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNguoiLon.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblTreEm.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTreEm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTreEm.setText("Trẻ em:");

        txtTreEm.setEditable(false);
        txtTreEm.setBackground(new java.awt.Color(94, 229, 243));
        txtTreEm.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtTreEm.setForeground(new java.awt.Color(255, 51, 51));
        txtTreEm.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTreEm.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblTienVe.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTienVe.setText("Tiền vé:");

        txtTienVe.setEditable(false);
        txtTienVe.setBackground(new java.awt.Color(94, 229, 243));
        txtTienVe.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtTienVe.setForeground(new java.awt.Color(255, 51, 51));
        txtTienVe.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblMonThem.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMonThem.setText("Món gọi thêm:");

        lblTienThem.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTienThem.setText("Tiền gọi thêm:");

        txtTienThem.setEditable(false);
        txtTienThem.setBackground(new java.awt.Color(94, 229, 243));
        txtTienThem.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtTienThem.setForeground(new java.awt.Color(255, 51, 51));
        txtTienThem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        lblTongTien.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblTongTien.setText("TỔNG TIỀN:");

        txtTongTien.setEditable(false);
        txtTongTien.setBackground(new java.awt.Color(94, 229, 243));
        txtTongTien.setFont(new java.awt.Font("Be Vietnam Pro", 1, 36)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 51, 51));
        txtTongTien.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        btnInHoaDon.setBackground(new java.awt.Color(0, 80, 143));
        btnInHoaDon.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnInHoaDon.setText("IN HOÁ ĐƠN");
        btnInHoaDon.setBorderPainted(false);
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        lblNgayXuat.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblNgayXuat.setText("Ngày xuất:");

        txtNgayXuat.setEditable(false);
        txtNgayXuat.setBackground(new java.awt.Color(94, 229, 243));
        txtNgayXuat.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtNgayXuat.setForeground(new java.awt.Color(255, 51, 51));
        txtNgayXuat.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        tblMDT.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        tblMDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMDT.setEnabled(false);
        tblMDT.setMaximumSize(new java.awt.Dimension(2147483647, 122));
        tblMDT.setMinimumSize(new java.awt.Dimension(60, 122));
        tblMDT.setPreferredSize(new java.awt.Dimension(300, 122));
        tblMDT.setRowHeight(30);
        tblMDT.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblMDT);

        lblMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblMaNV.setText("Nhân viên:");

        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new java.awt.Color(94, 229, 243));
        txtMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtMaNV.setForeground(new java.awt.Color(255, 51, 51));
        txtMaNV.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblTienphat.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblTienphat.setText("Tiền phạt:");

        txtTienPhat.setBackground(new java.awt.Color(94, 229, 243));
        txtTienPhat.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtTienPhat.setForeground(new java.awt.Color(255, 51, 51));
        txtTienPhat.setText("0");
        txtTienPhat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTienPhat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienPhatKeyReleased(evt);
            }
        });

        lblGiamGia.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblGiamGia.setText("Giảm giá:");

        txtGiamGia.setEditable(false);
        txtGiamGia.setBackground(new java.awt.Color(94, 229, 243));
        txtGiamGia.setFont(new java.awt.Font("Be Vietnam Pro", 1, 20)); // NOI18N
        txtGiamGia.setForeground(new java.awt.Color(255, 51, 51));
        txtGiamGia.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

        lblThongBao.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        lblThongBao.setForeground(new java.awt.Color(255, 51, 51));
        lblThongBao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThongBao.setText("Không áp dụng cho tiền phạt");

        lblLanDen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 20)); // NOI18N
        lblLanDen.setForeground(new java.awt.Color(255, 51, 51));
        lblLanDen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLanDen.setText("Chúc quý khách vui vẻ, hẹn gặp lại");

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(jSeparator11)
                                .addGap(54, 54, 54))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTongVe, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTienVe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblSoNguoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblNguoiLon)
                                            .addComponent(txtNguoiLon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTienVe, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12))))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(lblMaNV)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(lblGiamGia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(lblThongBao)
                                .addGap(28, 28, 28))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(lblTienThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienThem, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addComponent(lblBanSo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblGioVao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtGioVao))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlInfoLayout.createSequentialGroup()
                                        .addComponent(lblTienphat)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTienPhat, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlInfoLayout.createSequentialGroup()
                                        .addComponent(lblSDT)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblLanDen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jSeparator2)
                                            .addComponent(jSeparator5)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblTenChuBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblNgayXuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(13, 13, 13)
                                                .addComponent(txtNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jSeparator3)
                                            .addComponent(txtTreEm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblTreEm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSeparator7)
                                            .addComponent(txtTenChuBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSeparator4)))
                                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(12, 12, 12)))
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(lblMonThem)
                                .addGap(273, 273, 273))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlInfoLayout.createSequentialGroup()
                                .addComponent(lblTongTien)
                                .addGap(18, 18, 18)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBanSo)
                    .addComponent(txtSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMonThem)
                    .addComponent(lblGioVao)
                    .addComponent(txtGioVao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenChuBan)
                            .addComponent(txtTenChuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSDT)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNgayXuat)
                            .addComponent(txtNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNguoiLon)
                            .addComponent(lblSoNguoi)
                            .addComponent(lblTreEm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTongVe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNguoiLon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTreEm, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTienVe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTienVe))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTienThem)
                            .addComponent(txtTienThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaNV)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTienphat)
                            .addComponent(txtTienPhat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblGiamGia))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblThongBao))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLanDen)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnInHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        tabs.addTab("Chi tiết hoá đơn", new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-info.png")), pnlInfo); // NOI18N

        pnlList.setBackground(new java.awt.Color(0, 204, 255));

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

        tblHoaDon.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ HĐ", "SỐ BÀN", "GIỜ VÀO", "NGÀY XUẤT", "TỔNG VÉ", "TỔNG TIỀN", "SĐT", "MÃ NV"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(30);
        tblHoaDon.getTableHeader().setReorderingAllowed(false);
        jspHoaDon.setViewportView(tblHoaDon);

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(lblTimKiem)
                .addGap(32, 32, 32)
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jspHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE))
        );

        tabs.addTab("Danh sách", new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-bulleted-list.png")), pnlList); // NOI18N

        javax.swing.GroupLayout pnlHoaDonLayout = new javax.swing.GroupLayout(pnlHoaDon);
        pnlHoaDon.setLayout(pnlHoaDonLayout);
        pnlHoaDonLayout.setHorizontalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 860, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlHoaDonLayout.setVerticalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(tabs)
                .addContainerGap())
        );

        javax.swing.GroupLayout pblBanLayout = new javax.swing.GroupLayout(pblBan);
        pblBan.setLayout(pblBanLayout);
        pblBanLayout.setHorizontalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addGroup(pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pblBanLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );
        pblBanLayout.setVerticalGroup(
            pblBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pblBanLayout.createSequentialGroup()
                .addComponent(lblExit)
                .addGap(0, 0, 0)
                .addComponent(pnlHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pblBan, javax.swing.GroupLayout.PREFERRED_SIZE, 1224, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.showName();
        if (txtSoBan.getText().equals("")) {
            txtNgayXuat.setText("");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(System.currentTimeMillis());
            txtNgayXuat.setText(sdf.format(date));
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkBill(txtSoBan)) {
            this.insertHDdeleteBan();
        }
        this.fillTableHD();
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        this.fillTableHD();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTienPhatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienPhatKeyReleased
        // TODO add your handling code here:
        try {
            if (txtTienPhat.getText().length() > 6) {
                return;
            } else {
                // Cộng tiền phạt vào tổng
                double tong = Double.parseDouble(txtTongTien.getToolTipText()) + Double.parseDouble(txtTienPhat.getText());
                txtTongTien.setText(XHelper.commafy(Double.toString(tong)));
            }
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_txtTienPhatKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JScrollPane jspHoaDon;
    private javax.swing.JLabel lblBanSo;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblGioVao;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLanDen;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMonThem;
    private javax.swing.JLabel lblNgayXuat;
    private javax.swing.JLabel lblNguoiLon;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSoNguoi;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JLabel lblTenChuBan;
    private javax.swing.JLabel lblThongBao;
    private javax.swing.JLabel lblTienThem;
    private javax.swing.JLabel lblTienVe;
    private javax.swing.JLabel lblTienphat;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTreEm;
    private javax.swing.JPanel pblBan;
    private javax.swing.JPanel pnlFoot;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlSideBody;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblMDT;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtGioVao;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayXuat;
    private javax.swing.JTextField txtNguoiLon;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoBan;
    private javax.swing.JTextField txtTenChuBan;
    private javax.swing.JTextField txtTienPhat;
    private javax.swing.JTextField txtTienThem;
    private javax.swing.JTextField txtTienVe;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongVe;
    private javax.swing.JTextField txtTreEm;
    // End of variables declaration//GEN-END:variables

    Double btHeight = 0.0;
    Double bbHeight = 0.0;
    //--Lưu món đã thêm
    ArrayList<String> tenMon = new ArrayList<>();
    ArrayList<Integer> soLuongMon = new ArrayList<>();
    ArrayList<Integer> donGiaMon = new ArrayList<>();
    ArrayList<Integer> thanhTienMon = new ArrayList<>();
    //--Lưu người đến
    ArrayList<String> loaiVe = new ArrayList<>();
    ArrayList<Integer> soLuong = new ArrayList<>();
    ArrayList<Integer> donGiaVe = new ArrayList<>();
    ArrayList<Integer> tongTien = new ArrayList<>();

    private void inHoaDon() {
        btHeight = Double.valueOf(loaiVe.size());
        bbHeight = Double.valueOf(tenMon.size());

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(), getPageFormat(pj));
        try {
            //XDialog.alert(this, "Vui lòng tắt hoá đơn đang mở (nếu có) để in hoá đơn mới!");
            pj.print();
        } catch (PrinterException ex) {
            return;
        }
    }

    private void setFormHD() {
        List<MonDaThem> mc = mdtdao.selectByMonThem(txtSoBan.getText());
        for (int i = 0; i < mc.size(); i++) {
            MonDaThem mdt = mc.get(i);
            String tm = mtdao.selectById(mdt.getMaMT()).getTenMT();
            int soluong = mdt.getSoLuong();
            int dongia = (int) mtdao.selectById(mdt.getMaMT()).getGia();
            tenMon.add(tm);
            soLuongMon.add(soluong);
            donGiaMon.add(dongia);
            thanhTienMon.add(soluong * dongia);
        }
        List<Ban> ban = bdao.selectByBan(txtSoBan.getText());
        List<Ve> ve = vdao.selectAll();
        for (int i = 0; i < ban.size(); i++) {
            String loaive;
            int gia;
            Ban bans = ban.get(i);
            int soluongNL = bans.getNguoiLon();
            int soluongTE = bans.getTreEm();
            soLuong.add(soluongNL);
            soLuong.add(soluongTE);
            for (int j = 0; j < ve.size(); j++) {
                Ve v = ve.get(j);
                loaive = v.getLoaiVe();
                gia = v.getGia();
                loaiVe.add(loaive);
                donGiaVe.add(gia);
            }
        }
    }

    private PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double bodyHeight = btHeight + bbHeight;
        double headerHeight = 5.0;
        double footerHeight = 8.0;
        double width = cm_to_pp(25);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    private class BillPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            int r = tenMon.size();
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;
                Graphics2D g2dtitle = (Graphics2D) graphics;
                Graphics2D g2dtt = (Graphics2D) graphics;
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
                try {
                    int y = 20;
                    int yShift = 12;
                    int headerRectHeight = 15;
                    ImageIcon logo = new ImageIcon(XValidate.class.getResource("/icons/logo-app.png"));

                    g2d.drawImage(logo.getImage(), 162, 20, 100, 70, rootPane);
                    y += yShift + 90;
                    g2dtitle.setFont(new Font("Monospaced", Font.BOLD, 18));
                    g2dtitle.drawString(" PHIẾU THANH TOÁN ", 115, y);
                    y += yShift;
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawString("    ----------------------------------------------------", 48, y);
                    y += headerRectHeight;
                    g2d.setFont(new Font("Monospaced", Font.BOLD, 9));
                    g2d.drawString("Bàn số:", 70, y);
                    g2d.drawString(txtSoBan.getText(), 155, y);
                    y += yShift;
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawString("Giờ vào:", 70, y);
                    g2d.drawString(txtNgayXuat.getText() + " " + txtGioVao.getText(), 155, y);
                    y += yShift;
                    g2d.drawString("Nhân viên:", 70, y);
                    g2d.drawString(txtMaNV.getText(), 155, y);
                    y += yShift + 10;
                    g2d.drawString("Vé", 70, y);
                    g2d.drawString("SL", 175, y);
                    g2d.drawString("Đ.Giá", 220, y);
                    g2d.drawString("T.Tiền", 290, y);
                    y += yShift - 2;
                    g2d.drawString("    ----------------------------------------------------", 48, y);
                    y += headerRectHeight;
                    for (int i = 0; i < loaiVe.size(); i++) {
                        g2d.drawString(loaiVe.get(i) + ":", 70, y);
                        g2d.drawString(String.valueOf(soLuong.get(i)), 175, y);
                        g2d.drawString(XHelper.commafy(String.valueOf(donGiaVe.get(i))) + "", 220, y);
                        g2d.drawString(XHelper.commafy(String.valueOf(soLuong.get(i) * donGiaVe.get(i))), 290, y);
                        y += yShift;
                    }
                    g2d.drawString("Tổng số người:", 70, y);
                    g2d.drawString(txtTongVe.getText(), 175, y);
                    g2d.drawString(XHelper.commafy(String.valueOf(txtTienVe.getText())), 290, y);
                    y += yShift + 10;
                    g2d.drawString("Món", 70, y);
                    g2d.drawString("SL", 175, y);
                    g2d.drawString("Đ.Giá", 220, y);
                    g2d.drawString("T.Tiền", 290, y);
                    y += yShift - 2;
                    g2d.drawString("    ----------------------------------------------------", 48, y);
                    y += headerRectHeight;
                    for (int i = 0; i < r; i++) {
                        g2d.drawString(tenMon.get(i), 70, y);
                        g2d.drawString(String.valueOf(soLuongMon.get(i)), 175, y);
                        g2d.drawString(XHelper.commafy(String.valueOf(donGiaMon.get(i))), 220, y);
                        g2d.drawString(XHelper.commafy(String.valueOf(thanhTienMon.get(i))), 290, y);
                        y += yShift;
                    }
                    g2d.drawString("    ----------------------------------------------------", 48, y);
                    y += yShift;
                    g2dtt.setFont(new Font("Monospaced", Font.BOLD, 9));
                    g2dtt.drawString("Tiền phạt:", 70, y);
                    g2dtt.drawString(XHelper.commafy(String.valueOf(txtTienPhat.getText())), 290, y);
                    y += yShift + 2;
                    g2dtt.drawString("Giảm giá:", 70, y);
                    g2dtt.drawString(XHelper.commafy(String.valueOf(txtGiamGia.getText())), 175, y);
                    g2dtt.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2dtt.drawString("(" + lblThongBao.getText() + ")", 190, y);
                    g2dtt.setFont(new Font("Monospaced", Font.BOLD, 9));
                    y += yShift + 2;
                    g2dtt.drawString("Tổng cộng:", 70, y);
                    g2dtt.drawString(XHelper.commafy(String.valueOf(txtTongTien.getText())), 290, y);
                    y += yShift + 2;
                    g2dtt.drawString("Thanh toán:", 70, y);
                    g2dtt.drawString(XHelper.commafy(String.valueOf(txtTongTien.getText())), 290, y);
                    y += yShift;
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawString("    ----------------------------------------------------", 48, y);
                    y += yShift;
                    g2d.drawString("   CHÚC QUÝ KHÁCH VUI VẺ, HẸN GẶP LẠI ", 95, y);
                    y += yShift;
                    g2d.drawString("        ********************************************", 50, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result = PAGE_EXISTS;
            }
            return result;
        }
    }
}
