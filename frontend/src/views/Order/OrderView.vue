<template>
  <div class="order-container">
    <div class="order-title">
      <h2>결제/주문</h2>
    </div>
    <section class="recipient-info-section">
      <header class="section-header">
        <h2 class="section-title">받는 분 정보</h2>
      </header>
      <div class="info-table">
        <div class="info-row">
          <span class="label-title">배송지 정보</span>
          <button class="link-text" @click="showAddAddressModal = true">
            배송지 등록하기
          </button>
          <AddAddressView
            v-if="showAddAddressModal"
            @close="showAddAddressModal = false"
            @addressRegistered="updateAddress"
          />
        </div>
        <div class="info-row">
          <div class="info-label">배송지명</div>
          <input
            type="text"
            v-model="address.addressName"
            class="input-field"
            readonly
          />
        </div>
        <div class="info-row">
          <div class="info-label">받는 사람</div>
          <input
            type="text"
            v-model="address.recipientName"
            class="input-field"
            readonly
          />
        </div>
        <div class="info-row">
          <div class="info-label">휴대폰 번호</div>
          <input
            type="text"
            v-model="address.phoneNumber"
            class="input-field"
            readonly
          />
        </div>
        <div class="info-row">
          <div class="info-label">주소</div>
          <input
            type="text"
            v-model="address.fullAddress"
            class="input-field"
            readonly
          />
        </div>
      </div>
      <div class="info-row2">
        <div class="info-label">배송 메모</div>
        <div class="info-data">
          <div class="memo-list">
            <label
              class="memo-item"
              v-for="(memo, index) in memoOptions"
              :key="index"
            >
              <input
                type="radio"
                name="memo-option"
                class="memo-checkbox"
                :value="memo"
                v-model="selectedMemo"
              />
              <span class="memo-text">{{ memo }}</span>
            </label>
          </div>
        </div>
      </div>
    </section>
    <section class="product-info-section">
      <header class="section-header">
        <h2 class="section-title">상품 정보</h2>
      </header>

      <!-- Cart에서 저장된 selectedItems 출력 -->
      <div v-if="orderStore.selectedItems.length > 0">
        <div
          v-for="item in orderStore.selectedItems"
          :key="item.itemId"
          class="item-description"
        >
          <img
            class="item-thumbnail"
            :src="item.thumbnailUrl || '/images/placeholder.png'"
            alt="썸네일"
          />
          <div class="item-description2">
            <div
              v-if="item.type === 'product' && item.brandName"
              class="brand-name"
            >
              {{ item.brandName }}
            </div>
            <div v-if="item.type === 'product'" class="product-name">
              {{ item.itemName }}
            </div>

            <div v-else-if="item.type === 'codi'" class="product-name">
              {{ item.itemName }}
            </div>
          </div>

          <div v-if="item.type === 'product'" class="product-variant">
            {{ item.quantity }}개
          </div>

          <div v-else-if="item.type === 'codi'" class="product-variant">
            대여일 {{ item.startDate }} ~ 반납일 {{ item.endDate }}
            <div class="rental-period">
              대여기간 {{ calculateRentalDays(item.startDate, item.endDate) }}일
            </div>
          </div>
          <div class="price">
            <div class="item-price">{{ formatPrice(item.totalPrice) }}원</div>
            <div
              v-if="item.type === 'product' || item.type === 'codi'"
              class="adjusted-price"
            >
              {{ formatPrice(calculateAdjustedPrice(item)) }}원
            </div>
          </div>
        </div>
      </div>

      <!-- 단일 상품 정보 출력 -->
      <div v-else-if="orderItem">
        <!-- Product 정보 -->
        <div v-if="orderItem.type === 'product'">
          <div class="item-description">
            <img
              class="item-thumbnail"
              :src="orderItem.thumbnailUrl || '/images/placeholder.png'"
              alt="썸네일"
            />
            <div class="item-description2">
              <div class="brand-name">{{ orderItem.brandName }}</div>
              <div class="product-name">{{ orderItem.itemName }}</div>
            </div>
            <div class="product-variant">{{ orderItem.quantity }}개</div>
            <div class="price">
              <div class="item-price">{{ formatPrice(orderItem.price) }}원</div>
              <div class="adjusted-price">
                {{ formatPrice(orderItem.totalPrice) }}원
              </div>
            </div>
          </div>
        </div>
        <!-- Codi 정보 -->
        <div v-else-if="orderItem.type === 'codi'">
          <div class="item-description">
            <img
              class="item-thumbnail"
              :src="orderItem.thumbnailUrl || '/images/placeholder.png'"
              alt="썸네일"
            />
            <div class="product-name">{{ orderItem.itemName }}</div>
            <div class="product-variant">
              대여일 {{ orderItem.startDate }} ~반납일 {{ orderItem.endDate }}
            </div>
            <div class="product-variant2">대여기간 {{ rentalDays }}일</div>
            <div class="price">
              <div class="item-price">{{ formatPrice(orderItem.price) }}원</div>
              <div class="adjusted-price">
                {{ formatPrice(orderItem.totalPrice) }}원
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section class="payment-frame">
      <header class="section-header2">
        <h2 class="section-title">결제수단 선택</h2>
      </header>
      <div class="payment-method-list">
        <label class="payment-method-item">
          <input
            type="radio"
            name="payment-method"
            value="card"
            v-model="selectedPaymentMethod"
          />
          <img
            src="@/assets/icons/card-image.png"
            alt="Credit/Debit Card"
            class="method-icon"
          />
          <span class="method-title">신용/체크카드</span>
        </label>
      </div>
      <header class="section-header2">
        <h2 class="section-title">최종 결제금액</h2>
      </header>
      <div class="summary-row">
        <span class="summary-title">최종 결제금액</span>
        <span class="summary-value">{{ formatPrice(totalPrice) }}원</span>
      </div>
    </section>
    <section class="total-payment-section">
      <div class="agreement-list">
        <label
          class="agreement-item"
          v-for="(agreement, index) in agreements"
          :key="index"
        >
          <input type="checkbox" v-model="agreements[index].checked" />
          <span class="agreement-text">{{ agreement.text }}</span>
        </label>
      </div>
    </section>
    <button class="pay-button" @click="processPayment">
      총 {{ totalQuantity }}개 / {{ formatPrice(totalPrice) }}원 결제하기
    </button>
  </div>

  <div v-if="confirmModalStore.isModalVisible" class="modal">
    <div class="modal-content">
      <h3>{{ confirmModalStore.modalActionMessage }}</h3>
      <p>{{ confirmModalStore.questionMessage }}</p>
      <div class="modal-actions">
        <button @click="confirmModalStore.confirmCallback">확인</button>
        <button @click="confirmModalStore.closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, onBeforeUnmount } from "vue";
