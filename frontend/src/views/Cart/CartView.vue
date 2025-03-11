<template>
  <div class="cart-container">
    <div class="cart-title">
      <h2>옷장</h2>
    </div>
    <div class="tab-section">
      <div
        :class="{ active: activeTab === 'coordinate' }"
        @click="activeTab = 'coordinate'"
      >
        코디상품
      </div>
      <div
        :class="{ active: activeTab === 'single' }"
        @click="activeTab = 'single'"
      >
        단일상품
      </div>
    </div>
    <div class="cart-header">
      <h3>상품 정보</h3>
      <div class="cart-actions">
        <button class="select-delete-button" @click="deleteSelectedItems">
          <img class="icon-delete" :src="iconDelete" alt="삭제" />
          <span>선택삭제</span>
        </button>
      </div>
    </div>
    <!-- 코디 상품 탭 -->
    <div class="cart-item-list" v-if="activeTab === 'coordinate'">
      <div v-if="coordinateItems.length === 0">
        <p>코디상품이 없습니다.</p>
      </div>
      <div
        v-for="item in coordinateItems"
        :key="item.cartId"
        class="cart-item"
        :data-cart-id="item.cartId"
      >
        <div class="item-checkbox">
          <input
            type="checkbox"
            v-model="item.selected"
            class="checkbox-input"
          />
        </div>
        <div class="item-thumbnail">
          <a :href="`/codi/${item.codiId}`">
            <img
              class="thumbnail-img"
              :src="item.thumbnailUrl || '/images/placeholder.png'"
              alt="코디 이미지"
            />
          </a>
        </div>
        <div class="item-description">
          <div class="product-name">{{ item.codiDescription }}</div>
        </div>
        <div class="item-description">
          <div class="product-variant">
            대여일
            <input
              type="date"
              v-model="item.rentalStartDate"
              @change="updateRentalDate(item, 'start')"
            />
            ~ 반납일
            <input
              type="date"
              v-model="item.rentalEndDate"
              @change="updateRentalDate(item, 'end')"
            />
          </div>
        </div>
        <div class="item-price-box">
          <div class="item-price">
            <strong>{{ formatPrice(getCodiTotalPrice(item)) }}원</strong>
          </div>
        </div>
        <button class="delete-button" @click="deleteItem(item)">
          <span>삭제</span>
        </button>
      </div>
    </div>
    <!-- 단일 상품 탭 -->
    <div class="cart-item-list2" v-if="activeTab === 'single'">
      <div v-if="singleItems.length === 0">
        <p>단일상품이 없습니다.</p>
      </div>
      <div
        v-for="item in singleItems"
        :key="item.cartId"
        class="cart-item"
        :data-cart-id="item.cartId"
      >
        <div class="item-checkbox">
          <input
            type="checkbox"
            v-model="item.selected"
            class="checkbox-input"
          />
        </div>
        <div class="item-thumbnail">
          <a :href="`/product/${item.productId}`">
            <img
              class="thumbnail-img"
              :src="item.thumbnailUrl || '/images/placeholder.png'"
              alt="썸네일"
            />
          </a>
        </div>
        <div class="item-description">
          <div class="brand">{{ item.brandName }}</div>
          <div class="product-name">{{ item.productName }}</div>
        </div>
        <div class="item-description">
          <div class="product-variant">
            <select
              v-model.number="item.quantity"
              @change="updateItemQuantity(item)"
              id="quantity"
              class="quantity-input"
            >
              <option v-for="n in 10" :key="n" :value="n - 1">
                {{ n - 1 }}
              </option>
            </select>
          </div>
        </div>
        <div class="item-price-box">
          <div class="item-price">
            <strong>{{ formatPrice(getProductTotalPrice(item)) }}원</strong>
          </div>
        </div>
        <button class="delete-button" @click="deleteItem(item)">
          <span>삭제</span>
        </button>
      </div>
    </div>
    <!-- 선택된 상품 섹션 -->
    <div class="cart-header">
      <h3>선택된 상품</h3>
    </div>
    <div class="selected-items-section">
      <div v-if="selectedItems.length === 0">
        <p>선택된 상품이 없습니다.</p>
      </div>
      <div
        v-else
        v-for="item in selectedItems"
        :key="item.cartId"
        class="cart-item selected-item"
      >
        <div class="item-thumbnail">
          <a
            :href="
              item.productId
                ? `/product/${item.productId}`
                : `/codi/${item.codiId}`
            "
          >
            <img
              class="thumbnail-img"
              :src="item.thumbnailUrl || '/images/placeholder.png'"
              alt="상품 이미지"
            />
          </a>
        </div>
        <div class="item-description">
          <div class="product-name">
            {{ item.productName || item.codiDescription }}
          </div>
        </div>
        <div class="item-description">
          <div class="item-description">
            <div v-if="item.type === 'single'" class="product-variant">
              <span>{{ item.quantity }}개</span>
            </div>
            <div v-else-if="item.type === 'coordinate'" class="product-variant">
              대여일: {{ item.rentalStartDate }} ~ 반납일:
              {{ item.rentalEndDate }}
            </div>
          </div>
          <div class="item-price-box">
            <div class="item-price">
              <strong
                >{{
                  formatPrice(
                    item.type === "coordinate"
                      ? getCodiTotalPrice(item)
                      : getProductTotalPrice(item)
                  )
                }}원</strong
              >
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 장바구니 요약 및 결제 -->
    <div class="cart-summary">
      <div class="summary-header">
        <h3>최종 결제 예상 금액</h3>
      </div>
      <div class="summary-details">
        <div class="summary-item">
          <span>대여코디 개수</span>
          <strong>{{ totalSelectedCoordinateQuantity }}개</strong>
        </div>
        <div class="summary-item">
          <span>구매상품 개수</span>
          <strong>{{ totalSelectedSingleQuantity }}개</strong>
        </div>
        <div class="summary-item">
          <span>총 개수</span>
          <strong>{{ totalQuantity }}개</strong>
        </div>
        <div class="summary-item">
          <span>총 금액</span>
          <strong>{{ formatPrice(totalFinalPrice) }}원</strong>
        </div>
      </div>
      <div class="checkout-button-container">
        <button class="checkout-button" @click="checkout">
          <span
            >총 {{ totalQuantity }}개 / {{ formatPrice(totalFinalPrice) }}원
            주문하기</span
          >
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "@/api/axios";
import { getDownloadURL, ref as firebaseRef } from "firebase/storage";
import { firebaseStorage } from "@/firebase/firebaseConfig";
import "@/assets/styles/cart.css";
import iconDelete from "@/assets/icons/icon-delete.svg";
import { useOrderStore } from "@/stores/orderStore";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";

