<template>
    <div class="cart-container">
      <div class="cart-title">
        <h2>장바구니</h2>
      </div>
      <div class="tab-section">
        <div :class="{ active: activeTab === 'coordinate' }" @click="activeTab = 'coordinate'">코디상품</div>
        <div :class="{ active: activeTab === 'single' }" @click="activeTab = 'single'">단일상품</div>
      </div>
      <div class="cart-header">
        <h3>상품 정보</h3>
        <div class="cart-actions">
          <button class="select-delete-button" @click="deleteSelectedItems">
            <img class="icon-delete" src="/images/icon/icon-delete.svg">
            <span>선택삭제</span>
          </button>
        </div>
      </div>
      
      <div class="selected-items-section">
        <h3>선택된 상품</h3>
        <div v-if="selectedItems.value.length === 0">
          <p>선택된 상품이 없습니다.</p>
        </div>
        <div v-for="item in selectedItems.value" :key="item.cartId" class="cart-item selected-item" :data-cart-id="item.cartId">
          <div class="item-checkbox">
            <input type="checkbox" v-model="item.selected" class="checkbox-input" />
          </div>
          <div class="item-thumbnail">
            <a :href="`/product/${item.productId}`">
              <img class="thumbnail-img" :src="`/images/products/0${item.productId}/${item.productId}_thumbnail.webp`" alt="상품 이미지" />
            </a>
          </div>
          <div class="item-description">
            <div class="brand-name2">{{ item.brandName }}</div>
            <div class="product-name">{{ item.productName }}</div>
            <div class="product-variant">{{ item.quantity }}개</div>
          </div>
          <div class="item-price-box">
            <div class="item-price">
              <strong>{{ formatPrice(item.productPrice) }}원</strong>
            </div>
            <button class="delete-item-button" @click="deleteItem(item)">
              <img class="icon-delete" src="/images/icon/icon-delete.svg" alt="삭제">
              삭제
            </button>
          </div>
        </div>
      </div>
    
      <div v-if="activeTab === 'coordinate'">
        <div v-if="coordinateItems.length === 0">
          <p>코디상품이 없습니다.</p>
        </div>
        <div v-for="item in coordinateItems" :key="item.cartId" class="cart-item" :data-cart-id="item.cartId">
          <div class="item-checkbox">
            <input type="checkbox" v-model="item.selected" class="checkbox-input" />
          </div>
          <div class="item-thumbnail">
            <a :href="`/product/${item.productId}`">
              <img class="thumbnail-img" :src="`/images/products/0${item.productId}/${item.productId}_thumbnail.webp`" alt="상품 이미지" />
            </a>
          </div>
          <div class="item-description">
            <div class="brand-name2">{{ item.brandName }}</div>
            <div class="product-name">{{ item.productName }}</div>
            <div class="product-variant">대여일: {{ item.rentalStartDate }} ~ {{ item.rentalEndDate }}</div>
          </div>
          <div class="item-price-box">
            <div class="item-price">
              <strong>{{ formatPrice(item.productPrice) }}원</strong>
            </div>
          </div>
        </div>
      </div>
      <div v-if="activeTab === 'single'">
        <div v-if="singleItems.length === 0">
          <p>단일상품이 없습니다.</p>
        </div>
        <div v-for="item in singleItems" :key="item.cartId" class="cart-item" :data-cart-id="item.cartId">
          <div class="item-checkbox">
            <input type="checkbox" v-model="item.selected" class="checkbox-input" />
          </div>
          <div class="item-thumbnail">
            <a :href="`/product/${item.productId}`">
              <img class="thumbnail-img" :src="`/images/products/0${item.productId}/${item.productId}_thumbnail.webp`" alt="상품 이미지" />
            </a>
          </div>
          <div class="item-description">
            <div class="brand-name2">{{ item.brandName }}</div>
            <div class="product-name">{{ item.productName }}</div>
            <div class="product-variant">{{ item.quantity }}개</div>
          </div>
          <div class="item-price-box">
            <div class="item-price">
              <strong>{{ formatPrice(item.productPrice) }}원</strong>
            </div>
            <div>
              <input class="item-quantity quantity-input" type="number" v-model.number="item.quantity" @change="updateItemQuantity(item)" min="1" />
            </div>
          </div>
        </div>
      </div>
      <div class="cart-summary">
        <div class="summary-header">
          <h3>최종 결제 예상 금액</h3>
        </div>
        <div class="summary-details">
          <div class="summary-item">
            <span>대여료</span>
            <strong>{{ formatPrice(rentalFeeTotal) }}원</strong>
          </div>
          <div class="summary-item">
            <span>최종 결제 예상 금액</span>
            <strong>{{ formatPrice(totalFinalPrice) }}원</strong>
          </div>
        </div>
        <div class="checkout-button-container">
          <button class="checkout-button" @click="checkout">
            <span>총 {{ totalQuantity }}개 / {{ formatPrice(totalFinalPrice) }}원 주문하기</span>
            <img class="checkout-icon">
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue';
  import axios from 'axios';
  import "@/assets/styles/carts.css";
  
  const activeTab = ref('coordinate');
  const coordinateItems = ref([]);
  const singleItems = ref([]);
  
  const fetchCartItems = async () => {
    try {
      const response = await axios.get('/cart/items');
      const items = response.data;
      items.forEach(item => {
        if (item.type === 'coordinate') {
          coordinateItems.value.push({ ...item, selected: true });
        } else if (item.type === 'single') {
          singleItems.value.push({ ...item, selected: true });
        }
      });
    } catch (error) {
      console.error('장바구니 데이터를 불러오는 데 실패했습니다.', error);
    }
  };
  
  fetchCartItems();
  
  const formatPrice = (price) => {
    return price.toLocaleString();
  };
  
  const totalQuantity = computed(() => {
    let total = 0;
    [...coordinateItems.value, ...singleItems.value].forEach(item => {
      if (item.selected) {
        total += item.quantity || 1;
      }
    });
    return total;
  });
  
  const totalFinalPrice = computed(() => {
    return [...coordinateItems.value, ...singleItems.value].reduce((acc, item) => {
      if (item.selected) {
        return acc + (item.productPrice * (item.quantity || 1));
      }
      return acc;
    }, 0);
  });
  
  const deleteSelectedItems = () => {
  if (selectedItems.value.length === 0) {
    alert('선택된 항목이 없습니다.');
    return;
  }
  selectedItems.value.forEach(item => {
    axios.post('/cart/delete', { cartId: item.cartId }).then(() => {
      coordinateItems.value = coordinateItems.value.filter(i => i.cartId !== item.cartId);
      singleItems.value = singleItems.value.filter(i => i.cartId !== item.cartId);
    }).catch(() => {
      alert('선택된 상품을 삭제하는 데 실패했습니다.');
    });
  });
  coordinateItems.value.forEach(item => item.selected = true);
  singleItems.value.forEach(item => item.selected = true);
  };
  
  const updateItemQuantity = async (item) => {
    if (item.quantity <= 0) {
      alert('수량은 1 이상이어야 합니다.');
      item.quantity = 1;
      return;
    }
    try {
      await axios.post('/cart/update', { cartId: item.cartId, quantity: item.quantity });
    } catch (error) {
      alert('수량을 변경하는 데 실패했습니다.');
    }
  };
  
  const checkout = () => {
    window.location.href = '/cart/checkout';
  };
  </script>
  
