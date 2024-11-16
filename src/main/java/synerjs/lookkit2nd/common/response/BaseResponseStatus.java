package synerjs.lookkit2nd.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum BaseResponseStatus {
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),

    /* 문의하기 */
    INQUIRY_NOT_FOUNT(false, HttpStatus.NOT_FOUND.value(), "문의를 찾을 수 없습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

}
