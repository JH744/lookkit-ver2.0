import{a as f}from"./axios-ywBQ3a9P.js";import{_ as w,r as y,o as k,a as o,b as t,t as l,l as _,F as g,f as S,e as I,w as b,u as C,g as D,h as u,k as $}from"./index-BBnxBt67.js";import{r as x,f as R,g as N}from"./firebaseConfig-Cr89vwH1.js";const V="/images/question.png",j="/images/answer.png",B={class:"inquiry"},F={class:"inquiry-meta"},L={class:"inquiry-subject"},M={class:"inquiry-date"},P={key:0,class:"answer-state"},T={class:"inquiry-content"},U={class:"inquiry-text"},A={key:0},E=["src"],H={key:0,class:"reply"},O={class:"inquiry-meta"},Y={class:"inquiry-date"},z={class:"reply-content"},G={class:"reply-text"},J={class:"inquiry-btn-block"},K={__name:"InquiryDetail",setup(Q){const q=C().currentRoute.value,n=y({}),r=y({}),d=y([]),v=async()=>{try{const i=q.params.inquiryId,e=await f.get(`/api/mypage/inquiry/${i}`);if(n.value=e.data.data,n.value.inquiryImages&&n.value.inquiryImages.length>0){const c=await Promise.all(n.value.inquiryImages.map(async s=>{try{const a=x(R,s.imagePath);return await N(a)}catch(a){return console.error(`이미지 URL을 가져오는 중 오류 발생: ${s.imagePath}`,a),null}}));d.value=c.filter(s=>s!==null)}}catch(i){console.log(i)}};k(()=>{v()});const m=i=>{const e=new Date(i),c=e.getFullYear(),s=String(e.getMonth()+1).padStart(2,"0"),a=String(e.getDate()).padStart(2,"0"),p=String(e.getHours()).padStart(2,"0"),h=String(e.getMinutes()).padStart(2,"0");return`${c}.${s}.${a} ${p}:${h}`};return(i,e)=>{const c=D("router-link");return u(),o(g,null,[e[4]||(e[4]=t("section",{class:"section-inquiries"},[t("p",{id:"section-title"},"1:1 문의하기")],-1)),t("div",B,[t("div",F,[e[0]||(e[0]=t("img",{src:V,alt:""},null,-1)),t("div",null,[t("p",L,l(n.value.inquiryTitle),1),t("span",M,l(m(n.value.inquiryCreatedAt)),1)]),r.value&&r.value.answerContents?(u(),o("span",P,l(r.value?"✔ 답변 완료":""),1)):_("",!0)]),t("div",T,[t("p",U,l(n.value.inquiryContents),1),d.value.length?(u(),o("div",A,[(u(!0),o(g,null,S(d.value,(s,a)=>(u(),o("div",{key:a,class:"inquiry-image-container"},[t("img",{class:"inquiry-image",src:s,alt:""},null,8,E)]))),128))])):_("",!0)])]),Object.keys(r.value).length>0?(u(),o("div",H,[t("div",O,[e[2]||(e[2]=t("img",{class:"meta-icon question",src:j,alt:""},null,-1)),t("div",null,[e[1]||(e[1]=t("p",{class:"inquiry-subject"},"루킷 담당자",-1)),t("span",Y,l(m(r.value.answerCreatedAt)),1)])]),t("div",z,[t("p",G,l(r.value.answerContents),1)])])):_("",!0),t("div",J,[I(c,{class:"back-to-inquiry",to:"/mypage/inquiry"},{default:b(()=>e[3]||(e[3]=[$("문의 페이지로")])),_:1})])],64)}}},et=w(K,[["__scopeId","data-v-cc40cd54"]]);export{et as default};
