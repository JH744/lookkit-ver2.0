package synerjs.lookkit2nd.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImageDTO {
    private Long reviewImageId;
    private Long reviewId;
    private String imagePath;
}