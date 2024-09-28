package buffet.model;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Danh Loi
 */
public class HoaDon {

    private String maHD;
    private String soBan;
    private String maNV;
    private Time gioVao;
    private Date ngayXuat;
    private int nguoiLon;
    private int treEm;
    private int tongVe;
    private double tongTien;
    private String tenKH;
    private String SDT;

    @Override
    public String toString() {
        return this.maNV;
    }

    public HoaDon() {
    }

    public HoaDon(String maHD, String soBan, String maNV, Time gioVao, Date ngayXuat, int nguoiLon, int treEm, int tongVe, double tongTien, String tenKH, String SDT) {
        this.maHD = maHD;
        this.soBan = soBan;
        this.maNV = maNV;
        this.gioVao = gioVao;
        this.ngayXuat = ngayXuat;
        this.nguoiLon = nguoiLon;
        this.treEm = treEm;
        this.tongVe = tongVe;
        this.tongTien = tongTien;
        this.tenKH = tenKH;
        this.SDT = SDT;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getSoBan() {
        return soBan;
    }

    public void setSoBan(String soBan) {
        this.soBan = soBan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Time getGioVao() {
        return gioVao;
    }

    public void setGioVao(Time gioVao) {
        this.gioVao = gioVao;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public int getNguoiLon() {
        return nguoiLon;
    }

    public void setNguoiLon(int nguoiLon) {
        this.nguoiLon = nguoiLon;
    }

    public int getTreEm() {
        return treEm;
    }

    public void setTreEm(int treEm) {
        this.treEm = treEm;
    }

    public int getTongVe() {
        return tongVe;
    }

    public void setTongVe(int tongVe) {
        this.tongVe = tongVe;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

}
