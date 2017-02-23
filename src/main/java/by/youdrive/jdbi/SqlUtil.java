package by.youdrive.jdbi;

import org.jetbrains.annotations.NotNull;

public class SqlUtil {

    @NotNull
    public static String getOrder(String order) {
        if ("desc".equalsIgnoreCase(order)) {
            return "desc";
        }
        return "asc";
    }
}
