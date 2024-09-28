/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buffet.model;

/**
 *
 * @author Danh Loi
 */
public class TienMT {
    private int soLuong;
    private float gia;

    public TienMT() {
    }

    public TienMT(int soLuong, float gia) {
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

}
