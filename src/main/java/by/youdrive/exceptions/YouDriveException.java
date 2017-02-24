package by.youdrive.exceptions;

public class YouDriveException extends RuntimeException {

    public static final int CannotCreateOrUpdateDbEntity = 1001;
    static public final int UserIsNotAllowedToChangeField = 1002;
    static public final int ResourceNotFound = 1003;
    static public final int WrongDateOrDateFormat = 1004;
    static public final int WrongArgument = 1005;
    static public final int CannotCreateOAuthClient = 1006;
    static public final int CannotGetOAuthToken = 1007;

    private int status = 0;

    public YouDriveException() {
        super();
    }

    public YouDriveException(String message) {
        super(message);
    }

    public YouDriveException(Throwable cause) {
        super(cause.getLocalizedMessage(), cause);
    }

    public YouDriveException(int status, String message) {
        super(message);
        this.status = status;
    }

    protected YouDriveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }
}
