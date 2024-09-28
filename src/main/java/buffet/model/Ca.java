package buffet.model;

import buffet.utils.XDate;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Danh Loi
 */
public class Ca {

    private int maCa;
    private Date ngayLam;
    private String caLam;
    private Time batDau = XDate.toTime("00:00");
    private Time ketThuc = XDate.toTime("00:00");
    private String maNV;

    public Ca() {
    }

    public Ca(int maCa, Date ngayLam, String caLam, Time batDau, Time ketThuc, String maNV) {
        this.maCa = maCa;
        this.ngayLam = ngayLam;
        this.caLam = caLam;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
        this.maNV = maNV;
    }

    public int getMaCa() {
        return maCa;
    }

    public void setMaCa(int maCa) {
        this.maCa = maCa;
    }

    public Date getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(Date ngayLam) {
        this.ngayLam = ngayLam;
    }

    public String getCaLam() {
        return caLam;
    }

    public void setCaLam(String caLam) {
        this.caLam = caLam;
    }

    public Time getBatDau() {
        return batDau;
    }

    public void setBatDau(Time batDau) {
        this.batDau = batDau;
    }

    public Time getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(Time ketThuc) {
        this.ketThuc = ketThuc;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

}
