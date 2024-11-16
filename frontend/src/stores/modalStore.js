import { defineStore } from "pinia";

export const useModalStore = defineStore("modal", {
  state: () => ({
    isModalVisible: false,
    modalAction: "",
    message: "",
  }),
  actions: {
    showModal(modalAction, message) {
      this.isModalVisible = true;
      this.modalAction = modalAction;
      this.message = message;
    },
    closeModal() {
      this.isModalVisible = false;
      this.modalAction = "";
      this.message = "";
    },
  },
});

export const useConfirmModalStore = defineStore("confirmModal", {
  state: () => ({
    isModalVisible: false,
    modalActionMessage: "",
    questionMessage: "",
    warningMessage: "",
    cofirmButton: "",
    confirmCallback: null, // 확인 버튼을 눌렀을 때 실행할 콜백 함수
  }),
  actions: {
    showModal(
      modalActionMessage,
      questionMessage,
      warningMessage,
      cofirmButton,
      callback = null
    ) {
      this.isModalVisible = true;
      this.modalActionMessage = modalActionMessage;
      this.questionMessage = questionMessage;
      this.warningMessage = warningMessage;
      this.cofirmButton = cofirmButton;
      this.confirmCallback = callback; // 콜백 함수 저장
    },
    closeModal() {
      this.isModalVisible = false;
      this.modalActionMessage = "";
      this.questionMessage = "";
      this.warningMessage = "";
      this.cofirmButton = "";
      this.confirmCallback = null; // 모달이 닫히면 콜백 함수 초기화
    },
  },
});
