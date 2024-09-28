package buffet.model;

/**
 *
 * @author Danh Loi
 */
public class Ve {

    private String maVe;
    private String loaiVe;
    private int gia;

    public Ve() {
    }

    public Ve(String maVe, String loaiVe, int gia) {
        this.maVe = maVe;
        this.loaiVe = loaiVe;
        this.gia = gia;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public String getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(String loaiVe) {
        this.loaiVe = loaiVe;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

}
