import axios from "axios";
import { useAuthStore } from "@/stores/authStore";

const authStore = useAuthStore();
// 좋아요 버튼 클릭 함수
export const toggleLike = async (wish) => {
  try {
    if (wish.isLiked) {
      await axios.delete(
        `http://localhost:8081/api/mypage/wishlist/${wish.wishlistId}`
      );
      wish.isLiked = false;
      wish.likeCount--;
    } else {
      const response = await axios.post(
        `http://localhost:8081/api/mypage/wishlist/${authStore.user.userId}`,
        {
          productId: wish.productId,
          codiId: wish.codiId,
        }
      );
      wish.isLiked = true;
      wish.likeCount = response.data.data.likeCount;
      wish.wishlistId = response.data.data.wishlistId;
    }
  } catch (error) {
    console.error("Error toggling like:", error);
  }
};