import { useRoute, useRouter } from "vue-router";
import "@/assets/styles/order.css";
import { useOrderStore } from "@/stores/orderStore";
import AddAddressView from "@/views/Order/AddAddressView.vue";
import { getDownloadURL, ref as firebaseRef } from "firebase/storage";
import { firebaseStorage } from "@/firebase/firebaseConfig";
import { useAuthStore } from "@/stores/authStore";
import { useConfirmModalStore } from "@/stores/modalStore";
const baseURL = import.meta.env.VITE_APP_BASE_URL;
const confirmModalStore = useConfirmModalStore();
const authStore = useAuthStore();
const userId = authStore.user?.userId;

const orderStore = useOrderStore();
const orderItem = ref(null);
const route = useRoute();

// Firebase에서 이미지 불러오기
const fetchImageForItem = async (item) => {
  let storagePath;
  if (item.type === "codi") {
    storagePath = `lookkit/codi/0${item.itemId}/${item.itemId}_thumbnail.webp`;
  } else if (item.type === "product") {
    storagePath = `lookkit/products/0${item.itemId}/${item.itemId}_thumbnail.webp`;
  }

  console.log("이미지 경로 확인:", storagePath);
  try {
    const imageRef = firebaseRef(firebaseStorage, storagePath);
    const url = await getDownloadURL(imageRef);
    item.thumbnailUrl = url;
    if (item === orderItem.value) {
      orderItem.value.thumbnailUrl = url;
    }
  } catch (error) {
    console.error(`이미지 가져오기 실패: ${storagePath}`, error);
    item.thumbnailUrl = "/images/placeholder.png";
  }
};

// 주문 페이지로 넘어온 단일 상품 정보 (URL에서 가져온 파라미터로 설정)
onMounted(async () => {
  const params = route.query;
  if (params.itemId) {
    orderItem.value = {
      itemId: params.itemId,
      itemName: params.itemName,
      brandName: params.brandName,
      quantity: parseInt(params.quantity, 10) || 1,
      startDate: params.startDate,
      endDate: params.endDate,
      price: parseInt(params.price, 10),
      totalPrice: parseInt(params.totalPrice, 10),
      type: params.itemType || "product",
    };
    // 이미지 URL 설정
    await fetchImageForItem(orderItem.value);
  }

  // Firebase에서 이미지 불러오기 (Cart에서 가져온 selectedItems)
  for (const item of orderStore.selectedItems) {
    await fetchImageForItem(item);
  }
});

