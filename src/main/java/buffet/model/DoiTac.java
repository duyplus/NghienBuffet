package buffet.model;

/**
 *
 * @author Danh Loi
 */
public class DoiTac {

    private String maDT;
    private String tenDT;
    private String sdt;
    private String email;
    private String diaChi;

    public DoiTac() {
    }

    public DoiTac(String maDT, String tenDT, String sdt, String email, String diaChi) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return this.maDT;
    }
    
    public String getMaDT() {
        return maDT;
    }

    public void setMaDT(String maDT) {
        this.maDT = maDT;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

}
