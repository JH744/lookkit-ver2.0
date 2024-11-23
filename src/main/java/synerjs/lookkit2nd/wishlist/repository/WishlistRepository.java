package synerjs.lookkit2nd.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "c.codiId, " +
            "c.codiName, " +
            "c.codiDescription, " +
            "c.codiPrice, " +
            "c.codiThumbnail, " +
            "(CASE " +
            "   WHEN p.productId IS NOT NULL THEN (SELECT COUNT(w2) FROM Wishlist w2 WHERE w2.product.productId = p.productId) " +
            "   WHEN c.codiId IS NOT NULL THEN (SELECT COUNT(w2) FROM Wishlist w2 WHERE w2.codi.codiId = c.codiId) " +
            "   ELSE 0 END) " +
            ") " +
            "FROM Wishlist w " +
            "LEFT JOIN w.product p " +
            "LEFT JOIN w.codi c " +
            "WHERE w.userId = :userId")
    List<WishlistResponseDTO> getWishlistByUserId(@Param("userId") Long userId);


    @Query("SELECT new synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO(" +
            "w.wishlistId, " +
            "p.productId, " +
            "p.productName, " +
            "p.brandName, " +
            "p.productPrice, " +
            "p.productThumbnail, " +
            "c.codiId, " +
            "c.codiName, " +
            "c.codiDescription, " +
            "c.codiPrice, " +
            "c.codiThumbnail, " +
            "(CASE " +
            "   WHEN p.productId IS NOT NULL THEN (SELECT COUNT(w2) FROM Wishlist w2 WHERE w2.product.productId = p.productId) " +
            "   WHEN c.codiId IS NOT NULL THEN (SELECT COUNT(w2) FROM Wishlist w2 WHERE w2.codi.codiId = c.codiId) " +
            "   ELSE 0 END) " +
            ") " +
            "FROM Wishlist w " +
            "LEFT JOIN w.product p " +
            "LEFT JOIN w.codi c " +
            "WHERE w.userId = :userId " +
            "AND (:productId IS NULL OR p.productId = :productId) " +
            "AND (:codiId IS NULL OR c.codiId = :codiId)")
    WishlistResponseDTO getWishByUserId(@Param("userId") Long userId, @Param("productId") Long productId, @Param("codiId") Long codiId);

    Optional<Wishlist> findByUserIdAndProduct_ProductId(Long userId, Long productId);


    @Query(value = "SELECT PRODUCT_ID FROM wishlist " +
            "WHERE USER_ID = :userId AND PRODUCT_ID IN (:itemIds)", nativeQuery = true)
    List<Long> findAllItemIdsInWishlist(@Param("userId") Long userId, @Param("itemIds") List<Long> itemIds);


    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.userId = :userId AND w.product.id = :productId")
    void deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}