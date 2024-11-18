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

    public CodiDTO(Long codiId, String codiName, String codiDescription, String codiThumbnail, Integer codiPrice) {
        this.codiId = codiId;
        this.codiName = codiName;
        this.codiDescription = codiDescription;
        this.codiThumbnail = codiThumbnail;
        this.codiPrice = codiPrice;
    }
}
