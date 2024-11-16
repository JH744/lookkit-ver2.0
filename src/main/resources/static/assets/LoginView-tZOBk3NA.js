import{_ as x,r as i,e as L,w as I,c as f,b as s,f as k,g as M,h as u,v as B,i as F,n as g,j as P,k as v,l as p,m as _,o as c,d as m,p as y}from"./index-TG6V28-U.js";import{a as V}from"./axios-CCb-kr4I.js";const Z="data:image/svg+xml,%3csvg%20width='22'%20height='16'%20viewBox='0%200%2022%2016'%20fill='none'%20xmlns='http://www.w3.org/2000/svg'%3e%3cpath%20d='M11%202.45337C14.79%202.45337%2018.17%204.58337%2019.82%207.95337C18.17%2011.3234%2014.8%2013.4534%2011%2013.4534C7.2%2013.4534%203.83%2011.3234%202.18%207.95337C3.83%204.58337%207.21%202.45337%2011%202.45337ZM11%200.453369C6%200.453369%201.73%203.56337%200%207.95337C1.73%2012.3434%206%2015.4534%2011%2015.4534C16%2015.4534%2020.27%2012.3434%2022%207.95337C20.27%203.56337%2016%200.453369%2011%200.453369ZM11%205.45337C12.38%205.45337%2013.5%206.57337%2013.5%207.95337C13.5%209.33337%2012.38%2010.4534%2011%2010.4534C9.62%2010.4534%208.5%209.33337%208.5%207.95337C8.5%206.57337%209.62%205.45337%2011%205.45337ZM11%203.45337C8.52%203.45337%206.5%205.47337%206.5%207.95337C6.5%2010.4334%208.52%2012.4534%2011%2012.4534C13.48%2012.4534%2015.5%2010.4334%2015.5%207.95337C15.5%205.47337%2013.48%203.45337%2011%203.45337Z'%20fill='%239F9F9F'/%3e%3c/svg%3e",N="data:image/svg+xml,%3csvg%20width='24'%20height='25'%20viewBox='0%200%2024%2025'%20fill='none'%20xmlns='http://www.w3.org/2000/svg'%3e%3cg%20clip-path='url(%23clip0_377_3396)'%3e%3cpath%20fill-rule='evenodd'%20clip-rule='evenodd'%20d='M5.20939%2017.8512C7.1303%2019.2049%209.47183%2020%2012%2020C17%2020%2021.27%2016.89%2023%2012.5C21.884%209.66813%2019.7111%207.36889%2016.9671%206.09351L15.4446%207.61599C17.7624%208.47798%2019.694%2010.2003%2020.82%2012.5C19.17%2015.87%2015.8%2018%2012%2018C10.0396%2018%208.19356%2017.4331%206.63915%2016.4215L5.20939%2017.8512ZM8.18202%2014.8786C8.97813%2016.1512%2010.3924%2017%2012%2017C14.48%2017%2016.5%2014.98%2016.5%2012.5C16.5%2010.8924%2015.6512%209.47813%2014.3786%208.68202L12.8953%2010.1653C13.8335%2010.5256%2014.5%2011.4354%2014.5%2012.5C14.5%2013.88%2013.38%2015%2012%2015C10.9354%2015%2010.0256%2014.3335%209.66529%2013.3953L8.18202%2014.8786ZM9.52916%2012.1172L7.72896%2013.9174C7.58045%2013.4715%207.5%2012.9949%207.5%2012.5C7.5%2010.02%209.52%208%2012%208C12.4949%208%2012.9715%208.08045%2013.4174%208.22896L11.6172%2010.0292C10.5435%2010.1944%209.69436%2011.0435%209.52916%2012.1172ZM14.3628%207.28363C13.6025%207.09745%2012.8106%207%2012%207C8.21%207%204.83%209.13%203.18%2012.5C3.82386%2013.815%204.72961%2014.9412%205.82063%2015.8258L4.41054%2017.2359C2.91213%2015.9768%201.72996%2014.3523%201%2012.5C2.73%208.11%207%205%2012%205C13.3908%205%2014.7251%205.24062%2015.9639%205.68247L14.3628%207.28363Z'%20fill='%239F9F9F'/%3e%3cpath%20fill-rule='evenodd'%20clip-rule='evenodd'%20d='M20.7071%203.74626C21.0976%204.13679%2021.0976%204.76995%2020.7071%205.16048L4.70711%2021.1605C4.31658%2021.551%203.68342%2021.551%203.29289%2021.1605C2.90237%2020.77%202.90237%2020.1368%203.29289%2019.7463L19.2929%203.74626C19.6834%203.35574%2020.3166%203.35574%2020.7071%203.74626Z'%20fill='%239F9F9F'/%3e%3c/g%3e%3cdefs%3e%3cclipPath%20id='clip0_377_3396'%3e%3crect%20width='24'%20height='24'%20fill='white'%20transform='translate(0%200.453369)'/%3e%3c/clipPath%3e%3c/defs%3e%3c/svg%3e",S={class:"loginContainer"},D={class:"loginBox"},T={key:0,class:"loginFailMessage"},U={class:"formContainer"},j=["type"],E={class:"rememberIdContainer"},R={type:"button",class:"signupLink",id:"signupLink"},$={class:"findIdPasswordContainer"},z={class:"findIdLink"},H={class:"findPasswordLink"},q={__name:"LoginView",setup(A){const h=y(),n=i(""),d=i(""),t=i(!1),C=i("password"),w=i(!1),a=i(!1);L(()=>{const l=localStorage.getItem("savedId");l&&(a.value=!0,n.value=l)}),I(()=>t.value,l=>{C.value=l?"text":"password"});const b=async()=>{try{const l=await V.post("/api/auth/login",{username:n.value,password:d.value}).then(e=>{a.value?localStorage.setItem("savedId",n.value):localStorage.removeItem("savedId")});h.push("/main")}catch{w.value=!0}};return(l,e)=>{const r=_("router-link");return c(),f("div",null,[s("div",S,[s("div",D,[e[16]||(e[16]=s("div",{class:"loginHeading"},"로그인",-1)),w.value?(c(),f("div",T,e[5]||(e[5]=[s("span",null,"아이디 또는 비밀번호를 재확인해주세요",-1)]))):k("",!0),s("div",U,[s("form",{class:"formBox",onSubmit:M(b,["prevent"])},[e[13]||(e[13]=s("div",{class:"formLabel formLabelId"},[s("label",null,"아이디")],-1)),u(s("input",{name:"username","onUpdate:modelValue":e[0]||(e[0]=o=>n.value=o),type:"text",placeholder:"아이디를 입력하세요",id:"idInputBox",class:"inputField inputFieldId"},null,512),[[B,n.value]]),e[14]||(e[14]=s("div",{class:"formLabel formLabelPassword"},[s("label",null,"비밀번호")],-1)),u(s("input",{type:C.value,name:"password","onUpdate:modelValue":e[1]||(e[1]=o=>d.value=o),placeholder:"비밀번호 영문/특수문자/숫자 8~16자",id:"pwdInputBox",class:"inputField inputFieldPassword"},null,8,j),[[F,d.value]]),s("div",{id:"showPwdButton",class:g(["showPwdButton",{hidden:!t.value}]),onClick:e[2]||(e[2]=o=>t.value=!t.value)},e[6]||(e[6]=[s("img",{src:Z,width:"27",height:"27"},null,-1)]),2),s("div",{id:"NoShowPwdButton",class:g(["NoShowPwdButton",{hidden:t.value}]),onClick:e[3]||(e[3]=o=>t.value=!t.value)},e[7]||(e[7]=[s("img",{src:N,width:"31",height:"31"},null,-1)]),2),s("div",E,[u(s("input",{type:"checkbox",class:"checkbox",id:"rememberId","onUpdate:modelValue":e[4]||(e[4]=o=>a.value=o)},null,512),[[P,a.value]]),e[8]||(e[8]=s("label",{class:"rememberIdLabel",for:"rememberId"},"아이디 저장",-1))]),e[15]||(e[15]=s("button",{type:"submit",class:"loginButton"},"로그인",-1)),s("button",R,[v(r,{to:"/auth/signup"},{default:p(()=>e[9]||(e[9]=[m("회원가입")])),_:1})]),s("div",$,[s("div",z,[v(r,{to:"/auth/findId"},{default:p(()=>e[10]||(e[10]=[m("아이디 찾기")])),_:1})]),e[12]||(e[12]=s("div",{class:"verticalDivider"},null,-1)),s("div",H,[v(r,{to:"/auth/findPwd"},{default:p(()=>e[11]||(e[11]=[m("비밀번호 찾기")])),_:1})])])],32)])])])])}}},K=x(q,[["__scopeId","data-v-9115d06f"]]);export{K as default};