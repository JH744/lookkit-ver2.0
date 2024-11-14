package synerjs.lookkit2nd.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
//    @Query("SELECT new synerjs.lookkit2nd.wishlist.WishlistResponseDTO(" +
//            "w.product.productId, w.product.productName, w.product.brandName, " +
//            "w.product.price, w.product.thumbnail, COUNT(w)) " +
//            "FROM Wishlist w " +
//            "WHERE w.user.userId = :userId " +
//            "GROUP BY w.product.productId, w.product.productName, w.product.brandName, " +
//            "w.product.price, w.product.thumbnail")
//    List<WishlistResponseDTO> findAllByUserId(@Param("userId") Long userId);

}
