package synerjs.lookkit2nd.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodiProductDTO {
    private Long codiId;
    private String codiName;
    private String codiThumbnail;
    private Integer codiPrice;

    private Long productId;
    private String productName;
    private Integer productPrice;
    private String productThumbnail;
    private String brandName;
}