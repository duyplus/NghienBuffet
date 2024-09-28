package buffet.dao;

import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ducdu
 */
public class DoanhThuDAO {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String DOANHTHUNGAY_SQL = "{CALL sp_DoanhThuNgay}";
    public static String DOANHTHUTHANG_SQL = "{CALL sp_DoanhThuThang}";
    public static String DOANHTHUNAM_SQL = "{CALL sp_DoanhThuNam}";

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            rs = XJDBC.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getDoanhThuNgay() {
        String[] cols = {"NgayBan", "TongBan", "TongVe", "TongTien"};
        return this.getListOfArray(DOANHTHUNGAY_SQL, cols);
    }

    public List<Object[]> getDoanhThuThang() {
        String[] cols = {"ThangBan", "TongBan", "TongVe", "TongTien"};
        return this.getListOfArray(DOANHTHUTHANG_SQL, cols);
    }

    public List<Object[]> getDoanhThuNam() {
        String[] cols = {"NamBan", "TongBan", "TongVe", "TongTien"};
        return this.getListOfArray(DOANHTHUNAM_SQL, cols);
    }
}
