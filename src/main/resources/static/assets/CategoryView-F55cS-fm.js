import{_ as e,c as i,b as t,d,u as o,o as r}from"./index-TG6V28-U.js";import{_ as a,a as l,b as u}from"./heart0-C-aiX0Hr.js";const p={setup(){return{query:o().query}}},n={class:"page-container"};function v(c,s,m,h,_,x){return r(),i("div",n,s[0]||(s[0]=[t("div",{class:"page-container"},[t("div",{class:"title-section"},[t("h1",{"th:text":"${type}"},"아우터"),t("div",{class:"filter-section"},[t("div",{class:"filter-btn"},[t("img",{src:a,alt:""}),d(" 필터검색 "),t("img",{src:l,alt:"",width:"10px"})])]),t("div",{class:"sort-section"},[t("div",{class:"item-count","th:text":"'상품갯수: '+${productsList.size()}+'개'"}," 2,120개 "),t("div",{class:"sort-box"},[t("ul",null,[t("li",null,"추천순"),t("li",null,"최신순"),t("li",null,"낮은 가격순"),t("li",null,"높은 가격순"),t("li",null,"판매순"),t("li",null,"리뷰 많은순")])])])]),t("div",{class:"main-content"},[t("div",{class:"product-list"},[t("div",{"th:each":"product :${productsList}",class:"product-item"},[t("img",{"th:src":"@{'/images/products/0'+${product.productId}+'/'+${product.productId}+'_detail_1.webp'}",alt:"상품썸네일"}),t("img",{"th:src":"@{/images/icon/heart2.svg}",class:"like-btn",alt:"좋아요버튼","sec:authorize":"isAuthenticated()"}),t("div",{class:"product-info"},[t("a",{"th:href":"@{|/product/${product.productId}|}"},[t("div",{class:"brand-name","th:text":"${product.brandName}"}," 브랜드 "),t("div",{class:"product-name","th:text":"${product.productName}"}," 아우터 ")]),t("div",{class:"product-price-box"},[t("div",{class:"product-price","th:text":"${product.productPrice}+'원'"}," 120,000원 "),t("div",{class:"product-price-discount"},"20%"),t("div",{class:"hidden-id","th:text":"${product.productId}"}," 상품ID ")]),t("div",{class:"like-box"},[t("img",{src:u,alt:"",width:"20px",height:"20px"}),t("span",null,"120")]),t("div",{class:"product-event"},[t("div",{class:"product-event-box"},[t("div",null,"쿠폰"),t("div",null,"대여가능")])])])])])])],-1)]))}const g=e(p,[["render",v],["__scopeId","data-v-670edd5f"]]);export{g as default};