// 최종 가격을 계산하는 함수
const calculateAdjustedPrice = (item) => {
  if (item.type === "product") {
    // product 타입일 경우 수량에 따른 가격 계산
    return item.totalPrice * (item.quantity || 1);
  } else if (item.type === "codi") {
    // codi 타입일 경우 대여 기간에 따른 가격 계산
    const rentalDays = calculateRentalDays(item.startDate, item.endDate);
    const basePrice = item.totalPrice;

    // 기본 대여 기간(예: 3일)을 초과하는 경우 추가 요금 계산
    const additionalDays = rentalDays > 3 ? rentalDays - 3 : 0;
    const additionalFee = additionalDays * 10000; // 예를 들어 추가 요금이 하루에 10000원이라고 가정
    return basePrice + additionalFee;
  }
  return item.totalPrice;
};

// 대여기간을 계산하는 함수
const calculateRentalDays = (startDate, endDate) => {
  if (!startDate || !endDate) return 0;
  const start = new Date(startDate);
  const end = new Date(endDate);

  // 대여일 계산: 밀리초 단위의 차이를 일 수로 변환
  const diffTime = end - start;
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

  return diffDays > 0 ? diffDays : 0; // 음수일 경우 0으로 반환
};
const buyerAddressName = ref("");
const buyerRecipientName = ref("");
const buyerPhoneNumber = ref("");
const buyerAddress = ref("");
const selectedMemo = ref("선택 안 함");
const memoOptions = [
  "선택 안 함",
  "부재 시 경비실에 맡겨주세요",
  "부재 시 집 앞에 놔주세요",
  "부재 시 핸드폰으로 연락 바랍니다",
  "배송 전 연락 바랍니다",
];

const selectedPaymentMethod = ref("");
const agreements = ref([
  { text: "주문상품정보 및 서비스 이용약관에 동의", checked: false },
  { text: "구매조건 확인 및 결제 진행 동의(필수)", checked: false },
  { text: "개인정보 제 3자 제공 동의(필수)", checked: false },
  {
    text: "위 주문 내용을 확인하였으며, 결제에 모두 동의합니다",
    checked: false,
  },
]);

onMounted(async () => {
  await nextTick();
  console.log(
    "Selected items in orderStore after mount:",
    orderStore.selectedItems
  );
});

// // 주문 완료 후 데이터 초기화
// const completeOrder = () => {
//   alert('주문이 완료되었습니다.');
//   orderStore.clearSelectedItems();
//   localStorage.removeItem('orderStore');
//   window.location.href = '/order/complete';
// };

// 사용자가 페이지를 떠날 때 selectedItems 초기화
onBeforeUnmount(() => {
  99 / orderStore.clearSelectedItems();
  localStorage.removeItem("orderStore"); // 로컬 스토리지에서도 데이터 삭제
});

//----------------------------------------------------
// orderItem.value = route.params.orderItem;
// const params = route.query;

// // 구매인지 대여인지 구분하여 데이터 할당
// if (params.itemType === 'product') {
// orderItem.value = {
//   itemId: params.itemId,
//   itemName: params.itemName,
//   brandName: params.brandName,
//   quantity: parseInt(params.quantity, 10),
//   price: parseInt(params.price, 10),
//   totalPrice: parseInt(params.totalPrice, 10),
//   type: 'product'
// };
// } else if (params.itemType === 'codi') {
// orderItem.value = {
//   itemId: params.itemId,
//   itemName: params.itemName,
//   startDate: params.startDate,
//   endDate: params.endDate,
//   price: parseInt(params.price, 10),
//   totalPrice: parseInt(params.totalPrice, 10),
//   type: 'codi'
// };
// }

// //  총 수량 계산
// const totalQuantity = computed(() => {
//   if (orderItem.value && orderItem.value.quantity) {
//     return orderItem.value.quantity;
//   }
//   return 1; // Codi의 경우 수량은 1개로 간주
// });

// // 총 대여일 계산
// const rentalDays = computed(() => {
// if (orderItem.value && orderItem.value.startDate && orderItem.value.endDate) {
//   const startDate = new Date(orderItem.value.startDate);
//   const endDate = new Date(orderItem.value.endDate);
//   return (endDate - startDate) / (1000 * 60 * 60 * 24);
// }
// return 0;
// });

