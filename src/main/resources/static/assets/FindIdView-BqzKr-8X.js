import{_ as x,r as t,b as r,f as w,w as C,m,d as s,x as p,y as f,F,a as b,i as u,u as g}from"./index-Cm_Q1rqZ.js";import{F as M,_ as V}from"./FindResultModal-CyhI75FR.js";const k={key:0},h={class:"findIdContainer"},y={class:"findIdBackground"},B={class:"error-text-box"},N={key:0,class:"error-text"},T={action:"/auth/findID",method:"post",id:"findIdForm",class:"formContainer"},A={__name:"FindIdView",setup(D){g();const o=t(!1),a=t(""),l=t(""),_=t(""),n=t(""),v=t(""),c=()=>{o.value=!o.value},I=async()=>{try{const d=await b.post("/api/users/find/id",{userName:a.value,email:l.value},{withCredentials:!0}).then(e=>{console.log("res",e),_.value=e.data,c(),n.value=!1,v.value=`${a.value}님의 아이디는 ${e.data}입니다.`})}catch(d){console.log("error",d),n.value=!0}};return(d,e)=>(u(),r(F,null,[o.value?(u(),r("div",k,[w(M,{onOnCloseModal:c,resultMessage:v.value},{default:C(()=>e[2]||(e[2]=[s("p",null,"아이디 찾기 완료",-1)])),_:1},8,["resultMessage"])])):m("",!0),s("div",h,[e[6]||(e[6]=s("div",{class:"findIdTitle"},[s("img",{src:V,width:"300px"})],-1)),s("div",y,[e[5]||(e[5]=s("div",{class:"tabContainer"},[s("div",{class:"tabActive"},[s("div",{class:"tabTextActive"},"아이디찾기")])],-1)),s("div",B,[n.value?(u(),r("span",N,"조회되는 아이디가 없습니다.")):m("",!0)]),s("form",T,[e[3]||(e[3]=s("div",{class:"formLabel"},"이름",-1)),p(s("input",{class:"formInput",placeholder:"이름을 입력해 주세요","onUpdate:modelValue":e[0]||(e[0]=i=>a.value=i)},null,512),[[f,a.value]]),e[4]||(e[4]=s("div",{class:"formLabel"},"이메일",-1)),p(s("input",{class:"formInput",placeholder:"이메일을 입력해 주세요","onUpdate:modelValue":e[1]||(e[1]=i=>l.value=i)},null,512),[[f,l.value]]),s("div",{id:"findIdBtn",class:"buttonContainer",onClick:I}," 확인 ")])])])],64))}},R=x(A,[["__scopeId","data-v-a86316fd"]]);export{R as default};
