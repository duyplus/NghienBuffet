package buffet.dao;

import buffet.model.DoiTac;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ducdu
 */
public class DoiTacDAO extends BuffetDAO<DoiTac, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES (?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE DoiTac SET TenDT=?,SDT=?,Email=?,DiaChi=? WHERE MaDT=?";
    public static String DELETE_SQL = "DELETE FROM DoiTac WHERE MaDT=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM DoiTac";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM DoiTac WHERE MaDT=?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM DoiTac WHERE MaDT LIKE ? OR TenDT LIKE ? OR SDT LIKE ?";

    @Override
    public void insert(DoiTac entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaDT(),
                entity.getTenDT(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getDiaChi()
        );
    }

    @Override
    public void update(DoiTac entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getTenDT(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.getMaDT()
        );
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<DoiTac> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DoiTac selectById(String key) {
        List<DoiTac> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<DoiTac> selectBySql(String sql, Object... args) {
        List<DoiTac> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    DoiTac entity = new DoiTac();
                    entity.setMaDT(rs.getString("MaDT"));
                    entity.setTenDT(rs.getString("TenDT"));
                    entity.setSdt(rs.getString("SDT"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setDiaChi(rs.getString("DiaChi"));
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

    public List<DoiTac> selectByKeyword(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
    }
}
