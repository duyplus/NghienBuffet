package buffet.model;

/**
 *
 * @author ducdu
 */
public class KHTT {

    private String TenKH;
    private String SDT;
    private String Email;
    private int SoLanDen;

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getSoLanDen() {
        return SoLanDen;
    }

    public void setSoLanDen(int SoLanDen) {
        this.SoLanDen = SoLanDen;
    }

    public KHTT(String TenKH, String SDT, String Email, int SoLanDen) {
        this.TenKH = TenKH;
        this.SDT = SDT;
        this.Email = Email;
        this.SoLanDen = SoLanDen;
    }

    public KHTT() {
    }

}
