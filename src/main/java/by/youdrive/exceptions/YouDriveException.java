package by.youdrive.exceptions;

public class YouDriveException extends RuntimeException {

    public static final int CannotCreateOrUpdateDbEntity = 1001;
    public static final int UserIsNotAllowedToChangeField = 1002;
    public static final int ResourceNotFound = 1003;
    public static final int WrongDateOrDateFormat = 1004;
    public static final int WrongArgument = 1005;
    public static final int CannotCreateOAuthClient = 1006;
    public static final int CannotGetOAuthToken = 1007;

    private int status = 0;

    public YouDriveException() {
        super();
    }

    public YouDriveException(String message) {
        super(message);
    }

    public YouDriveException(Throwable cause) {
        super(cause.getLocalizedMessage(), cause);
        if (cause instanceof YouDriveException) {
            this.status = ((YouDriveException) cause).getStatus();
        }
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
