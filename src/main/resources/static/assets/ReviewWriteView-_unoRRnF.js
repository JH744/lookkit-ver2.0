import{_ as H,i as K,r as d,o as Q,B as C,c as X,a as i,b as t,t as r,F as b,f as k,v as B,x as T,l as V,m as Y,u as Z,h as n,C as ee}from"./index-BBnxBt67.js";import{_ as O}from"./add_img-CGArnYmR.js";import{a as w}from"./axios-ywBQ3a9P.js";import{r as q,f as E,g as j}from"./firebaseConfig-Cr89vwH1.js";const te={class:"second-grid"},se={key:0,class:"review-section"},oe={class:"product-details"},le=["src"],ae={class:"product-name"},ie={class:"product-model"},ne={class:"product-price"},re={class:"rating-section"},ue=["src","alt"],ce={class:"rating-text"},de={class:"review-input-section"},pe={class:"text-state"},ve={class:"text-count"},me={class:"photo-upload-section"},ge={class:"image-upload"},fe=["onClick"],he=["onChange"],we={key:0,class:"plus-text"},_e=["src"],ye={key:1,class:"review-section"},be={class:"product-details"},ke=["src"],Ie={class:"product-name"},Re={class:"product-price"},Ce={class:"product-price"},xe={class:"rating-section"},Se=["src","alt"],$e={class:"rating-text"},Fe={class:"review-input-section"},Me={class:"text-state"},Ue={class:"text-count"},De={class:"photo-upload-section"},Ne={class:"image-upload"},Le=["onClick"],Pe=["onChange"],Be={key:0,class:"plus-text"},Te=["src"],Ve={__name:"ReviewWriteView",setup(Oe){var P;const I=K(),A=(P=I.user)==null?void 0:P.userId,x=Y(),_=Z(),u=d(null),c=d(null),m=d(0),p=d(""),g=d([]);d(null);const R=d([]),y=d(null),h=d([null,null,null]),S=async(o,e)=>{let s;e==="product"?s=`lookkit/products/0${o.productId}/${o.productId}_thumbnail.webp`:e==="codi"&&(s=`lookkit/codi/0${o.codiId}/${o.codiId}_thumbnail.webp`),console.log("이미지 경로 확인:",s);try{const l=q(E,s),a=await j(l);o.thumbnailUrl=a}catch(l){console.error(`이미지 가져오기 실패: ${s}`,l),o.thumbnailUrl=null}};Q(()=>{const o=x.query.productId,e=x.query.codiId;o?W(Number(o),I.user.userId):e?z(Number(e),I.user.userId):(alert("리뷰를 작성할 상품 또는 코디 정보가 없습니다."),_.push("/mypage/manage"))});const W=async(o,e)=>{try{if((await w.get(`/api/reviews/list/${e}`)).data.find(v=>v.productId===o)){C().showModal("리뷰 작성 제한","이미 해당 상품에 대한 리뷰를 작성하셨습니다.","주문 관리 페이지로 이동합니다.","확인"),_.push("/mypage/manage");return}const f=await w.get(`/api/products/${o}`);u.value=f.data,await S(u.value)}catch(s){console.error("상품 정보를 가져오는 중 오류 발생:",s)}},z=async(o,e)=>{try{if((await w.get(`/api/reviews/list/${e}`)).data.find(v=>v.codiId===o)){C().showModal("리뷰 작성 제한","이미 해당 코디에 대한 리뷰를 작성하셨습니다.","주문 관리 페이지로 이동합니다.","확인"),_.push("/mypage/manage");return}const f=await w.get(`/api/codi/${o}`);c.value=f.data,await S(c.value,"codi")}catch(s){console.error("코디 정보를 가져오는 중 오류 발생:",s)}},$=o=>{const e=Array.from(o.target.parentNode.children).indexOf(o.target)+1;m.value=e},F=X(()=>{switch(m.value){case 1:return"별로에요";case 2:return"그저 그래요";case 3:return"괜찮아요";case 4:return"좋아요";case 5:return"최고에요";default:return""}}),M=o=>{const e=R.value[o];e&&e.click()},U=()=>{y.value&&y.value.click()},D=(o,e)=>{const s=o.target.files[0];s&&(g.value[e]=s,h.value[e]=URL.createObjectURL(s))},N=o=>{const e=Array.from(o.target.files);if(e.length>3){alert("사진은 최대 3개까지만 업로드 가능합니다.");return}g.value=[],h.value=[null,null,null],e.forEach((s,l)=>{l<3&&(g.value[l]=s,h.value[l]=URL.createObjectURL(s))})},J=async()=>{const o=[];for(let e=0;e<g.value.length;e++)if(g.value[e]){const s=g.value[e],l=`${Date.now()}-${s.name}`,a=q(E,`uploads/reviews/${l}`);try{if(s.size>5*1024*1024)throw new Error(`파일 ${s.name}이 5MB를 초과합니다`);const f=await uploadBytes(a,s),v=await j(a);o.push(v)}catch(f){console.error(`Error uploading image ${s.name}:`,f)}}return o},L=()=>{ee().showModal("리뷰 작성","이렇게 리뷰를 작성하시겠습니까?","작성된 리뷰는 다시 볼 수 있습니다.","작성하기",G)},G=async()=>{if(m.value===0||p.value.length<5){alert("별점과 리뷰 내용을 모두 입력해주세요. 리뷰는 최소 5자 이상 작성해주세요.");return}try{const o=await J(),e={userId:A,rating:m.value,reviewText:p.value,imageUrls:o};if(u.value)e.productId=u.value.productId;else if(c.value)e.codiId=c.value.codiId;else{alert("리뷰 작성할 상품이나 코디가 없습니다.");return}const s=new FormData;s.append("reviewDTO",new Blob([JSON.stringify(e)],{type:"application/json"})),await w.post("/api/reviews/write",s,{headers:{"Content-Type":"multipart/form-data"}}),C().showModal("리뷰 작성","리뷰 작성이 완료되었습니다."),_.push("/mypage/review")}catch(o){console.error("리뷰 작성 실패:",o),alert("리뷰 작성에 실패했습니다. 다시 시도해주세요.")}};return(o,e)=>(n(),i("div",te,[e[20]||(e[20]=t("h1",{class:"title"},"리뷰 작성하기",-1)),u.value?(n(),i("section",se,[e[11]||(e[11]=t("h2",{class:"subtitle"},"상품은 어떠셨나요?",-1)),t("div",oe,[t("img",{src:u.value.thumbnailUrl||"/images/placeholder.png",alt:"상품 이미지",class:"product-image"},null,8,le),t("div",null,[t("p",ae,r(u.value.brandName),1),t("p",ie,r(u.value.productName),1),t("p",ne,r(u.value.productPrice.toLocaleString())+"원 / 수량 "+r(u.value.quantity)+"개 ",1)])]),t("div",re,[t("div",{class:"stars",onClick:e[0]||(e[0]=s=>$(s))},[(n(),i(b,null,k(5,s=>t("img",{key:s,src:s<=m.value?"/images/full_star.png":"/images/empty_star.png",alt:"별 "+s,class:"star"},null,8,ue)),64))]),t("p",ce,r(F.value),1)]),t("section",de,[e[5]||(e[5]=t("h2",{class:"subtitle"},"대여한 상품의 리뷰를 남겨주세요",-1)),e[6]||(e[6]=t("p",{class:"subtitle-detail"},"본문 입력(필수)",-1)),B(t("textarea",{class:"review-text","onUpdate:modelValue":e[1]||(e[1]=s=>p.value=s),placeholder:"리뷰 작성란",minlength:"20"},null,512),[[T,p.value]]),t("div",pe,[e[4]||(e[4]=t("p",{class:"text-limit"},"5자 이상",-1)),t("p",ve,r(p.value.length)+" / 500",1)])]),t("section",me,[e[9]||(e[9]=t("h2",{class:"subtitle"},"사진 첨부",-1)),e[10]||(e[10]=t("p",{class:"photo-limit"},"사진 첨부는 3장까지만 가능합니다.",-1)),t("div",ge,[(n(!0),i(b,null,k(h.value,(s,l)=>(n(),i("div",{key:l,class:"image-placeholder",onClick:a=>M(l)},[t("input",{ref_for:!0,ref:a=>R.value[l]=a,style:{display:"none"},type:"file",accept:"image/*",onChange:a=>D(a,l)},null,40,he),s?(n(),i("img",{key:1,src:s},null,8,_e)):(n(),i("div",we,"+"))],8,fe))),128)),t("div",{class:"upload-button",onClick:U},[t("input",{type:"file",ref_key:"multipleFileInput",ref:y,style:{display:"none"},accept:"image/*",multiple:"",onChange:N},null,544),e[7]||(e[7]=t("img",{class:"upload-icon",src:O},null,-1)),e[8]||(e[8]=t("span",{class:"upload-text"},"여러장 선택하기",-1))])])]),t("button",{onClick:L,class:"submit-button"}," 리뷰 작성 완료 ")])):V("",!0),c.value?(n(),i("section",ye,[e[19]||(e[19]=t("h2",{class:"subtitle"},"코디는 마음에 드셨나요?",-1)),t("div",be,[t("img",{src:c.value.thumbnailUrl||"/images/placeholder.png",alt:"코디 이미지",class:"product-image"},null,8,ke),t("div",null,[t("p",Ie,r(c.value.codiDescription),1),t("p",Re,"코디 ID: "+r(c.value.codiName),1),t("p",Ce,r(c.value.codiPrice.toLocaleString())+"원 ",1)])]),t("div",xe,[t("div",{class:"stars",onClick:e[2]||(e[2]=s=>$(s))},[(n(),i(b,null,k(5,s=>t("img",{key:s,src:s<=m.value?"/images/full_star.png":"/images/empty_star.png",alt:"별 "+s,class:"star"},null,8,Se)),64))]),t("p",$e,r(F.value),1)]),t("section",Fe,[e[13]||(e[13]=t("h2",{class:"subtitle"},"대여한 상품의 리뷰를 남겨주세요",-1)),e[14]||(e[14]=t("p",{class:"subtitle-detail"},"본문 입력(필수)",-1)),B(t("textarea",{class:"review-text","onUpdate:modelValue":e[3]||(e[3]=s=>p.value=s),placeholder:"리뷰 작성란",minlength:"20"},null,512),[[T,p.value]]),t("div",Me,[e[12]||(e[12]=t("p",{class:"text-limit"},"5자 이상",-1)),t("p",Ue,r(p.value.length)+" / 500",1)])]),t("section",De,[e[17]||(e[17]=t("h2",{class:"subtitle"},"사진 첨부",-1)),e[18]||(e[18]=t("p",{class:"photo-limit"},"사진 첨부는 3장까지만 가능합니다.",-1)),t("div",Ne,[(n(!0),i(b,null,k(h.value,(s,l)=>(n(),i("div",{key:l,class:"image-placeholder",onClick:a=>M(l)},[t("input",{ref_for:!0,ref:a=>R.value[l]=a,style:{display:"none"},type:"file",accept:"image/*",onChange:a=>D(a,l)},null,40,Pe),s?(n(),i("img",{key:1,src:s},null,8,Te)):(n(),i("div",Be,"+"))],8,Le))),128)),t("div",{class:"upload-button",onClick:U},[t("input",{type:"file",ref_key:"multipleFileInput",ref:y,style:{display:"none"},accept:"image/*",multiple:"",onChange:N},null,544),e[15]||(e[15]=t("img",{class:"upload-icon",src:O},null,-1)),e[16]||(e[16]=t("span",{class:"upload-text"},"여러장 선택하기",-1))])])]),t("button",{onClick:L,class:"submit-button"}," 리뷰 작성 완료 ")])):V("",!0)]))}},We=H(Ve,[["__scopeId","data-v-b9d65d8d"]]);export{We as default};
