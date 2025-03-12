import{_ as q,r as S,j as f,o as w,b as a,d as t,F as c,g as I,a as p,$ as b,i as l,f as v,w as C,l as M,t as i,C as k,B as $,h as x}from"./index-D656lRRA.js";const D={class:"inquiries-table"},B={class:"right-padding"},E={class:"answer-text"},N=["onClick"],F={__name:"UserInquiryList",setup(L){const n=S([]),u=f(),y=async()=>{console.log(">>>>>>>>>>>>>>>>",u.user.userId);try{const o=await p.get(`/api/mypage/inquiry/user/${u.user.userId}`);n.value=o.data.data,console.log(o.data.data)}catch(o){console.error("Error loading inquiries:",o)}},m=o=>{const e=new Date(o),r=e.getFullYear(),s=String(e.getMonth()+1).padStart(2,"0"),d=String(e.getDate()).padStart(2,"0");return`${r}.${s}.${d}`},g=o=>{k().showModal("문의 삭제","정말로 문의를 삭제하시겠습니까?","삭제된 문의는 복구할 수 없습니다.","삭제하기",()=>_(o))},_=async o=>{try{await p.delete(`/api/mypage/inquiry/${o}`),n.value=n.value.filter(r=>r.inquiryId!==o),$().showModal("문의 삭제","문의 삭제가 완료되었습니다.")}catch(e){console.error("Error deleting inquiry:",e)}},h=()=>{b.push("/mypage/inquiry/create")};return w(()=>{y()}),(o,e)=>{const r=x("router-link");return l(),a(c,null,[t("section",{class:"section-inquiries"},[e[1]||(e[1]=t("div",null,[t("p",{id:"section-title"},"1:1 문의내역")],-1)),t("div",{class:"space-between"},[e[0]||(e[0]=t("p",{style:{"font-size":"13px"}},"한번 등록한 상담내용은 수정이 불가능합니다.",-1)),t("button",{class:"inquiry-btn",onClick:h},"+ 문의하기")])]),t("table",D,[e[2]||(e[2]=t("thead",null,[t("tr",null,[t("th",null,"문의 제목"),t("th",null,"작성일"),t("th",null,"답변여부"),t("th")])],-1)),t("tbody",null,[(l(!0),a(c,null,I(n.value,s=>(l(),a("tr",{key:s.inquiryId},[t("td",B,[v(r,{to:`/mypage/inquiry/${s.inquiryId}`},{default:C(()=>[M(i(s.inquiryTitle),1)]),_:2},1032,["to"])]),t("td",null,i(m(s.inquiryCreatedAt)),1),t("td",E,i(s.answerState==="Y"?"YES":"NO"),1),t("td",null,[t("button",{onClick:d=>g(s.inquiryId),class:"delete-btn"}," 삭제 ",8,N)])]))),128))])])],64)}}},Y=q(F,[["__scopeId","data-v-0a378898"]]);export{Y as default};
