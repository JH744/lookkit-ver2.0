package synerjs.lookkit2nd.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    @Query("SELECT new synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO(" +
            "w.wishlistId, " +
            "p.productId, " +
            "p.productName, " +
            "p.brandName, " +
            "p.productPrice, " +
            "p.productThumbnail, " +
            "(SELECT COUNT(w2) FROM Wishlist w2 WHERE w2.product.productId = p.productId)" +
            ") " +
            "FROM Wishlist w " +
            "JOIN w.product p " +
            "WHERE w.userId = :userId " +
            "GROUP BY p.productId, p.productName, p.brandName, p.productPrice, p.productThumbnail")
    List<WishlistResponseDTO> getWishlistByUserId(@Param("userId") Long userId);

    @Query("SELECT new synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO(" +
            "w.wishlistId, " +
            "w.product.productId, " +
            "w.product.productName, " +
            "w.product.brandName, " +
            "w.product.productPrice, " +
            "w.product.productThumbnail, " +
            "(SELECT COUNT(w2) FROM Wishlist w2 WHERE w2.product.productId = w.product.productId)" +
            ") " +
            "FROM Wishlist w " +
            "JOIN w.product p " +
            "WHERE w.userId = :userId AND w.product.productId = :productId " +
            "GROUP BY w.product.productId, w.product.productName, w.product.brandName, w.product.productPrice, w.product.productThumbnail")
    WishlistResponseDTO getWishByUserId(@Param("userId") Long userId, @Param("productId") Long productId);

    Optional<Wishlist> findByUserIdAndProduct_ProductId(Long userId, Long productId);
}
