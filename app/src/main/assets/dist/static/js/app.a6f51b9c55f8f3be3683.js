webpackJsonp([1],{NHnr:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n("7+uW"),s={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"app"}},[e("p",[this._v("测试页面，如果路由对了下面会有内容")]),this._v(" "),e("p",[this._v("=====================================")]),this._v(" "),e("router-view")],1)},staticRenderFns:[]};var i=n("VU/8")({name:"App"},s,!1,function(t){n("dACf")},null,null).exports,r=n("YaEn");a.a.config.productionTip=!1,new a.a({el:"#app",router:r.a,components:{App:i},template:"<App/>"})},ScRE:function(t,e,n){"use strict";var a=n("mvHQ"),s=n.n(a);var i={postjson:function(t,e,n){var a=new XMLHttpRequest;a.open("POST",t,!0),a.onreadystatechange=function(){4==a.readyState&&(200==a.status?n(200,JSON.parse(a.responseText)):n(a.status,null))},a.send(s()(e))}},r={name:"error_page",data:function(){return{}},methods:{onClickClosed:function(){i.postjson("https://qsafe.desktop/window/close",{},null)}}},c={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",[e("p",{staticStyle:{"font-size":"20px"}},[this._v("this is A/B/C Page")]),this._v(" "),e("img",{staticStyle:{width:"100%"},attrs:{src:"/static/jsy.jpeg"}})])}]};var o=n("VU/8")(r,c,!1,function(t){n("yLl4")},"data-v-7ed24566",null);e.a=o.exports},VLFt:function(t,e,n){"use strict";var a={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",[e("p",{staticStyle:{"font-size":"20px"}},[this._v("that is A/B/D Page")]),this._v(" "),e("img",{staticStyle:{width:"100%"},attrs:{src:"/static/aaa.webp"}})])}]};var s=n("VU/8")({name:"loading_page",data:function(){return{}}},a,!1,function(t){n("Z7hI")},"data-v-5c709d36",null);e.a=s.exports},YaEn:function(t,e,n){"use strict";(function(t){var a=n("7+uW"),s=n("/ocq"),i=n("ScRE"),r=n("VLFt");a.a.use(s.a),e.a=new s.a({mode:"history",base:t,routes:[{path:"/a/b/c",name:"ErrorPage",component:i.a},{path:"/a/b/d",name:"LoadingPage",component:r.a}]})}).call(e,"/")},Z7hI:function(t,e){},dACf:function(t,e){},yLl4:function(t,e){}},["NHnr"]);
//# sourceMappingURL=app.a6f51b9c55f8f3be3683.js.map