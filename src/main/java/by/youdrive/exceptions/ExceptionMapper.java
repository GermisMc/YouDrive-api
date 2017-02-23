package by.youdrive.exceptions;

import by.youdrive.commons.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    private static Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);
    private static final Map<Integer, Response.Status> statuses = new HashMap<Integer, Response.Status>() {{
        put(0, Response.Status.INTERNAL_SERVER_ERROR);
        put(YouDriveException.CannotCreateOrUpdateDbEntity, Response.Status.INTERNAL_SERVER_ERROR);
        put(YouDriveException.ResourceNotFound, Response.Status.NOT_FOUND);
        put(YouDriveException.UserIsNotAllowedToChangeField, Response.Status.FORBIDDEN);
        put(YouDriveException.WrongArgument, Response.Status.BAD_REQUEST);
        put(YouDriveException.WrongDateOrDateFormat, Response.Status.BAD_REQUEST);
        put(YouDriveException.CannotCreateOAuthClient, Response.Status.INTERNAL_SERVER_ERROR);
    }};

    @Override
    public Response toResponse(Exception exception) {
        logger.error(exception.getLocalizedMessage(), exception);

        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        else if (exception instanceof YouDriveException) {
            YouDriveException youDriveException = (YouDriveException) exception;
            return Response.status(statuses.get(youDriveException.getStatus()))
                    .entity(new ErrorMessages(youDriveException.getLocalizedMessage()))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessages(Messages.AnUnexpectedErrorHasOccurred))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }
}