// // 총 가격 계산
// const totalPrice = computed(() => {
//   if (orderItem.value) {
//     if (orderItem.value.quantity) {
//       // Product의 경우 수량에 따라 총 가격 계산
//       return orderItem.value.quantity * orderItem.value.price;
//     } else {
//       // Codi의 경우 대여 가격 사용
//       return orderItem.value.price;
//     }
//   }
//   return 0;
// });

//   const formatPrice = (price) => {
//     return price ? price.toLocaleString() : '0';
//   };

//--------------------------------------------------------

// 총 수량 계산
const totalQuantity = computed(() => {
  if (orderItem.value) {
    return orderItem.value.quantity || 1; // 단일 상품일 경우 수량을 직접 계산
  }
  return orderStore.selectedItems.reduce(
    (acc, item) => acc + (item.quantity || 1),
    0
  );
});

// 총 대여 일수 계산 (코디 아이템만 해당)
const rentalDays = computed(() => {
  if (
    orderItem.value &&
    orderItem.value.type === "codi" &&
    orderItem.value.startDate &&
    orderItem.value.endDate
  ) {
    const startDate = new Date(orderItem.value.startDate);
    const endDate = new Date(orderItem.value.endDate);
    return Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));
  }

  return orderStore.selectedItems.reduce((acc, item) => {
    if (item.type === "codi" && item.startDate && item.endDate) {
      const startDate = new Date(item.startDate);
      const endDate = new Date(item.endDate);
      return acc + Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));
    }
    return acc;
  }, 0);
});

// 총 가격 계산
const totalPrice = computed(() => {
  if (orderItem.value) {
    // 단일 주문 아이템이 있는 경우
    if (orderItem.value.type === "product") {
      return orderItem.value.quantity * orderItem.value.price;
    } else if (orderItem.value.type === "codi") {
      const days = rentalDays.value > 3 ? rentalDays.value - 3 : 0; // 3일 초과 시 추가 요금 계산
      const extraFee = days * 10000;
      return orderItem.value.price + extraFee;
    }
  }

  // 장바구니에 여러 아이템이 있는 경우
  return orderStore.totalFinalPrice || 0;
});

// 포맷된 가격 표시
const formatPrice = (price) => {
  return price ? price.toLocaleString() : "0";
};

onMounted(() => {
  // 아임포트 스크립트 동적 로드
  const script = document.createElement("script");
  script.src = "https://cdn.iamport.kr/js/iamport.payment-1.2.0.js";
  script.async = true;
  script.onload = () => {
    console.log("Iamport script loaded successfully");
    // 아임포트 초기화
    const IMP = window.IMP;
    if (IMP) {
      IMP.init("imp40354073");
    }
  };
  document.body.appendChild(script);
});

