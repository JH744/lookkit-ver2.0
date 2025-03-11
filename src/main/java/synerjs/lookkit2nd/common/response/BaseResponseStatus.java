package synerjs.lookkit2nd.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum BaseResponseStatus {
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),
    USER_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 회원입니다."),

    /* 상품 */
    PRODUCT_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(),"해당 상품이 존재하지 않습니다."),

    /* 코디 */
    CODI_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(),"해당 코디가 존재하지 않습니다."),

    /* 문의하기 */
    INQUIRY_NOT_FOUNT(false, HttpStatus.NOT_FOUND.value(), "문의를 찾을 수 없습니다."),

    /* 위시리스트 */
    WISH_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "선택한 위시리스트의 상품을 찾을 수 없습니다."),
    WISH_ALREADY_EXISTS(false, HttpStatus.CONFLICT.value(),"이미 찜한 상품입니다."),

    /* 주문 */
    ORDER_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "해당 주문은 존재하지 않습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

}