const authStore = useAuthStore();
const jwtToken = authStore.token;
const userId = authStore.user?.userId;

console.log("JWT Token:", jwtToken);
console.log("User ID from AuthStore:", userId);

const activeTab = ref("coordinate");
const coordinateItems = ref([]);
const singleItems = ref([]);
const orderStore = useOrderStore();

// Firebase에서 이미지 불러오기
const fetchImageForItem = async (item) => {
  let storagePath;
  if (item.codiId) {
    storagePath = `lookkit/codi/0${item.codiId}/${item.codiId}_thumbnail.webp`;
  } else if (item.productId) {
    storagePath = `lookkit/products/0${item.productId}/${item.productId}_thumbnail.webp`;
  }

  console.log("이미지 경로 확인:", storagePath);
  try {
    const imageRef = firebaseRef(firebaseStorage, storagePath);
    const url = await getDownloadURL(imageRef);
    item.thumbnailUrl = url;
  } catch (error) {
    console.error(`이미지 가져오기 실패: ${storagePath}`, error);
    item.thumbnailUrl = null;
  }
};

// 장바구니 데이터 불러오기
const fetchCartItems = async () => {
  try {
    console.log("Fetching cart items for user ID:", userId); // 추가된 로그
    const API_BASE_URL = "/api/cart";
    const response = await axios.get(`${API_BASE_URL}/items?userId=${userId}`, {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    });
    console.log("Fetched cart items response:", response.data);
    const items = response.data;
    for (const item of items) {
      if (item.codiId) {
        item.type = "coordinate";
        await fetchImageForItem(item);
        coordinateItems.value.push({ ...item, selected: true });
      } else if (item.productId) {
        item.type = "single";
        await fetchImageForItem(item);
        singleItems.value.push({ ...item, selected: true });
      }
    }
  } catch (error) {
    console.error("장바구니 데이터를 불러오는 데 실패했습니다.", error);
  }
};

// 초기 장바구니 데이터를 불러옵니다.
fetchCartItems();

// 가격 포맷팅
// const formatPrice = (price) => {
//   return price ? price.toLocaleString() : '0';
// };

// 수량에 따라 단일 상품 가격 계산
const getProductTotalPrice = (item) => {
  return item.productPrice * (item.quantity || 1);
};

// 대여일에 따라 코디 상품의 총 가격 계산
const getCodiTotalPrice = (item) => {
  if (item.rentalStartDate && item.rentalEndDate) {
    const startDate = new Date(item.rentalStartDate);
    const endDate = new Date(item.rentalEndDate);
    const diffTime = endDate - startDate;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    // 기본 3일 가격 + 추가 요금 계산
    if (diffDays > 3) {
      return item.codiPrice + (diffDays - 3) * 10000;
    }
    return item.codiPrice; // 기본 3일 가격
  }
  return item.codiPrice;
};

