package buffet.dao;

import buffet.model.NhanVien;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danh Loi
 */
public class NhanVienDAO extends BuffetDAO<NhanVien, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES (?,?,?,?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE NhanVien SET HoTen=?,NgaySinh=?,GioiTinh=?,SDT=?,Email=?,DiaChi=?,Hinh=? WHERE MaNV=?";
    public static String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV=?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM NhanVien WHERE HoTen LIKE ? OR MaNV LIKE ? OR SDT LIKE ?";

    @Override
    public void insert(NhanVien entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaNV(),
                entity.getHoTen(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.getHinh());
    }

    @Override
    public void update(NhanVien entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getHoTen(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.getHinh(),
                entity.getMaNV());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String key) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    NhanVien entity = new NhanVien();
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setNgaySinh(rs.getDate("NgaySinh"));
                    entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                    entity.setSdt(rs.getString("SDT"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setDiaChi(rs.getString("DiaChi"));
                    entity.setHinh(rs.getString("Hinh"));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<NhanVien> selectByKeyword(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
    }

}
