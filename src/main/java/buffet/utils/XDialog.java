package buffet.utils;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author duyplus
 */
public class XDialog {

    /*
     * Hiển thị thông báo cho người dùng
     */
    public static void alert(Component parent, String message) {
        UIManager.put("OptionPane.messageFont", new Font("Be Vietnam Pro", Font.PLAIN, 16));
        JOptionPane.showMessageDialog(parent, message, "Hệ thống quản lý nhà hàng buffet",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * Hiển thị thông báo và yêu cầu người dùng xác nhận
     */
    public static boolean confirm(Component parent, String message) {
        UIManager.put("OptionPane.messageFont", new Font("Be Vietnam Pro", Font.PLAIN, 16));
        int result = JOptionPane.showConfirmDialog(parent, message, "Hệ thống quản lý nhà hàng buffet",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    /*
     * Hiển thị thông báo yêu cầu nhập dữ liệu
     */
    public static String prompt(Component parent, String message) {
        UIManager.put("OptionPane.messageFont", new Font("Be Vietnam Pro", Font.PLAIN, 16));
        return JOptionPane.showInputDialog(parent, message, "Hệ thống quản lý nhà hàng buffet",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
