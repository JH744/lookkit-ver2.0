import{r as i,f as d,u as p}from"./firebaseConfig-Cr89vwH1.js";import{_ as u,b as c,d as r,t as f,i as m}from"./index-Cm_Q1rqZ.js";const _={data(){return{uploadProgress:0,currentFile:""}},methods:{async handleFileUpload(t){const e=t.target.files[0];if(!e)return;this.currentFile=e.name;const s=i(d,`uploads/${e.name}`);try{await p(s,e),this.uploadProgress=100,this.currentFile="업로드 완료: "+e.name}catch(a){console.error("파일 업로드 실패",a),this.currentFile="업로드 실패"}}}},g={id:"app"},h=["value"];function F(t,e,s,a,o,l){return m(),c("div",g,[e[1]||(e[1]=r("h1",null,"파일 업로드",-1)),r("input",{type:"file",onChange:e[0]||(e[0]=(...n)=>l.handleFileUpload&&l.handleFileUpload(...n))},null,32),r("progress",{value:o.uploadProgress,max:"100"},null,8,h),r("p",null,"현재 파일: "+f(o.currentFile),1)])}const v=u(_,[["render",F],["__scopeId","data-v-9b3b956c"]]);export{v as default};