const router = useRouter();
const processPayment = () => {
  if (!selectedPaymentMethod.value) {
    alert("결제 수단을 선택해주세요.");
    return;
  }

  if (agreements.value.some((agreement) => !agreement.checked)) {
    alert("모든 필수 동의 항목에 체크해주세요.");
    return;
  }

  if (!address.value) {
    alert("배송지를 등록해주세요.");
    return;
  }

  // 아임포트 초기화
  const IMP = window.IMP;
  IMP.init("imp40354073");

  const orderName =
    orderStore.selectedItems.length > 0
      ? orderStore.selectedItems[0].itemName
      : "주문 상품";
  const buyerName = address.value.recipientName;
  const buyerTel = address.value.phoneNumber;
  const buyerAddr = address.value.fullAddress;

  IMP.request_pay(
    {
      pg: "kcp",
      pay_method: "card",
      merchant_uid: "merchant_" + new Date().getTime(),
      name: orderName,
      amount: totalPrice.value,
      buyer_name: buyerName,
      buyer_tel: buyerTel,
      buyer_addr: buyerAddr,
    },
    function (rsp) {
      if (rsp.success) {
        confirmModalStore.showModal(
          "결제 완료",
          `결제가 완료되었습니다.`,
          "",
          "확인",
          () => {
            confirmModalStore.closeModal();
          }
        );

        // 주문 상세 정보 수집
        // const orderDetails = orderStore.selectedItems.map(item => ({
        //   productId: item.type === 'product' ? item.itemId : null, // 단일 상품 ID
        //   codiId: item.type === 'codi' ? item.itemId : null, // 코디 상품 ID
        //   quantity: item.type === 'product' ? item.quantity : 1, // 수량 (상품의 경우 지정, 코디는 1로 설정)
        //   rentalStartDate: item.type === 'codi' ? item.startDate : null, // 대여 시작일 (코디에 해당)
        //   rentalEndDate: item.type === 'codi' ? item.endDate : null, // 대여 종료일 (코디에 해당)
        //   productPrice: item.price // 상품 또는 대여 가격
        // }));

        // 주문 상세 정보 수집
        let orderDetails = [];

        // 장바구니에 있는 상품들 추가
        if (orderStore.selectedItems.length > 0) {
          orderDetails = orderStore.selectedItems.map((item) => ({
            productId: item.type === "product" ? item.itemId : null, // 단일 상품 ID
            codiId: item.type === "codi" ? item.itemId : null, // 코디 상품 ID
            quantity: item.type === "product" ? item.quantity : 1, // 수량 (상품의 경우 지정, 코디는 1로 설정)
            rentalStartDate: item.type === "codi" ? item.startDate : null, // 대여 시작일 (코디에 해당)
            rentalEndDate: item.type === "codi" ? item.endDate : null, // 대여 종료일 (코디에 해당)
            productPrice: item.totalPrice, // 상품 또는 대여 가격
          }));
        }

        // 단일 주문이 있는 경우 추가
        if (orderItem.value) {
          orderDetails.push({
            productId:
              orderItem.value.type === "product"
                ? orderItem.value.itemId
                : null, // 단일 상품 ID
            codiId:
              orderItem.value.type === "codi" ? orderItem.value.itemId : null, // 코디 상품 ID
            quantity:
              orderItem.value.type === "product" ? orderItem.value.quantity : 1, // 수량 (상품의 경우 지정, 코디는 1로 설정)
            rentalStartDate:
              orderItem.value.type === "codi"
                ? orderItem.value.startDate
                : null, // 대여 시작일 (코디에 해당)
            rentalEndDate:
              orderItem.value.type === "codi" ? orderItem.value.endDate : null, // 대여 종료일 (코디에 해당)
            productPrice: orderItem.value.totalPrice, // 상품 또는 대여 가격
          });
        }

        // 최종 paymentData 생성
        const paymentData = {
          userId: userId, // 실제 유저 ID를 사용해야 합니다.
          totalAmount: totalPrice.value,
          orderAddress: buyerAddr,
          orderAddressee: buyerName,
          orderPhone: buyerTel,
          orderComment: selectedMemo.value,
          orderStatus: "결제완료",
          orderDetails: orderDetails,
        };

        // 주문 정보를 서버로 전송
        fetch(baseURL + "/api/order/complete", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(paymentData),
        })
          .then((response) => response.json())
          .then((orderId) => {
            if (!orderId) {
              alert("주문 ID를 받지 못했습니다. 문제가 발생했습니다.");
              return;
            }

            console.log("주문 완료 페이지로 이동, orderId:", orderId);
            // Vue 라우터를 사용해 주문 완료 페이지로 이동합니다.
            if (router) {
              router.push({
                path: "/order/orderComplete",
                query: { orderId: orderId },
              });
            } else {
              console.error("Router 객체가 정의되지 않았습니다.");
            }
          })
          .catch((error) => {
            console.error("결제 실패 또는 서버 문제:", error);
            alert("결제를 완료하는 데 실패했습니다.");
          });
      } else {
        confirmModalStore.showModal(
          "결제 실패",
          `결제에 실패했습니다. 실패사유: ${rsp.error_msg}`,
          "",
          "확인",
          confirmModalStore.closeModal
        );
      }
    }
  );
};

const showAddAddressModal = ref(false);
const address = ref({
  addressName: "",
  recipientName: "",
  phoneNumber: "",
  fullAddress: "",
});

// 주소 업데이트 메서드
const updateAddress = (newAddress) => {
  address.value = newAddress;
};

// onMounted(async () => {
//   try {
//     const jwtToken = authStore.jwt;

//     // JWT 토큰을 이용해 API 요청 보내기
//     const response = await fetch('http://localhost:8081/api/user/address', {
//       method: 'GET',
//       headers: {
//         'Content-Type': 'application/json',
//         'Authorization': `Bearer ${jwtToken}` // JWT 토큰을 헤더에 포함
//       }
//     });

//     if (!response.ok) {
//       throw new Error('배송지 정보를 불러오는 데 문제가 발생했습니다.');
//     }

//     const data = await response.json();
//     console.error('')

//     // 받은 주소 데이터를 Vue ref에 설정
//     address.value = {
//       addressName: data.addressName,
//       recipientName: data.recipientName,
//       phoneNumber: data.phoneNumber,
//       fullAddress: data.fullAddress
//     };

//   } catch (error) {
//     console.error('배송지 정보를 불러오는 데 실패했습니다:', error);
//     alert('배송지 정보를 불러오는 데 실패했습니다.');
//   }
// });
</script>
