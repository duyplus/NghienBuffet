package buffet.model;

import java.sql.Time;

/**
 *
 * @author DELL
 */
public class Ban {

    private String maBan;
    private String SDT;
    private String maNV;
    private String tenNV;
    private String SoBan;
    private Time gioDat;
    private int soLuong;
    private int nguoiLon;
    private int treEm;

    public Ban() {
    }

    public Ban(String maBan, String SDT, String maNV, String tenNV, String SoBan, Time gioDat, int soLuong, int nguoiLon, int treEm) {
        this.maBan = maBan;
        this.SDT = SDT;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.SoBan = SoBan;
        this.gioDat = gioDat;
        this.soLuong = soLuong;
        this.nguoiLon = nguoiLon;
        this.treEm = treEm;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSoBan() {
        return SoBan;
    }

    public void setSoBan(String SoBan) {
        this.SoBan = SoBan;
    }

    public Time getGioDat() {
        return gioDat;
    }

    public void setGioDat(Time gioDat) {
        this.gioDat = gioDat;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
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

}
