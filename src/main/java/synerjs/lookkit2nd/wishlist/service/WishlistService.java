package synerjs.lookkit2nd.wishlist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.wishlist.repository.WishlistRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository repository;

//    public List<WishlistResponseDTO> getWishlist(Long userId) {
//        return repository.findAllByUserId(userId);
//    }
}
