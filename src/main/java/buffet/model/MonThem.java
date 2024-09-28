package buffet.model;

/**
 *
 * @author Danh Loi
 */
public class MonThem {

    private String maMT;
    private String tenMT;
    private int gia;

    @Override
    public String toString() {
        return this.tenMT;
    }

    public MonThem() {
    }

    public MonThem(String maMT, String tenMT, int gia) {
        this.maMT = maMT;
        this.tenMT = tenMT;
        this.gia = gia;
    }

    public String getMaMT() {
        return maMT;
    }

    public void setMaMT(String maMT) {
        this.maMT = maMT;
    }

    public String getTenMT() {
        return tenMT;
    }

    public void setTenMT(String tenMT) {
        this.tenMT = tenMT;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

}