// 선택된 아이템 삭제
const deleteSelectedItems = async () => {
  const itemsToDelete = selectedItems.value;
  if (itemsToDelete.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  try {
    const API_BASE_URL = "http://localhost:8081/api/cart";
    await axios.post(`${API_BASE_URL}/delete/bulk`, {
      cartIds: itemsToDelete.map((item) => item.cartId),
    });
    coordinateItems.value = coordinateItems.value.filter((i) => !i.selected);
    singleItems.value = singleItems.value.filter((i) => !i.selected);
  } catch (error) {
    alert("선택된 상품을 삭제하는 데 실패했습니다.");
    console.error("Error deleting selected items:", error);
  }
};

// 개별 아이템 삭제
const deleteItem = async (item) => {
  try {
    const API_BASE_URL = "http://localhost:8081/api/cart";
    await axios.post(`${API_BASE_URL}/delete`, { cartId: item.cartId });
    if (item.type === "coordinate") {
      coordinateItems.value = coordinateItems.value.filter(
        (i) => i.cartId !== item.cartId
      );
    } else {
      singleItems.value = singleItems.value.filter(
        (i) => i.cartId !== item.cartId
      );
    }
  } catch (error) {
    alert("아이템을 삭제하는 데 실패했습니다.");
    console.error("Error deleting item:", error);
  }
};

// // 선택된 아이템 계산
// const selectedItems = computed(() => {
// const selected = [...coordinateItems.value, ...singleItems.value].filter(item => item.selected);
// console.log("Selected items in cart.vue:", selected);
// return selected;
// });

// // 총 수량 계산
// const totalQuantity = computed(() => {
//   return selectedItems.value.reduce((acc, item) => acc + (item.quantity || 1), 0);
// });

// // 최종 결제 금액 계산
// const totalFinalPrice = computed(() => {
// return selectedItems.value.reduce((acc, item) => {
//   if (item.type === 'coordinate') {
//     return acc + getCodiTotalPrice(item);
//   } else {
//     return acc + getProductTotalPrice(item);
//   }
// }, 0);
// });

// 선택된 아이템 계산
const selectedItems = computed(() => {
  const selected = [...coordinateItems.value, ...singleItems.value].filter(
    (item) => item.selected
  );
  console.log("Selected items in cart.vue:", selected);
  return selected;
});

// 선택된 코디 상품 개수 계산
const totalSelectedCoordinateQuantity = computed(() => {
  return selectedItems.value
    .filter((item) => item.type === "coordinate")
    .reduce((acc, item) => acc + (item.quantity || 1), 0);
});

// 선택된 단일 상품 개수 계산
const totalSelectedSingleQuantity = computed(() => {
  return selectedItems.value
    .filter((item) => item.type === "single")
    .reduce((acc, item) => acc + (item.quantity || 1), 0);
});

// 총 선택된 상품 개수 계산 (코디 상품과 단일 상품 합산)
const totalQuantity = computed(() => {
  return (
    totalSelectedCoordinateQuantity.value + totalSelectedSingleQuantity.value
  );
});

// 최종 결제 금액 계산
const totalFinalPrice = computed(() => {
  return selectedItems.value.reduce((acc, item) => {
    if (item.type === "coordinate") {
      return acc + getCodiTotalPrice(item);
    } else {
      return acc + getProductTotalPrice(item);
    }
  }, 0);
});

// 가격 포맷팅
const formatPrice = (price) => {
  return price ? price.toLocaleString() : "0";
};

// 수량 업데이트
const updateItemQuantity = async (item) => {
  if (item.quantity <= 0) {
    alert("수량은 1 이상이어야 합니다.");
    item.quantity = 1;
    return;
  }
  try {
    const API_BASE_URL = "http://localhost:8081/api/cart";
    await axios.post(`${API_BASE_URL}/update?userId=${userId}`, {
      cartId: item.cartId,
      quantity: item.quantity,
    });
  } catch (error) {
    alert("수량을 변경하는 데 실패했습니다.");
    console.error("Error updating quantity:", error);
  }
};

// 대여일 업데이트
const updateRentalDate = async (item, dateType) => {
  try {
    const API_BASE_URL = "http://localhost:8081/api/cart";
    await axios.post(`${API_BASE_URL}/update?userId=${userId}`, {
      cartId: item.cartId,
      rentalStartDate: dateType === "start" ? item.rentalStartDate : undefined,
      rentalEndDate: dateType === "end" ? item.rentalEndDate : undefined,
    });
  } catch (error) {
    alert("대여일을 변경하는 데 실패했습니다.");
    console.error("Error updating rental date:", error);
  }
};

const router = useRouter();

const checkout = async () => {
  if (selectedItems.value.length === 0) {
    alert("선택된 상품이 없습니다.");
    return;
  }

  // 구매하기 버튼을 클릭한 경우에만 데이터 저장
  orderStore.$patch({
    selectedItems: selectedItems.value.map((item) => ({
      itemId: item.productId || item.codiId,
      itemName: item.productName || item.codiDescription,
      brandName: item.brandName || "",
      type: item.type,
      quantity: item.quantity || 1,
      startDate: item.rentalStartDate || null,
      endDate: item.rentalEndDate || null,
      totalPrice: item.productPrice || item.codiPrice,
    })),
    totalFinalPrice: totalFinalPrice.value, // Cart에서 계산된 총 금액을 저장
  });

  orderStore.setSelectedItems(selectedItems.value, totalFinalPrice.value);

  // 데이터를 로컬 스토리지에 저장
  localStorage.setItem("orderStore", JSON.stringify(orderStore.selectedItems));

  // 로그 출력
  console.log(
    "Order Store before navigating to order page:",
    orderStore.selectedItems
  );

  // 결제 페이지로 이동
  router.push("/order");
};
</script>
