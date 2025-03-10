import{F as V,_ as F}from"./FindResultModal-CyhI75FR.js";import{_ as M,r as t,i as c,b as f,d as e,x as C,y as b,G as y,m,F as x,v as B,a as A,f as E,w as U,s as g,C as O,h as N,t as S}from"./index-Cm_Q1rqZ.js";const T={class:"formContainer2"},H={class:"error-text-box"},D={key:0,class:"error-text"},L={__name:"AuthenticationEmail",props:{verificationCode:String},emits:["completeAuthentication"],setup(_,{emit:d}){const l=_,n=t(""),v=d,i=()=>{console.log("에밋발사전"),console.log("verificationCode",l.verificationCode),v("completeAuthentication")},u=()=>{l.verificationCode==n.value?(console.log("일치함"),i()):alert("불일치합니다.")};return(o,s)=>(c(),f(x,null,[s[2]||(s[2]=e("div",{class:"tabContainer"},[e("div",{class:"tabActive"},[e("div",{class:"tabTextActive"},"비밀번호찾기")])],-1)),e("div",T,[C(e("input",{class:"formInput",placeholder:"인증번호 입력","onUpdate:modelValue":s[0]||(s[0]=a=>n.value=a)},null,512),[[b,n.value]]),s[1]||(s[1]=e("div",{style:{margin:"15px"},class:"verificationNotice-box"},[e("span",{class:"verificationNotice"},"*3분 이내로 인증번호 6자리를 입력해주세요.")],-1)),e("div",H,[o.isFail?(c(),f("span",D,[y(o.$slots,"default",{},void 0,!0)])):m("",!0)]),e("div",{id:"authenticationOKBtn",class:"buttonContainer",onClick:u}," 확인 ")])],64))}},G=M(L,[["__scopeId","data-v-85534416"]]),K={action:"/auth/updatePwd",method:"post",class:"formContainer3"},R={__name:"UpdatePassword",props:{userUuid:String},emits:["onOpenModal"],setup(_,{emit:d}){const l=_,n=d,v=()=>{console.log("비밀번호찾기완료창 오픈전"),n("onOpenModal")},i=t(""),u=t(""),o=async()=>{i.value==u.value?A.post("/api/users/update/password",{userUuid:l.userUuid,password:i.value}).then(s=>{console.log(s),v()}).catch(s=>{console.log(s)}):alert("번호가 일치하지 않음")};return(s,a)=>(c(),f(x,null,[a[4]||(a[4]=e("div",{class:"tabContainer"},[e("div",{class:"tabActive"},[e("div",{class:"tabTextActive"},"비밀번호찾기")])],-1)),e("form",K,[a[2]||(a[2]=e("span",{class:""},"새비밀번호입력",-1)),C(e("input",{type:"password",class:"formInput",placeholder:"새비밀번호를 입력해 주세요",id:"newPwd","onUpdate:modelValue":a[0]||(a[0]=w=>i.value=w)},null,512),[[b,i.value]]),a[3]||(a[3]=e("span",{class:""},"새비밀번호 재입력",-1)),C(e("input",{type:"password",class:"formInput",placeholder:"새비밀번호를 재입력해 주세요",id:"newPwd2","onUpdate:modelValue":a[1]||(a[1]=w=>u.value=w)},null,512),[[b,u.value]]),e("div",{id:"ModifyBtn",class:"buttonContainer",onClick:B(o,["prevent"])}," 확인 ")])],64))}},j=M(R,[["__scopeId","data-v-2a270e97"]]),q={class:"tabContainer"},z={class:"error-text-box"},J={class:"error-text"},Q={class:"formContainer"},W={__name:"FindPasswordForm",emits:["onEmailSender"],setup(_,{emit:d}){const l=t(""),n=t(""),v=d,i=()=>{console.log("에밋발사전"),v("onEmailSender",{userUuid:l.value,email:n.value})};return(u,o)=>(c(),f(x,null,[e("div",q,[o[2]||(o[2]=e("div",{class:"tabActive"},[e("div",{class:"tabTextActive"},"비밀번호찾기")],-1)),e("div",z,[e("span",J,[y(u.$slots,"default",{},void 0,!0)])])]),e("div",Q,[o[3]||(o[3]=e("div",{class:"formLabel"},"아이디",-1)),C(e("input",{class:"formInput",placeholder:"아이디를 입력해 주세요","onUpdate:modelValue":o[0]||(o[0]=s=>l.value=s)},null,512),[[b,l.value]]),o[4]||(o[4]=e("div",{class:"formLabel"},"이메일",-1)),C(e("input",{class:"formInput",placeholder:"이메일을 입력해 주세요","onUpdate:modelValue":o[1]||(o[1]=s=>n.value=s)},null,512),[[b,n.value]]),e("div",{id:"authenticationBtn",class:"buttonContainer",onClick:i}," 인증받기 ")])],64))}},X=M(W,[["__scopeId","data-v-5b063ba7"]]),Y={key:0},Z={class:"findIdContainer"},ee={class:"findIdBackground"},oe={__name:"FindPwdView",setup(_){const d=t("box1"),l=t(""),n=t(""),v=t(""),i=t(!1),u=t(""),o=t(""),s=t(""),a=t(!1),w=t(""),h=()=>{console.log("HandleShowModal 호출"),i.value=!i.value,u.value="비밀번호를 성공적으로 변경하였습니다."},k=r=>{console.log("userUuid",r.userUuid),console.log("email",r.email),n.value=r.userUuid,v.value=r.email;let p=/^[^\s@]+@[^\s@]+\.[^\s@]+$/;if(r.userUuid.trim()===""){o.value="아이디를 입력해 주세요.";return}if(!p.test(v.value)){o.value="올바른 이메일 형식을 입력해 주세요.";return}o.value="이메일 인증 요청중입니다....",P()},P=async()=>{await A.post("/api/mailsender",{userUuid:n.value,email:v.value}).then(r=>{console.log("인증코드",r.data),l.value=String(r.data),O().showModal("이메일 인증","입력하신 이메일로 인증번호를 발송했습니다."),console.log("인증코드: ",l.value),d.value="box2"}).catch(r=>{console.error("Error:",r),s.value="이메일 인증에 실패했습니다. 다시 시도해 주세요."})},$=()=>{d.value="box3"};return(r,p)=>{const I=N("ConfirmModal");return c(),f(x,null,[i.value?(c(),f("div",Y,[E(V,{resultMessage:u.value,onOnCloseModal:h},{default:U(()=>p[0]||(p[0]=[e("p",null,"비밀번호 변경완료",-1)])),_:1},8,["resultMessage"])])):m("",!0),a.value?(c(),g(I,{key:1,confirmMessage:w.value,onOnCloseModal:r.HandleShowConfirmModal},null,8,["confirmMessage","onOnCloseModal"])):m("",!0),e("div",Z,[p[1]||(p[1]=e("div",{class:"findIdTitle"},[e("img",{src:F,width:"300px"})],-1)),e("div",ee,[d.value=="box1"?(c(),g(X,{key:0,onOnEmailSender:k},{default:U(()=>[e("span",null,S(o.value),1)]),_:1})):m("",!0),d.value=="box2"?(c(),g(G,{key:1,"verification-code":l.value,onCompleteAuthentication:$},{default:U(()=>[e("span",null,S(s.value),1)]),_:1},8,["verification-code"])):m("",!0),d.value=="box3"?(c(),g(j,{key:2,userUuid:n.value,onOnOpenModal:h},null,8,["userUuid"])):m("",!0)])])],64)}}},ae=M(oe,[["__scopeId","data-v-b4acabf0"]]);export{ae as default};
