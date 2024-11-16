package synerjs.lookkit2nd.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    @Query("SELECT new synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO(" +
            "w.product.productId, w.product.productName, w.product.brandName, " +
            "w.product.productPrice, w.product.productThumbnail, COUNT(w)) " +
            "FROM Wishlist w " +
            "WHERE w.userId = :userId " +
            "GROUP BY w.product.productId, w.product.productName, w.product.brandName, " +
            "w.product.productPrice, w.product.productThumbnail")
    List<WishlistResponseDTO> getWishlistByUserId(@Param("userId") Long userId);

}
