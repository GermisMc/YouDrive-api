package by.youdrive.jdbi;

import by.youdrive.domain.UserEntity;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.exceptions.TransactionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import java.sql.Array;
import java.util.List;

import static by.youdrive.jdbi.SqlUtil.getOrder;

public abstract class YouDriveDAO {

    private DBI dbi;

    @CreateSqlObject
    abstract public UserDAO userDAO();

    public DBI getDbi() {
        return dbi;
    }

    public void setDbi(DBI dbi) {
        this.dbi = dbi;
    }

    public List<UserEntity> getUsers(Array userEmail, String orderBy, String order, int limit, int offset) {
        String queryTemplate = "select user.* from user where (_filter_by_user_email_) _order_by_ limit :limit)";

        if (orderBy != null) {
            switch (orderBy) {
                case "firstName":
                    orderBy = "first_name";
                    break;

                case "lastName":
                    orderBy = "last_name";
                    break;

                case "email":
                    orderBy = "email";
                    break;

                case "admin":
                    orderBy = "admin";
                    break;

                case "locale":
                    orderBy = "locale";
                    break;

                default:
                    orderBy = null;
            }
        }

        String query = queryTemplate
                .replaceAll("_filter_by_user_email_", userEmail == null ? "true" : "user.email = :userEmail")
                .replaceAll("_order_by_", orderBy == null ? "" : "order by " + orderBy + " " + getOrder(order));

        Handle h = null;
        try {
            h = dbi.open();

            List<UserEntity> users = h.createQuery(query)
                    .bind("userEmail", userEmail)
                    .bind("limit", limit)
                    .bind("offset", offset)
                    .map(new UserDAO.Mapper()).list();

            h.close();
            return users;
        } catch (Exception e) {
            if (h != null) {
                h.close();
            }
            throw new TransactionException(e);
        }
    }
}
