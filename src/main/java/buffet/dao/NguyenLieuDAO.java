package buffet.dao;

import buffet.model.NguyenLieu;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguyenLieuDAO extends BuffetDAO<NguyenLieu, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO NguyenLieu (MaNL,TenNL,SoLuong,DVT,HSD,MaDT) VALUES (?,?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE NguyenLieu SET TenNL=?,SoLuong=?,DVT=?,HSD=?,MaDT=? WHERE MaNL=?";
    public static String DELETE_SQL = "DELETE FROM NguyenLieu WHERE MaNL=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM NguyenLieu";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM NguyenLieu WHERE MaNL=?";
    public static String COUNT_BY_ID_SQL = "SELECT COUNT(TenNL) FROM NguyenLieu";
    public static String UPDATE_BY_ID_SQL = "UPDATE NguyenLieu SET SoLuong=? WHERE MaNL=?";

    @Override
    public void insert(NguyenLieu entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaNL(),
                entity.getTenNL(),
                entity.getSoLuong(),
                entity.getdVT(),
                entity.getHsd(),
                entity.getMaDT());
    }

    @Override
    public void update(NguyenLieu entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getTenNL(),
                entity.getSoLuong(),
                entity.getdVT(),
                entity.getHsd(),
                entity.getMaDT(),
                entity.getMaNL());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<NguyenLieu> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguyenLieu selectById(String key) {
        List<NguyenLieu> list = selectBySql(SELECT_BY_ID_SQL, key);
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    protected List<NguyenLieu> selectBySql(String sql, Object... args) {
        List<NguyenLieu> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    NguyenLieu entity = new NguyenLieu();
                    entity.setMaNL(rs.getString("MaNL"));
                    entity.setTenNL(rs.getString("TenNL"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setdVT(rs.getString("DVT"));
                    entity.setHsd(rs.getDate("HSD"));
                    entity.setMaDT(rs.getString("MaDT"));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public String countAll() {
        String count = null;
        rs = XJDBC.query(COUNT_BY_ID_SQL);
        try {
            try {
                while (rs.next()) {
                    count = rs.getString("");
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return count;
    }

}
