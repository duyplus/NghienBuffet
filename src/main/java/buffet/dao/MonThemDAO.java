package buffet.dao;

import buffet.model.MonThem;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class MonThemDAO extends BuffetDAO<MonThem, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO MonThem (MaMT,TenMT,Gia) VALUES (?,?,?)";
    public static String UPDATE_SQL = "UPDATE MonThem SET TenMT=?,Gia=? WHERE MaMT=?";
    public static String DELETE_SQL = "DELETE FROM MonThem WHERE MaMT=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM MonThem";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM MonThem WHERE MaMT=?";

    @Override
    public void insert(MonThem entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaMT(),
                entity.getTenMT(),
                entity.getGia()
        );
    }

    @Override
    public void update(MonThem entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getTenMT(),
                entity.getGia(),
                entity.getMaMT()
        );
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<MonThem> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public MonThem selectById(String key) {
        List<MonThem> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<MonThem> selectBySql(String sql, Object... args) {
        List<MonThem> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    MonThem entity = new MonThem();
                    entity.setMaMT(rs.getString("MaMT"));
                    entity.setTenMT(rs.getString("TenMT"));
                    entity.setGia(rs.getInt("Gia"));
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
}
