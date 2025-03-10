import{_ as $,r as S,j as w,o as k,b as c,d as o,F as h,g as v,a as m,i as d,t as r,u as y,D,C as M}from"./index-Cm_Q1rqZ.js";import{r as R,f as C,g as x}from"./firebaseConfig-Cr89vwH1.js";const U={class:"reviews-table"},F={class:"product-info"},L=["src","onClick"],B={class:"product-detail"},N={class:"brand-name"},V={class:"product-name"},A={class:"review-text"},E=["onClick"],T={__name:"ReviewListView",setup(j){const l=S([]),i=w(),u=y(),f=async()=>{var e;try{const s=await m.get(`/api/reviews/list/${i.user.userId}`);l.value=s.data;for(let t of l.value)t.productId?await p(t,"product"):t.codiId&&await p(t,"codi");console.log("User ID:",(e=i.user)==null?void 0:e.userId),console.log("불러온 리뷰 데이터:",s.data)}catch(s){console.error("리뷰를 불러오는 중 오류 발생:",s)}},p=async(e,s)=>{let t;s==="product"?t=`lookkit/products/0${e.productId}/${e.productId}_thumbnail.webp`:s==="codi"&&(t=`lookkit/codi/0${e.codiId}/${e.codiId}_thumbnail.webp`);try{const a=R(C,t),n=await x(a);e.thumbnailUrl=n}catch(a){console.error(`이미지 가져오기 실패: ${t}`,a),e.thumbnailUrl=null}},_=e=>{e.productId?u.push(`/product/${e.productId}`):e.codiId?u.push(`/codi/${e.codiId}`):alert("상품 또는 코디 정보가 올바르지 않습니다.")},g=e=>{const s=new Date(e),t=s.getFullYear(),a=String(s.getMonth()+1).padStart(2,"0"),n=String(s.getDate()).padStart(2,"0");return`${t}.${a}.${n}`},I=e=>{D().showModal("리뷰 삭제","정말로 리뷰를 삭제하시겠습니까?","삭제된 리뷰는 복구할 수 없습니다.","삭제하기",()=>b(e))},b=async e=>{try{await m.delete(`/api/reviews/${e}`),l.value=l.value.filter(t=>t.reviewId!==e),M().showModal("리뷰 삭제","리뷰 삭제가 완료되었습니다.")}catch(s){console.error("Error deleting review:",s)}};return k(()=>{f()}),(e,s)=>(d(),c(h,null,[s[1]||(s[1]=o("section",{class:"section-reviews"},[o("div",null,[o("p",{id:"section-title"},"작성한 리뷰")])],-1)),o("table",U,[s[0]||(s[0]=o("thead",null,[o("tr",null,[o("th",null,"상품/코디 정보"),o("th",null,"작성일"),o("th",null,"평점"),o("th",null,"리뷰 내용"),o("th",null,"삭제")])],-1)),o("tbody",null,[(d(!0),c(h,null,v(l.value,t=>(d(),c("tr",{key:t.reviewId},[o("td",null,[o("div",F,[o("img",{src:t.thumbnailUrl||"/images/placeholder.png",alt:"상품/코디 이미지",class:"product-thumbnail",onClick:a=>_(t)},null,8,L),o("div",B,[o("p",N,r(t.brandName||""),1),o("p",V,r(t.productId?t.productName:t.codiDescription),1)])])]),o("td",null,r(g(t.createdAt)),1),o("td",null,r(t.rating)+" / 5",1),o("td",A,r(t.reviewText),1),o("td",null,[o("button",{onClick:a=>I(t.reviewId),class:"delete-btn"}," 삭제 ",8,E)])]))),128))])])],64))}},q=$(T,[["__scopeId","data-v-7fa55f29"]]);export{q as default};
