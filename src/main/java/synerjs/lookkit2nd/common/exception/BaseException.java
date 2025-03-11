package synerjs.lookkit2nd.common.exception;

import lombok.Getter;
import synerjs.lookkit2nd.common.response.BaseResponseStatus;

@Getter
public class BaseException extends RuntimeException {
    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
