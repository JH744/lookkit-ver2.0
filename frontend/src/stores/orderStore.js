import { defineStore } from 'pinia';

export const useOrderStore = defineStore('orderStore', {
  state: () => ({
    selectedItems: [], // 초기 상태는 빈 배열로 정의
    totalFinalPrice: 0, // 총 가격을 저장할 상태
  }),
  actions: {
    setSelectedItems(items, totalPrice) {
      console.log("Setting items in store:", items);
      this.selectedItems = items.map(item => {
        // item.type이 없는 경우, productId 또는 codiId에 따라 타입을 설정합니다.
        const type = item.productId ? 'product' : 'codi';
    
        return {
          itemId: item.productId || item.codiId,
          itemName: item.productName || item.codiDescription,
          brandName: item.brandName || '',
          type: type,  // 확실히 타입을 설정합니다.
          quantity: item.quantity || 1,
          startDate: item.rentalStartDate || null,
          endDate: item.rentalEndDate || null,
          totalPrice: item.productPrice || item.codiPrice,
        };
      });
      this.totalFinalPrice = totalPrice; // 전달받은 totalPrice를 상태에 저장
    },
    
    clearSelectedItems() {
      console.log("Clearing selected items in store.");
      this.selectedItems = [];
      this.totalFinalPrice = 0; // 선택된 아이템 초기화시 총 가격도 초기화
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'orderStore',
        storage: localStorage,
        paths: ['selectedItems', 'totalFinalPrice'], // 선택된 아이템과 총 가격을 저장
      },
    ],
  },
});
