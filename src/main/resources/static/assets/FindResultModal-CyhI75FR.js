import{_ as d,i as c,b as r,d as s,G as _,t as m,f as p,w as g,h as u}from"./index-Cm_Q1rqZ.js";const C="/assets/Logo2-RfmGv2nh.png",v="data:image/svg+xml,%3csvg%20width='13'%20height='13'%20viewBox='0%200%2013%2013'%20fill='none'%20xmlns='http://www.w3.org/2000/svg'%3e%3cpath%20d='M6.364%204.95L11.314%200L12.728%201.414L7.778%206.364L12.728%2011.314L11.314%2012.728L6.364%207.778L1.414%2012.728L0%2011.314L4.95%206.364L0%201.414L1.414%200L6.364%204.95Z'%20fill='%238D8D8D'/%3e%3c/svg%3e",L={class:"modal-section"},h={class:"modal-container"},f={class:"modal-wrapper"},w={class:"modal-title-box"},x={class:"modal-content-box"},M={class:"modal-content"},b={__name:"FindResultModal",props:{resultMessage:String},emits:["onCloseModal"],setup(t,{emit:e}){const n=e,a=()=>{n("onCloseModal")};return(l,o)=>{const i=u("RouterLink");return c(),r("div",L,[s("div",h,[s("div",{class:"btn-modal-close",onClick:a},o[0]||(o[0]=[s("img",{src:v},null,-1)])),s("div",f,[s("div",w,[_(l.$slots,"default",{},void 0,!0)]),s("div",x,[s("div",M,[s("span",null,m(t.resultMessage),1)]),p(i,{to:"/auth/login"},{default:g(()=>o[1]||(o[1]=[s("div",{class:"btn-toLoginPage"},"로그인 페이지로",-1)])),_:1})])])])])}}},R=d(b,[["__scopeId","data-v-18bd2705"]]);export{R as F,C as _};
