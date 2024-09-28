package buffet.model;

import java.sql.Time;

/**
 *
 * @author HPhi
 */
public class KhachHang {

    private String sdt;
    private String tenKH;
    private String email;
    
    
    @Override
    public String toString(){
        return this.sdt;
    }

    public KhachHang() {
    }

    public KhachHang(String sdt, String tenKH, String email) {
        this.sdt = sdt;
        this.tenKH = tenKH;
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    
    
}
