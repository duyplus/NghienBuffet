package buffet.dao;

import buffet.model.KhachHang;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class KhachHangDAO extends BuffetDAO<KhachHang, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO KhachHang (SDT,TenKH,Email) VALUES (?,?,?)";
    public static String UPDATE_SQL = "UPDATE KhachHang SET TenKH=?,Email=? WHERE SDT=?";
    public static String DELETE_SQL = "DELETE FROM KhachHang WHERE SDT=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE SDT=?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM KhachHang WHERE SDT LIKE ?";

    @Override
    public void insert(KhachHang entity) {
        XJDBC.update(INSERT_SQL,
                entity.getSdt(),
                entity.getTenKH(),
                entity.getEmail());
    }

    @Override
    public void update(KhachHang entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getTenKH(),
                entity.getEmail(),
                entity.getSdt());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(String key) {
        List<KhachHang> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    KhachHang entity = new KhachHang();
                    entity.setSdt(rs.getString("SDT"));
                    entity.setTenKH(rs.getString("TenKH"));
                    entity.setEmail(rs.getString("Email"));

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

    public List<KhachHang> selectByKhachHang(String key) {
        return this.selectBySql(SELECT_BY_ID_SQL, key);
    }

    public KhachHang selectBySDT(String key) {
        return (KhachHang) this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%");
    }
}
