package synerjs.lookkit2nd.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import synerjs.lookkit2nd.product.Product;

import java.util.List;

@Getter
@AllArgsConstructor
public class CodiProductDTO {
    private Long codiId;
    private String codiName;
    private String codiThumbnail;
    private Integer codiPrice;
    private List<Product> products;  // 상품 리스트
}