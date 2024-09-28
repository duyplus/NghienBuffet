package buffet.dao;

import buffet.model.Ve;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danh Loi
 */
public class VeDAO extends BuffetDAO<Ve, String> {

    public static ResultSet rs = null;
    public static String UPDATE_SQL = "UPDATE Ve SET Gia=? WHERE MaVe=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM Ve";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM Ve WHERE MaVe=?";
    public static String SELECT_GIA = "SELECT * FROM Ve WHERE MaVe=?";

    @Override
    public void insert(Ve entity) {
    }

    @Override
    public void update(Ve entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getGia(),
                entity.getMaVe()
        );
    }

    @Override
    public void delete(String key) {
    }

    @Override
    public List<Ve> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Ve selectById(String key) {
        List<Ve> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<Ve> selectBySql(String sql, Object... args) {
        List<Ve> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    Ve entity = new Ve();
                    entity.setMaVe(rs.getString("MaVe"));
                    entity.setLoaiVe(rs.getString("LoaiVe"));
                    entity.setGia(rs.getInt("Gia"));
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
    
    public List<Ve> selectgia(String key){
        return selectBySql(SELECT_GIA, key);
    }
}
