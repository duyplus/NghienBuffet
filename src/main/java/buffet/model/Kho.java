package buffet.model;

import java.util.Date;

/**
 *
 * @author Danh Loi
 */
public class Kho {

    private int maXN;
    private Date ngayXN;
    private int soLuong;
    private String trangThai;
    private int tiGia;
    private int tongGia;
    private String maNL;
    private String maNV;

    @Override
    public String toString() {
        return this.maNV;
    }

    public Kho() {
    }

    public Kho(int maXN, Date ngayXN, int soLuong, String trangThai, int tiGia, int tongGia, String maNL, String maNV) {
        this.maXN = maXN;
        this.ngayXN = ngayXN;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.tiGia = tiGia;
        this.tongGia = tongGia;
        this.maNL = maNL;
        this.maNV = maNV;
    }

    public int getMaXN() {
        return maXN;
    }

    public void setMaXN(int maXN) {
        this.maXN = maXN;
    }

    public Date getNgayXN() {
        return ngayXN;
    }

    public void setNgayXN(Date ngayXN) {
        this.ngayXN = ngayXN;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getTiGia() {
        return tiGia;
    }

    public void setTiGia(int tiGia) {
        this.tiGia = tiGia;
    }

    public int getTongGia() {
        return tongGia;
    }

    public void setTongGia(int tongGia) {
        this.tongGia = tongGia;
    }

    public String getMaNL() {
        return maNL;
    }

    public void setMaNL(String maNL) {
        this.maNL = maNL;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

}
