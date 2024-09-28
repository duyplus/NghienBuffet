package buffet.model;

import java.util.Date;

/**
 *
 * @author Danh Loi
 */
public class NguyenLieu {

    private String maNL;
    private String tenNL;
    private int soLuong;
    private String dVT;
    private Date hsd;
    private String maDT;

    @Override
    public String toString() {
        return this.tenNL + " (" + this.maNL + ")";
    }

    public NguyenLieu() {
    }

    public NguyenLieu(String maNL, String tenNL, int soLuong, String dVT, Date hsd, String maDT) {
        this.maNL = maNL;
        this.tenNL = tenNL;
        this.soLuong = soLuong;
        this.dVT = dVT;
        this.hsd = hsd;
        this.maDT = maDT;
    }

    public String getMaNL() {
        return maNL;
    }

    public void setMaNL(String maNL) {
        this.maNL = maNL;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getdVT() {
        return dVT;
    }

    public void setdVT(String dVT) {
        this.dVT = dVT;
    }

    public Date getHsd() {
        return hsd;
    }

    public void setHsd(Date hsd) {
        this.hsd = hsd;
    }

    public String getMaDT() {
        return maDT;
    }

    public void setMaDT(String maDT) {
        this.maDT = maDT;
    }

}
