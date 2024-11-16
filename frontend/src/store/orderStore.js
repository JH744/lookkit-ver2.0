import { defineStore } from 'pinia';

export const useOrderStore = defineStore('orderStore', {
  state: () => ({
    selectedItems: [], // 초기 상태는 빈 배열로 정의
  }),
  actions: {
    setSelectedItems(items) {
      console.log("Setting items in store:", items);
      this.selectedItems = items.map(item => ({
        itemId: item.productId || item.codiId,
        itemName: item.productName || item.codiName,
        brandName: item.brandName || '',
        type: item.type,
        quantity: item.quantity || 1,
        startDate: item.rentalStartDate || null,
        endDate: item.rentalEndDate || null,
        totalPrice: item.productPrice || item.codiPrice,
      }));
    },
    clearSelectedItems() {
      console.log("Clearing selected items in store.");
      this.selectedItems = [];
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'orderStore',
        storage: localStorage,
        paths: [], // 기본적으로 아무 데이터도 저장하지 않음
      },
    ],
  },
});
