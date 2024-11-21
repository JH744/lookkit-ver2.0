<template>
    <aside class="sidebar">
      <div>
        <p class="mypage-block">MY PAGE</p>
      </div>
      <div>
        <ul>
          <li><router-link to="/mypage/manage">주문 관리</router-link></li>
          <li>
            <router-link to="/mypage/inquiry">1:1 문의하기</router-link>
          </li>
          <li>
            <router-link to="/mypage/review">리뷰 관리</router-link>
          </li>
          <li><router-link to="/mypage/wishlist">찜한 상품</router-link></li>
        </ul>
      </div>
    </aside>
  </template>
  
  <script setup>

  </script>
  
  <style scoped>
.sidebar {
    width: 170px;
    height: 100%;
    margin-top : 25px;
}

.mypage-block {
    text-align: center;
    align-content: center;
    background-color: #f0f0f0;
    height: 150px;
    font-size: 20px;
}

.sidebar h3 {
    font-size: 18px;
    margin-bottom: 20px;
}

.sidebar ul {
    list-style: none;
    padding: 0;
}

.sidebar ul li {
    margin-bottom: 15px;
}

.sidebar ul li a {
    text-decoration: none;
    color: #333;
    font-size: 17px;
    margin-top: 15px;
    display: block;
    padding: 5px 10px;
    transition: background-color 0.3s, color 0.3s;
}

.sidebar ul li a:hover {
    background-color: #f0f0f0;
    color: #000;
    cursor: pointer;
}

.sidebar ul li ul {
    padding-left: 15px; /* 하위 메뉴 들여쓰기 */
    margin-top: 10px; /* 하위 메뉴와 상위 메뉴 간 간격 */
}

.sidebar ul li ul li {
    margin-bottom: 10px; /* 하위 메뉴의 간격 */
    font-size: 0.85em; /* 하위 항목의 글씨 크기를 부모 항목 대비 85%로 설정 */
}

.sidebar ul li ul li a {
    font-size: 14px; /* 하위 메뉴의 글자 크기 */
    color: #555; /* 하위 메뉴의 색상 */
}

.sidebar ul li ul li a:hover {
    background-color: #e0e0e0;
    color: #333;
}
  </style>
  