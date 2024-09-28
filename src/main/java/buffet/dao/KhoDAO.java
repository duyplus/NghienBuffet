package buffet.dao;

import buffet.model.Kho;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhoDAO extends BuffetDAO<Kho, String> {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO Kho (NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (?,?,?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE Kho SET NgayXN=?,Soluong=?,TrangThai=?,TiGia=?,TongGia=?,MaNL=?,MaNV=? WHERE MaXN=?";
    public static String DELETE_SQL = "DELETE FROM Kho WHERE MaXN=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM Kho";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM Kho WHERE MaXN=?";

    @Override
    public void insert(Kho entity) {
        XJDBC.update(INSERT_SQL,
                entity.getNgayXN(),
                entity.getSoLuong(),
                entity.getTrangThai(),
                entity.getTiGia(),
                entity.getTongGia(),
                entity.getMaNL(),
                entity.getMaNV());
    }

    @Override
    public void update(Kho entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getNgayXN(),
                entity.getSoLuong(),
                entity.getTrangThai(),
                entity.getTiGia(),
                entity.getTongGia(),
                entity.getMaNL(),
                entity.getMaNV(),
                entity.getMaXN());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<Kho> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Kho selectById(String key) {
        List<Kho> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<Kho> selectBySql(String sql, Object... args) {
        List<Kho> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    Kho entity = new Kho();
                    entity.setMaXN(rs.getInt("MaXN"));
                    entity.setNgayXN(rs.getDate("NgayXN"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setTrangThai(rs.getString("TrangThai"));
                    entity.setTiGia(rs.getInt("TiGia"));
                    entity.setTongGia(rs.getInt("TongGia"));
                    entity.setMaNL(rs.getString("MaNL"));
                    entity.setMaNV(rs.getString("MaNV"));
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
}
