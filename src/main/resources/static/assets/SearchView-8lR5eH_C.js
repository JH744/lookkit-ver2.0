import{_ as g}from"./heart0-B8JIzpbZ.js";import{_ as f,i as b,r as c,j as y,o as w,a as i,b as s,t as a,F as k,f as x,m as I,h as d}from"./index-BBnxBt67.js";import{a as $}from"./axios-ywBQ3a9P.js";import{d as B}from"./img_none-DejiioVZ.js";const P={class:"page-container"},S={class:"page-container"},N={class:"title-section"},U={class:"title-box"},E={class:"main-content"},R={class:"product-list"},V=["src"],q={class:"product-info"},C=["href"],F={class:"brand-name"},j={class:"product-name"},A={class:"product-price-box"},D={class:"product-price"},K={__name:"SearchView",setup(L){const p=b(),r=c([]),l=I(),n=c(l.query.keyword),m=c("https://firebasestorage.googleapis.com/v0/b/test-24a07.appspot.com/o/lookkit"),_=t=>{t.target.src=B};y(()=>l.query.keyword,t=>{n.value=t,u()});const u=async()=>{try{const t=await $.get("/api/main/search",{params:{keyword:n.value}});r.value=t.data,console.log("검색결과 리스트:",r.value)}catch(t){console.error("상품 데이터 조회 오류 발생:",t)}};w(()=>{console.log("유저정보",p.user),u()});const v=t=>{const o=`/products/0${t}`,e=`/${t}_thumbnail.webp`,h=`${encodeURIComponent(o)}${encodeURIComponent(e)}`;return`${m.value}${h}?alt=media`};return(t,o)=>(d(),i("div",P,[s("div",S,[s("div",N,[s("div",U,[s("h1",null,a(`'  ${n.value}  ' 검색완료`),1),s("h1",null,a("검색결과수 "+r.value.length),1)]),o[0]||(o[0]=s("div",{class:"filter-section"},null,-1)),o[1]||(o[1]=s("div",{class:"sort-section"},null,-1))]),s("div",E,[s("div",R,[(d(!0),i(k,null,x(r.value,e=>(d(),i("div",{key:e.productId,class:"product-item"},[s("img",{class:"product-img",src:v(e.productId),alt:"상품썸네일",onError:_},null,40,V),s("div",q,[s("a",{href:`/product/${e.productId}`},[s("div",F,a(e.brandName),1),s("div",j,a(e.productName),1)],8,C),s("div",A,[s("div",D,a(e.productPrice)+"원",1)]),o[2]||(o[2]=s("div",{class:"like-box"},[s("img",{src:g,alt:"",width:"20px",height:"20px"}),s("span",null,"120")],-1)),o[3]||(o[3]=s("div",{class:"product-event"},[s("div",{class:"product-event-box"},[s("div",null,"대여가능")])],-1))])]))),128))])])])]))}},J=f(K,[["__scopeId","data-v-959b4819"]]);export{J as default};
