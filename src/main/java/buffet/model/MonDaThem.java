package buffet.model;

/**
 *
 * @author DELL
 */
public class MonDaThem {
    private int maMDT;
    private String maBan;
    private String maMT;
    private int soLuong;

    public MonDaThem() {
    }

    public MonDaThem(int maMDT, String maBan, String maMT, int soLuong) {
        this.maMDT = maMDT;
        this.maBan = maBan;
        this.maMT = maMT;
        this.soLuong = soLuong;
    }

    @Override
    public String toString(){
        return this.maBan;
    }
    
    public int getMaMDT() {
        return maMDT;
    }

    public void setMaMDT(int maMDT) {
        this.maMDT = maMDT;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getMaMT() {
        return maMT;
    }

    public void setMaMT(String maMT) {
        this.maMT = maMT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    
    
}
