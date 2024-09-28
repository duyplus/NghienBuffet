package buffet.dao;

import buffet.model.NguoiDung;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class NguoiDungDAO extends BuffetDAO<NguoiDung, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO NguoiDung (TaiKhoan,MatKhau,HoTen,VaiTro) VALUES (?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE NguoiDung SET MatKhau=?,HoTen=?,VaiTro=? WHERE TaiKhoan=?";
    public static String DELETE_SQL = "DELETE FROM NguoiDung WHERE TaiKhoan=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM NguoiDung";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM NguoiDung WHERE TaiKhoan=?";

    @Override
    public void insert(NguoiDung entity) {
        XJDBC.update(INSERT_SQL,
                entity.getTaiKhoan(),
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.isVaiTro()
        );
    }

    @Override
    public void update(NguoiDung entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.isVaiTro(),
                entity.getTaiKhoan()
        );
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<NguoiDung> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguoiDung selectById(String key) {
        List<NguoiDung> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NguoiDung> selectBySql(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    NguoiDung entity = new NguoiDung();
                    entity.setTaiKhoan(rs.getString("TaiKhoan"));
                    entity.setMatKhau(rs.getString("MatKhau"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setVaiTro(rs.getBoolean("VaiTro"));
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
