package synerjs.lookkit2nd.codi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodiDTO {

    private Long codiId;
    private String codiName;
    private String codiDescription;
    private String codiThumbnail;
    private Integer codiPrice;
    private Integer quantity; // quantity 필드 추가

    public CodiDTO(Long codiId, String codiName, String codiDescription, String codiThumbnail, Integer codiPrice, Integer quantity) {
        this.codiId = codiId;
        this.codiName = codiName;
        this.codiDescription = codiDescription;
        this.codiThumbnail = codiThumbnail;
        this.codiPrice = codiPrice;
        this.quantity = (quantity != null) ? quantity : 1; // null일 경우 기본값으로 1 설정
    }
}
