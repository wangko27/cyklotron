(function(){var g=true,h=null,j=false,aa=(new Date).getTime(),k=function(a){var b=(new Date).getTime()-aa;b="&dtd="+(b<1000?b:"M");return a+b};var l=this,ba=function(a,b,c){a=a.split(".");c=c||l;!(a[0]in c)&&c.execScript&&c.execScript("var "+a[0]);for(var d;a.length&&(d=a.shift());)if(!a.length&&b!==undefined)c[d]=b;else c=c[d]?c[d]:(c[d]={})},n=function(a){var b=typeof a;if(b=="object")if(a){if(a instanceof Array||!(a instanceof Object)&&Object.prototype.toString.call(a)=="[object Array]"||typeof a.length=="number"&&typeof a.splice!="undefined"&&typeof a.propertyIsEnumerable!="undefined"&&!a.propertyIsEnumerable("splice"))return"array";
if(!(a instanceof Object)&&(Object.prototype.toString.call(a)=="[object Function]"||typeof a.call!="undefined"&&typeof a.propertyIsEnumerable!="undefined"&&!a.propertyIsEnumerable("call")))return"function"}else return"null";else if(b=="function"&&typeof a.call=="undefined")return"object";return b},o=function(a){return n(a)=="array"},ca=function(a){var b=n(a);return b=="array"||b=="object"&&typeof a.length=="number"},p=function(a){return typeof a=="string"},da=function(a){a=n(a);return a=="object"||
a=="array"||a=="function"};Math.floor(Math.random()*2147483648).toString(36);
var ea=function(a){var b=n(a);if(b=="object"||b=="array"){if(a.clone)return a.clone.call(a);b=b=="array"?[]:{};for(var c in a)b[c]=ea(a[c]);return b}return a},fa=function(a,b){var c=b||l;if(arguments.length>2){var d=Array.prototype.slice.call(arguments,2);return function(){var e=Array.prototype.slice.call(arguments);Array.prototype.unshift.apply(e,d);return a.apply(c,e)}}else return function(){return a.apply(c,arguments)}},ga=Date.now||function(){return(new Date).getTime()},q=function(a,b,c){ba(a,
b,c)};var ha=function(a,b,c){if(a.forEach)a.forEach(b,c);else if(Array.forEach)Array.forEach(a,b,c);else for(var d=a.length,e=p(a)?a.split(""):a,f=0;f<d;f++)f in e&&b.call(c,e[f],f,a)},ia=function(a){if(o(a))return a.concat();else{for(var b=[],c=0,d=a.length;c<d;c++)b[c]=a[c];return b}};var r=function(a,b){this.x=a!==undefined?a:0;this.y=b!==undefined?b:0};r.prototype.clone=function(){return new r(this.x,this.y)};r.prototype.toString=function(){return"("+this.x+", "+this.y+")"};var s=function(a,b){this.width=a;this.height=b};s.prototype.clone=function(){return new s(this.width,this.height)};s.prototype.toString=function(){return"("+this.width+" x "+this.height+")"};s.prototype.ceil=function(){this.width=Math.ceil(this.width);this.height=Math.ceil(this.height);return this};s.prototype.floor=function(){this.width=Math.floor(this.width);this.height=Math.floor(this.height);return this};
s.prototype.round=function(){this.width=Math.round(this.width);this.height=Math.round(this.height);return this};s.prototype.scale=function(a){this.width*=a;this.height*=a;return this};var ja=function(a,b,c){for(var d in a)b.call(c,a[d],d,a)};var ka=function(a){return a.replace(/^[\s\xa0]+|[\s\xa0]+$/g,"")},qa=function(a,b){if(b)return a.replace(la,"&amp;").replace(ma,"&lt;").replace(na,"&gt;").replace(oa,"&quot;");else{if(!pa.test(a))return a;if(a.indexOf("&")!=-1)a=a.replace(la,"&amp;");if(a.indexOf("<")!=-1)a=a.replace(ma,"&lt;");if(a.indexOf(">")!=-1)a=a.replace(na,"&gt;");if(a.indexOf('"')!=-1)a=a.replace(oa,"&quot;");return a}},la=/&/g,ma=/</g,na=/>/g,oa=/\"/g,pa=/[&<>\"]/,ta=function(a){if(u(a,"&"))return"document"in l&&!u(a,"<")?
ra(a):sa(a);return a},ra=function(a){var b=l.document.createElement("a");b.innerHTML=a;b.normalize&&b.normalize();a=b.firstChild.nodeValue;b.innerHTML="";return a},sa=function(a){return a.replace(/&([^;]+);/g,function(b,c){switch(c){case "amp":return"&";case "lt":return"<";case "gt":return">";case "quot":return'"';default:if(c.charAt(0)=="#"){c=Number("0"+c.substr(1));if(!isNaN(c))return String.fromCharCode(c)}return b}})},ua=function(a,b){for(var c=b.length,d=0;d<c;d++){var e=c==1?b:b.charAt(d);
if(a.charAt(0)==e&&a.charAt(a.length-1)==e)return a.substring(1,a.length-1)}return a},u=function(a,b){return a.indexOf(b)!=-1},wa=function(a,b){var c=0;a=ka(String(a)).split(".");b=ka(String(b)).split(".");for(var d=Math.max(a.length,b.length),e=0;c==0&&e<d;e++){var f=a[e]||"",i=b[e]||"",m=new RegExp("(\\d*)(\\D*)","g"),t=new RegExp("(\\d*)(\\D*)","g");do{var A=m.exec(f)||["","",""],B=t.exec(i)||["","",""];if(A[0].length==0&&B[0].length==0)break;c=A[1].length==0?0:parseInt(A[1],10);var zb=B[1].length==
0?0:parseInt(B[1],10);c=va(c,zb)||va(A[2].length==0,B[2].length==0)||va(A[2],B[2])}while(c==0)}return c},va=function(a,b){if(a<b)return-1;else if(a>b)return 1;return 0};ga();var v,xa,w,ya,za,Aa,Ba,Ca,Da,Ea,Fa=function(){return l.navigator?l.navigator.userAgent:h},x=function(){return l.navigator},Ga=function(){Aa=za=ya=w=xa=v=j;var a;if(a=Fa()){var b=x();v=a.indexOf("Opera")==0;xa=!v&&a.indexOf("MSIE")!=-1;ya=(w=!v&&a.indexOf("WebKit")!=-1)&&a.indexOf("Mobile")!=-1;Aa=(za=!v&&!w&&b.product=="Gecko")&&b.vendor=="Camino"}};Ga();
var y=v,z=xa,Ha=za,C=w,Ia=ya,Ja=function(){var a=x();return a&&a.platform||""},Ka=Ja(),La=function(){Ba=u(Ka,"Mac");Ca=u(Ka,"Win");Da=u(Ka,"Linux");Ea=!!x()&&u(x().appVersion||"","X11")};La();
var Ma=Ba,Na=Ca,Oa=Da,Pa=function(){var a="",b;if(y&&l.opera){a=l.opera.version;a=typeof a=="function"?a():a}else{if(Ha)b=/rv\:([^\);]+)(\)|;)/;else if(z)b=/MSIE\s+([^\);]+)(\)|;)/;else if(C)b=/WebKit\/(\S+)/;if(b)a=(a=b.exec(Fa()))?a[1]:""}return a},Qa=Pa(),Ra={},D=function(a){return Ra[a]||(Ra[a]=wa(Qa,a)>=0)};var E;var Sa=function(a){return p(a)?document.getElementById(a):a},Ta=Sa,Va=function(a,b){ja(b,function(c,d){if(d=="style")a.style.cssText=c;else if(d=="class")a.className=c;else if(d=="for")a.htmlFor=c;else if(d in Ua)a.setAttribute(Ua[d],c);else a[d]=c})},Ua={cellpadding:"cellPadding",cellspacing:"cellSpacing",colspan:"colSpan",rowspan:"rowSpan",valign:"vAlign",height:"height",width:"width",usemap:"useMap",frameborder:"frameBorder",type:"type"},Wa=function(a){var b=a.document;if(C&&!D("500")&&!Ia){if(typeof a.innerHeight==
"undefined")a=window;b=a.innerHeight;var c=a.document.documentElement.scrollHeight;if(a==a.top)if(c<b)b-=15;return new s(a.innerWidth,b)}a=b.compatMode=="CSS1Compat"&&(!y||y&&D("9.50"))?b.documentElement:b.body;return new s(a.clientWidth,a.clientHeight)},Xa=function(a){a=!C&&a.compatMode=="CSS1Compat"?a.documentElement:a.body;return new r(a.scrollLeft,a.scrollTop)},Za=function(){return Ya(document,arguments)},Ya=function(a,b){var c=b[0],d=b[1];if(z&&d&&(d.name||d.type)){c=["<",c];d.name&&c.push(' name="',
qa(d.name),'"');if(d.type){c.push(' type="',qa(d.type),'"');d=ea(d);delete d.type}c.push(">");c=c.join("")}var e=a.createElement(c);if(d)if(p(d))e.className=d;else Va(e,d);if(b.length>2){function f(i){if(i)e.appendChild(p(i)?a.createTextNode(i):i)}for(d=2;d<b.length;d++){c=b[d];ca(c)&&!(da(c)&&c.nodeType>0)?ha($a(c)?ia(c):c,f):f(c)}}return e},ab=function(a,b){a.appendChild(b)},bb=function(a){return a&&a.parentNode?a.parentNode.removeChild(a):h},cb=function(a,b){var c=b.parentNode;c&&c.replaceChild(a,
b)},db=C&&D("522"),eb=function(a,b){if(typeof a.contains!="undefined"&&!db&&b.nodeType==1)return a==b||a.contains(b);if(typeof a.compareDocumentPosition!="undefined")return a==b||Boolean(a.compareDocumentPosition(b)&16);for(;b&&a!=b;)b=b.parentNode;return b==a},F=function(a){return a.nodeType==9?a:a.ownerDocument||a.document},$a=function(a){if(a&&typeof a.length=="number")if(da(a))return typeof a.item=="function"||typeof a.item=="string";else if(n(a)=="function")return typeof a.item=="function";return j},
G=function(a){this.a=a||l.document||document};G.prototype.createElement=function(a){return this.a.createElement(a)};G.prototype.createTextNode=function(a){return this.a.createTextNode(a)};G.prototype.o=function(){return this.a.compatMode=="CSS1Compat"};G.prototype.n=function(){return Xa(this.a)};G.prototype.appendChild=ab;G.prototype.removeNode=bb;G.prototype.replaceNode=cb;G.prototype.contains=eb;function H(a,b){a=parseFloat(a);return isNaN(a)||a>1||a<0?b:a}function I(a,b){if(a=="true")return g;if(a=="false")return j;return b}function J(a,b){var c=/^([\w-]+\.)+[\w-]{2,}(\:[0-9]+)?$/;return c.test(a)?a:b};var fb="pagead2.googlesyndication.com",gb="googleads.g.doubleclick.net",hb="pubads.g.doubleclick.net",ib="securepubads.g.doubleclick.net",jb="partner.googleadservices.com",K=J("pagead2.googlesyndication.com",fb),kb=J("googleads.g.doubleclick.net",gb),lb=J("pagead2.googlesyndication.com",fb);J("pubads.g.doubleclick.net",hb);J("partner.googleadservices.com",jb);J("securepubads.g.doubleclick.net",ib);function L(a){return typeof encodeURIComponent=="function"?encodeURIComponent(a):escape(a)}function mb(a,b,c){var d=document.createElement("script");d.type="text/javascript";if(b)d.onload=b;if(c)d.id=c;d.src=a;var e=document.getElementsByTagName("head")[0];if(!e)return j;window.setTimeout(function(){e.appendChild(d)},0);return g}function nb(a,b){a.google_image_requests||(a.google_image_requests=[]);var c=new Image;c.src=b;a.google_image_requests.push(c)}
function ob(a){if(a in pb)return pb[a];return pb[a]=navigator.userAgent.toLowerCase().indexOf(a)!=-1}var pb={};
function qb(){if(navigator.plugins&&navigator.mimeTypes.length){var a=navigator.plugins["Shockwave Flash"];if(a&&a.description)return a.description.replace(/([a-zA-Z]|\s)+/,"").replace(/(\s)+r/,".")}else if(navigator.userAgent&&navigator.userAgent.indexOf("Windows CE")>=0){a=3;for(var b=1;b;)try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+(a+1));a++}catch(c){b=h}return a.toString()}else if(ob("msie")&&!window.opera){b=h;try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7")}catch(d){a=
0;try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");a=6;b.AllowScriptAccess="always"}catch(e){if(a==6)return a.toString()}try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash")}catch(f){}}if(b){a=b.GetVariable("$version").split(" ")[1];return a.replace(/,/g,".")}}return"0"}function M(a){var b=a.google_ad_format;if(b)return b.indexOf("_0ads")>0;return a.google_ad_output!="html"&&a.google_num_radlinks>0}function N(a){return!!a&&a.indexOf("_sdo")!=-1}
function O(a,b){var c=Math.random();if(c<b){b=Math.floor(c/b*a.length);return a[b]}return""}
var rb=function(a){a.u_tz=-(new Date).getTimezoneOffset();a.u_his=window.history.length;a.u_java=navigator.javaEnabled();if(window.screen){a.u_h=window.screen.height;a.u_w=window.screen.width;a.u_ah=window.screen.availHeight;a.u_aw=window.screen.availWidth;a.u_cd=window.screen.colorDepth}if(navigator.plugins)a.u_nplug=navigator.plugins.length;if(navigator.mimeTypes)a.u_nmime=navigator.mimeTypes.length},sb=function(a){if(a.body)try{return Wa(window)}catch(b){return new s(-12245933,-12245933)}else return new s(-1,
-1)};var tb={google_ad_channel:"channel",google_ad_host:"host",google_ad_host_channel:"h_ch",google_ad_host_tier_id:"ht_id",google_ad_section:"region",google_ad_type:"ad_type",google_adtest:"adtest",google_allow_expandable_ads:"ea",google_alternate_ad_url:"alternate_ad_url",google_alternate_color:"alt_color",google_bid:"bid",google_city:"gcs",google_color_bg:"color_bg",google_color_border:"color_border",google_color_line:"color_line",google_color_link:"color_link",google_color_text:"color_text",google_color_url:"color_url",
google_contents:"contents",google_country:"gl",google_cust_age:"cust_age",google_cust_ch:"cust_ch",google_cust_gender:"cust_gender",google_cust_id:"cust_id",google_cust_interests:"cust_interests",google_cust_job:"cust_job",google_cust_l:"cust_l",google_cust_lh:"cust_lh",google_cust_u_url:"cust_u_url",google_disable_video_autoplay:"disable_video_autoplay",google_ed:"ed",google_encoding:"oe",google_feedback:"feedback_link",google_flash_version:"flash",google_font_face:"f",google_font_size:"fs",google_hints:"hints",
google_kw:"kw",google_kw_type:"kw_type",google_language:"hl",google_page_url:"url",google_referrer_url:"ref",google_region:"gr",google_reuse_colors:"reuse_colors",google_safe:"adsafe",google_tag_info:"gut",google_targeting:"targeting",google_ui_features:"ui",google_ui_version:"uiv",google_video_doc_id:"video_doc_id",google_video_product_type:"video_product_type"},ub={google_ad_client:"client",google_ad_format:"format",google_ad_output:"output",google_ad_callback:"callback",google_ad_height:"h",google_ad_override:"google_ad_override",
google_ad_slot:"slotname",google_ad_width:"w",google_ctr_threshold:"ctr_t",google_image_size:"image_size",google_last_modified_time:"lmt",google_max_num_ads:"num_ads",google_max_radlink_len:"max_radlink_len",google_num_radlinks:"num_radlinks",google_num_radlinks_per_unit:"num_radlinks_per_unit",google_only_ads_with_video:"only_ads_with_video",google_page_location:"loc",google_rl_dest_url:"rl_dest_url",google_rl_filtering:"rl_filtering",google_rl_mode:"rl_mode",google_rt:"rt",google_skip:"skip"},vb=
{google_only_pyv_ads:"pyv",google_with_pyv_ads:"withpyv"};function wb(a,b){try{return a.top.document.URL==b.URL}catch(c){}return j}function xb(a,b,c,d){c=c||a.google_ad_width;d=d||a.google_ad_height;if(wb(a,b))return j;var e=b.documentElement;if(c&&d){var f=1,i=1;if(a.innerHeight){f=a.innerWidth;i=a.innerHeight}else if(e&&e.clientHeight){f=e.clientWidth;i=e.clientHeight}else if(b.body){f=b.body.clientWidth;i=b.body.clientHeight}if(i>2*d||f>2*c)return j}return g}function yb(a,b){for(var c in b)a["google_"+c]=b[c]}
function Ab(a,b){if(!b)return a.URL;return a.referrer}function Bb(a,b){if(!b&&a.google_referrer_url==h)return"0";else if(b&&a.google_referrer_url==h)return"1";else if(!b&&a.google_referrer_url!=h)return"2";else if(b&&a.google_referrer_url!=h)return"3";return"4"}function Cb(a,b,c,d){a.page_url=Ab(c,d);a.page_location=h}function Db(a,b,c,d){a.page_url=b.google_page_url;a.page_location=Ab(c,d)||"EMPTY"}
function Eb(a,b){var c={},d=xb(a,b,a.google_ad_width,a.google_ad_height);c.iframing=Bb(a,d);a.google_page_url?Db(c,a,b,d):Cb(c,a,b,d);c.last_modified_time=b.URL==c.page_url?Date.parse(b.lastModified)/1000:h;c.referrer_url=d?a.google_referrer_url:a.google_page_url&&a.google_referrer_url?a.google_referrer_url:b.referrer;return c}function Fb(a){var b={},c=a.URL.substring(a.URL.lastIndexOf("http"));b.iframing=h;b.page_url=c;b.page_location=a.URL;b.last_modified_time=h;b.referrer_url=c;return b}
function Gb(a,b){b=Hb(a,b);yb(a,b)}function Hb(a,b){return a=a.google_page_url==h&&Ib[b.domain]?Fb(b):Eb(a,b)}var Ib={};Ib["ad.yieldmanager.com"]=g;var Jb=document,Kb=I("true",j),Lb=I("true",j),Mb=I("false",j);I("false",j);var P=window;var Nb=function(a,b,c){b=fa(b,l,a);a=window.onerror;window.onerror=b;try{c()}catch(d){c=d.toString();var e="";if(d.fileName)e=d.fileName;var f=-1;if(d.lineNumber)f=d.lineNumber;b=b(c,e,f);if(!b)throw d;}window.onerror=a};q("google_protectAndRun",Nb);
var Pb=function(a,b,c,d){if(Math.random()<0.01){var e=Jb;a=["http://",lb,"/pagead/gen_204","?id=jserror","&jscb=",Kb?1:0,"&jscd=",Lb?1:0,"&context=",L(a),"&msg=",L(b),"&file=",L(c),"&line=",L(d.toString()),"&url=",L(e.URL.substring(0,512)),"&ref=",L(e.referrer.substring(0,512))];a.push(Ob());nb(P,a.join(""))}return!Mb};q("google_handleError",Pb);
var Rb=function(a){Qb|=a},Qb=0,Ob=function(){var a=["&client=",L(P.google_ad_client),"&format=",L(P.google_ad_format),"&slotname=",L(P.google_ad_slot),"&output=",L(P.google_ad_output),"&ad_type=",L(P.google_ad_type)];return a.join("")};var Q="",Ub=function(){if(window.google_ad_frameborder==h)window.google_ad_frameborder=0;if(window.google_ad_output==h)window.google_ad_output="html";if(N(window.google_ad_format)){var a=window.google_ad_format.match(/^(\d+)x(\d+)_.*/);if(a){window.google_ad_width=parseInt(a[1],10);window.google_ad_height=parseInt(a[2],10);window.google_ad_output="html"}}window.google_ad_format=Sb(window.google_ad_format,window.google_ad_output,window.google_ad_width,window.google_ad_height,window.google_ad_slot,
!!window.google_override_format);Q=window.google_ad_client||"";window.google_ad_client=Tb(window.google_ad_format,window.google_ad_client);Gb(window,document);if(window.google_num_slots_by_channel==h)window.google_num_slots_by_channel={};if(window.google_viewed_host_channels==h)window.google_viewed_host_channels={};if(window.google_num_slots_by_client==h)window.google_num_slots_by_client={};if(window.google_prev_ad_formats_by_region==h)window.google_prev_ad_formats_by_region={};if(window.google_prev_ad_slotnames_by_region==
h)window.google_prev_ad_slotnames_by_region={};if(window.google_correlator==h)window.google_correlator=(new Date).getTime();if(window.google_adslot_loaded==h)window.google_adslot_loaded={};if(window.google_adContentsBySlot==h)window.google_adContentsBySlot={};if(window.google_flash_version==h)window.google_flash_version=qb();if(window.google_new_domain_checked==h)window.google_new_domain_checked=0;if(window.google_new_domain_enabled==h)window.google_new_domain_enabled=0;if(!window.google_num_ad_slots)window.google_num_ad_slots=
0;if(!window.google_num_0ad_slots)window.google_num_0ad_slots=0;if(!window.google_num_sdo_slots)window.google_num_sdo_slots=0;window.google_ad_section=window.google_ad_section||window.google_ad_region||"";window.google_country=window.google_country||window.google_gl||"";a=(new Date).getTime();if(o(window.google_color_bg))window.google_color_bg=R(window.google_color_bg,a);if(o(window.google_color_text))window.google_color_text=R(window.google_color_text,a);if(o(window.google_color_link))window.google_color_link=
R(window.google_color_link,a);if(o(window.google_color_url))window.google_color_url=R(window.google_color_url,a);if(o(window.google_color_border))window.google_color_border=R(window.google_color_border,a);if(o(window.google_color_line))window.google_color_line=R(window.google_color_line,a)},Vb=function(a){var b;for(b in tb)a[b]=h;for(b in ub)a[b]=h;for(b in vb)a[b]=h;a.google_container_id=h;a.google_eids=h;a.google_ad_region=h;a.google_gl=h},R=function(a,b){Rb(2);return a[b%a.length]},Tb=function(a,
b){if(!b)return"";b=b.toLowerCase();return b=N(a)?Wb(b):Xb(b)},Xb=function(a){if(a&&a.substring(0,3)!="ca-")a="ca-"+a;return a},Wb=function(a){if(a&&a.substring(0,9)!="dist-aff-")a="dist-aff-"+a;return a},Sb=function(a,b,c,d,e,f){if(!a&&b=="html")a=c+"x"+d;return a=Yb(a,e,f)?a.toLowerCase():""},Yb=function(a,b,c){if(!a)return j;if(!b)return g;return c};var S=document,T=navigator,U=window;
function Zb(){var a=S.cookie,b=Math.round((new Date).getTime()/1000),c=U.google_analytics_domain_name;c=typeof c=="undefined"?$b("auto"):$b(c);var d=a.indexOf("__utma="+c+".")>-1,e=a.indexOf("__utmb="+c)>-1,f=a.indexOf("__utmc="+c)>-1,i={},m=!!U&&!!U.gaGlobal;if(d){a=a.split("__utma="+c+".")[1].split(";")[0].split(".");i.sid=e&&f?a[3]+"":m&&U.gaGlobal.sid?U.gaGlobal.sid:b+"";i.vid=a[0]+"."+a[1];i.from_cookie=g}else{i.sid=m&&U.gaGlobal.sid?U.gaGlobal.sid:b+"";i.vid=m&&U.gaGlobal.vid?U.gaGlobal.vid:
(Math.round(Math.random()*2147483647)^ac()&2147483647)+"."+b;i.from_cookie=j}i.dh=c;i.hid=m&&U.gaGlobal.hid?U.gaGlobal.hid:Math.round(Math.random()*2147483647);return U.gaGlobal=i}
function ac(){var a=S.cookie?S.cookie:"",b=U.history.length,c,d=[T.appName,T.version,T.language?T.language:T.browserLanguage,T.platform,T.userAgent,T.javaEnabled()?1:0].join("");if(U.screen)d+=U.screen.width+"x"+U.screen.height+U.screen.colorDepth;else if(U.java){c=java.awt.Toolkit.getDefaultToolkit().getScreenSize();d+=c.screen.width+"x"+c.screen.height}d+=a;d+=S.referrer?S.referrer:"";for(a=d.length;b>0;)d+=b--^a++;return bc(d)}
function bc(a){var b=1,c=0,d;if(!(a==undefined||a=="")){b=0;for(d=a.length-1;d>=0;d--){c=a.charCodeAt(d);b=(b<<6&268435455)+c+(c<<14);c=b&266338304;b=c!=0?b^c>>21:b}}return b}function $b(a){if(!a||a==""||a=="none")return 1;if("auto"==a){a=S.domain;if("www."==a.substring(0,4))a=a.substring(4,a.length)}return bc(a.toLowerCase())};var cc={google:1,googlegroups:1,gmail:1,googlemail:1,googleimages:1,googleprint:1};function dc(a){a=a.google_page_location||a.google_page_url;if(!a)return j;a=a.toString();if(a.indexOf("http://")==0)a=a.substring(7,a.length);else if(a.indexOf("https://")==0)a=a.substring(8,a.length);var b=a.indexOf("/");if(b==-1)b=a.length;a=a.substring(0,b);a=a.split(".");b=j;if(a.length>=3)b=a[a.length-3]in cc;if(a.length>=2)b=b||a[a.length-2]in cc;return b}
function ec(a,b,c){if(dc(a)){a.google_new_domain_checked=1;return j}if(a.google_new_domain_checked==0){var d=Math.random();if(d<=c){c="http://"+kb+"/pagead/test_domain.js";d="script";b.write("<"+d+' src="'+c+'"></'+d+">");a.google_new_domain_checked=1;return g}}return j}function fc(a){if(!dc(a)&&a.google_new_domain_enabled==1)return"http://"+kb;return"http://"+lb};document.URL&&(document.URL.indexOf("?google_debug")>0||document.URL.indexOf("&google_debug")>0);var V=function(a){this.w=a;this.h=[];this.g=0;this.b=[];this.s=0;this.d=[];this.q=j;this.i=this.j="";this.p=j};V.prototype.u=function(a,b){var c=this.w[b],d=this.h;this.w[b]=function(e){if(e&&e.length>0){var f=e.length>1?e[1].url:h;d.push([a,ta(e[0].url),f])}c(e)}};V.prototype.t=function(){this.g++};V.prototype.v=function(a){this.b.push(a)};V.prototype.r=function(){if(!this.q){mb("http://"+K+"/pagead/osd.js");this.q=g}};
V.prototype.k=function(a){if(this.g>0)for(var b=document.getElementsByTagName("iframe"),c=this.p?"google_ads_iframe_":"google_ads_frame",d=0;d<b.length;d++){var e=b.item(d);e.src&&e.name&&e.name.indexOf(c)==0&&a(e,e.src)}};
V.prototype.l=function(a){var b=this.h;if(b.length>0)for(var c=document.getElementsByTagName("a"),d=function(A,B){return A.innerHTML.indexOf(B)>0},e=0;e<c.length;e++)for(var f=0;f<b.length;f++)if(c.item(e).href==b[f][1]){var i=c.item(e).parentNode;if(b[f][2])for(var m=i,t=0;t<4;t++){if(d(m,b[f][2])){i=m;break}m=m.parentNode}a(i,b[f][0]);b.splice(f,1);break}};
V.prototype.m=function(a){for(var b=0;b<this.b.length;b++){var c=this.b[b],d=gc(c);if(d)(d=document.getElementById("google_ads_div_"+d))&&a(d,c)}};V.prototype.e=function(a){this.l(a);this.m(a);this.k(a)};V.prototype.setupOsd=function(a,b,c){this.s=a;this.j=b;this.i=c};V.prototype.getOsdMode=function(){return this.s};V.prototype.getEid=function(){return this.j};V.prototype.getCorrelator=function(){return this.i};V.prototype.f=function(){return this.h.length+this.g+this.b.length};
V.prototype.setValidAdBlockTypes=function(a){this.d=a};V.prototype.c=function(a,b,c){if(this.d.length>0){for(var d=0;d<this.d.length;d++)if(this.d[d]==a){this.p=c;if(a=="js")this.u(b,"google_ad_request_done");else if(a=="html")this.t();else a=="json_html"&&this.v(b)}this.r()}};var gc=function(a){if((a=a.match(/[&\?](?:slotname)=([^&]+)/))&&a.length==2)return a[1];return""},hc=function(){window.__google_ad_urls||(window.__google_ad_urls=new V(window));return window.__google_ad_urls};
q("Goog_AdSense_getAdAdapterInstance",hc);q("Goog_AdSense_OsdAdapter",V);q("Goog_AdSense_OsdAdapter.prototype.numBlocks",V.prototype.f);q("Goog_AdSense_OsdAdapter.prototype.findBlocks",V.prototype.e);q("Goog_AdSense_OsdAdapter.prototype.getOsdMode",V.prototype.getOsdMode);q("Goog_AdSense_OsdAdapter.prototype.getEid",V.prototype.getEid);q("Goog_AdSense_OsdAdapter.prototype.getCorrelator",V.prototype.getCorrelator);q("Goog_Adsense_OsdAdapter.prototype.setValidAdBlockTypes",V.setValidAdBlockTypes);
q("Goog_Adsense_OsdAdapter.prototype.setupOsd",V.prototype.setupOsd);q("Goog_Adsense_OsdAdapter.prototype.registerAdBlockByType",V.prototype.c);var ic,jc,kc,lc,mc,nc,oc=function(){nc=mc=lc=kc=jc=ic=j;var a=Fa();if(a)if(a.indexOf("Firefox")!=-1)ic=g;else if(a.indexOf("Camino")!=-1)jc=g;else if(a.indexOf("iPhone")!=-1||a.indexOf("iPod")!=-1)kc=g;else if(a.indexOf("Android")!=-1)lc=g;else if(a.indexOf("Chrome")!=-1)mc=g;else if(a.indexOf("Safari")!=-1)nc=g};oc();var pc=function(a,b){var c=F(a);if(c.defaultView&&c.defaultView.getComputedStyle)if(a=c.defaultView.getComputedStyle(a,""))return a[b];return h},W=function(a,b){return pc(a,b)||(a.currentStyle?a.currentStyle[b]:h)||a.style[b]},qc=function(a){a=a?a.nodeType==9?a:F(a):document;if(z&&!(a?new G(F(a)):E||(E=new G)).o())return a.body;return a.documentElement},rc=function(a){var b=a.getBoundingClientRect();if(z){a=a.ownerDocument;b.left-=a.documentElement.clientLeft+a.body.clientLeft;b.top-=a.documentElement.clientTop+
a.body.clientTop}return b},sc=function(a){if(z)return a.offsetParent;var b=F(a),c=W(a,"position"),d=c=="fixed"||c=="absolute";for(a=a.parentNode;a&&a!=b;a=a.parentNode){c=W(a,"position");d=d&&c=="static"&&a!=b.documentElement&&a!=b.body;if(!d&&(a.scrollWidth>a.clientWidth||a.scrollHeight>a.clientHeight||c=="fixed"||c=="absolute"))return a}return h},tc=function(a){var b,c=F(a),d=W(a,"position"),e=Ha&&c.getBoxObjectFor&&!a.getBoundingClientRect&&d=="absolute"&&(b=c.getBoxObjectFor(a))&&(b.screenX<0||
b.screenY<0),f=new r(0,0),i=qc(c);if(a==i)return f;if(a.getBoundingClientRect){b=rc(a);a=(c?new G(F(c)):E||(E=new G)).n();f.x=b.left+a.x;f.y=b.top+a.y}else if(c.getBoxObjectFor&&!e){b=c.getBoxObjectFor(a);a=c.getBoxObjectFor(i);f.x=b.screenX-a.screenX;f.y=b.screenY-a.screenY}else{b=a;do{f.x+=b.offsetLeft;f.y+=b.offsetTop;if(b!=a){f.x+=b.clientLeft||0;f.y+=b.clientTop||0}if(C&&W(b,"position")=="fixed"){f.x+=c.body.scrollLeft;f.y+=c.body.scrollTop;break}b=b.offsetParent}while(b&&b!=a);if(y||C&&d=="absolute")f.y-=
c.body.offsetTop;for(b=a;(b=sc(b))&&b!=c.body&&b!=i;){f.x-=b.scrollLeft;if(!y||b.tagName!="TR")f.y-=b.scrollTop}}return f},uc=function(a,b,c,d){if(/^\d+px?$/.test(b))return parseInt(b,10);else{var e=a.style[c],f=a.runtimeStyle[c];a.runtimeStyle[c]=a.currentStyle[c];a.style[c]=b;b=a.style[d];a.style[c]=e;a.runtimeStyle[c]=f;return b}},vc=function(a){var b=F(a),c="";if(b.createTextRange){c=b.body.createTextRange();c.moveToElementText(a);c=c.queryCommandValue("FontName")}if(!c){c=W(a,"fontFamily");if(y&&
Oa)c=c.replace(/ \[[^\]]*\]/,"")}a=c.split(",");if(a.length>1)c=a[0];return ua(c,"\"'")},wc=/[^\d]+$/,xc=function(a){return(a=a.match(wc))&&a[0]||h},yc={cm:1,"in":1,mm:1,pc:1,pt:1},zc={em:1,ex:1},Ac=function(a){var b=W(a,"fontSize"),c=xc(b);if(b&&"px"==c)return parseInt(b,10);if(z)if(c in yc)return uc(a,b,"left","pixelLeft");else if(a.parentNode&&a.parentNode.nodeType==1&&c in zc){a=a.parentNode;c=W(a,"fontSize");return uc(a,b==c?"1em":b,"left","pixelLeft")}c=Za("span",{style:"visibility:hidden;position:absolute;line-height:0;padding:0;margin:0;border:0;height:1em;"});
ab(a,c);b=c.offsetHeight;bb(c);return b};var X={};function Bc(a){if(a==1)return g;return!X[a]}function Y(a,b){if(a)if(b==1)if(X[b])X[b]+=","+a;else X[b]=a;else X[b]=a}function Cc(){var a=[];for(var b in X)a.push(X[b]);return a.join(",")}function Dc(a,b){if(a&&a instanceof Array)for(var c=0;c<a.length;c++)a[c]&&typeof a[c]=="string"&&Y(a[c],b)}var Z=j;
function Ec(a,b){var c="script";Z=Fc(a,b);if(!Z)a.google_allow_expandable_ads=j;var d=!Gc();Z&&d&&b.write("<"+c+' src="http://'+K+'/pagead/expansion_embed.js"></'+c+">");a=ec(a,b,H("1",0.01));(d=d||a)&&ob("msie")&&!window.opera?b.write("<"+c+' src="http://'+K+'/pagead/render_ads.js"></'+c+">"):b.write("<"+c+'>google_protectAndRun("ads_core.google_render_ad", google_handleError, google_render_ad);</'+c+">")}function $(a){return a!=h?'"'+a+'"':'""'}
function Hc(a){var b="google_unique_id";if(a[b])++a[b];else a[b]=1;return a[b]}var Ic=function(a,b){var c=b.slice(-1);c=c=="?"||c=="#"?"":"&";b=[b];for(var d in a){var e=a[d];if(e||e===0||e===j){if(typeof e=="boolean")e=e?1:0;b.push(c,d,"=",L(e));c="&"}}return b.join("")};function Jc(){var a=z&&D("6"),b=Ha&&D("1.8.1"),c=C&&D("525");if(Na&&(a||b||c))return g;else if(Ma&&(c||b))return g;else if(Oa&&b)return g;return j}
function Gc(){return(typeof ExpandableAdSlotFactory=="function"||typeof ExpandableAdSlotFactory=="object")&&typeof ExpandableAdSlotFactory.createIframe=="function"}function Fc(a,b){if(a.google_allow_expandable_ads===j||!b.body||a.google_ad_output!="html"||xb(a,b)||!Kc(a)||isNaN(a.google_ad_height)||isNaN(a.google_ad_width)||!Jc())return j;return g}function Kc(a){var b=a.google_ad_format;if(N(b))return j;if(M(a)&&b!="468x15_0ads_al")return j;return g}
function Lc(){var a;if(P.google_ad_output=="html"&&!(M(P)||N(P.google_ad_format))&&Bc(0)){a=["6083035","6083034"];a=O(a,H("0.001",0));Y(a,0)}return a=="6083035"}function Mc(a,b){if((a.google_unique_id||0)==0&&a.google_ad_output=="html"&&document.body&&typeof b.body.getBoundingClientRect=="function")return O(["36812001","36812002"],H("0.01",0));return""}
function Nc(a,b){if((a.google_unique_id||0)!=0||N(a.google_ad_format))return"";var c="";a=M(a);if(b=="html"||a)c=O(["36815001","36815002"],H("0.006",0));if(c==""&&(b=="js"||a))c=O(["36815003","36815004"],H("0.006",0));if(c==""&&(b=="html"||b=="js"))c=O(["36813005","36813006"],H("0.008",0));return c}
function Oc(){var a=hc(),b=window.google_enable_osd,c="";if(b===g){c="36813006";Pc(c,a)}else if(b!==j&&Bc(0)){c=O(["68120011","68120021","68120031","68120041"],H("0",0))||Mc(window,document);if(c==""){c=Nc(window,window.google_ad_output);if(c!="")Pc(c,a);else c=a.getEid()}}return c}
function Pc(a,b){var c=b.getOsdMode(),d=[];switch(a){case "36815004":c=1;d=["js"];break;case "36815002":c=1;d=["html"];break;case "36813006":c=0;d=["html","js"];break}d.length>0&&b.setValidAdBlockTypes(d);b.setupOsd(c,a,window.google_correlator)}
function Qc(a,b,c,d){var e=Hc(a);c=Ic({ifi:e},c);c=c.substring(0,1992);c=c.replace(/%\w?$/,"");var f="script";if((a.google_ad_output=="js"||a.google_ad_output=="json_html")&&(a.google_ad_request_done||a.google_radlink_request_done))b.write("<"+f+' language="JavaScript1.1" src='+$(k(c))+"></"+f+">");else if(a.google_ad_output=="html")if(Z&&Gc()){b=a.google_container_id||d||h;a["google_expandable_ad_slot"+e]=ExpandableAdSlotFactory.createIframe("google_ads_frame"+e,k(c),a.google_ad_width,a.google_ad_height,
b)}else{e='<iframe name="google_ads_frame" width='+$(a.google_ad_width)+" height="+$(a.google_ad_height)+" frameborder="+$(a.google_ad_frameborder)+" src="+$(k(c))+' marginwidth="0" marginheight="0" vspace="0" hspace="0" allowtransparency="true" scrolling="no"></iframe>';a.google_container_id?Rc(a.google_container_id,b,e):b.write(e)}return c}function Sc(a){Vb(a)}function Tc(a,b){if(!Uc())return j;var c=Lc();a=Vc(a,b);c=fc(window)+Wc(a.google_ad_format,c);window.google_ad_url=Ic(a,c);return g}
var Zc=function(a){a.dt=aa;var b=window.google_prev_ad_formats_by_region,c=window.google_ad_section,d=window.google_ad_format,e=window.google_ad_slot;if(b[c])if(!N(d)){a.prev_fmts=b[c];if(window.google_num_slots_by_client.length>1)a.slot=window.google_num_slots_by_client[Q]}var f=window.google_prev_ad_slotnames_by_region;if(f[c])a.prev_slotnames=f[c].toLowerCase();if(d){if(!N(d))if(b[c])b[c]+=","+d;else b[c]=d}else if(e)if(f[c])f[c]+=","+e;else f[c]=e;a.correlator=window.google_correlator;if(window.google_new_domain_checked==
1&&window.google_new_domain_enabled==0)a.dblk=1;if(window.google_ad_channel){b=window.google_num_slots_by_channel;c="";d=window.google_ad_channel.split(Xc);for(e=0;e<d.length;e++){f=d[e];if(b[f])c+=f+"+";else b[f]=1}a.pv_ch=c}if(window.google_ad_host_channel){b=Yc(window.google_ad_host_channel,window.google_viewed_host_channels);a.pv_h_ch=b}if(Kb)a.jscb=1;if(Lb)a.jscd=1;a.frm=window.google_iframing;b=Zb();a.ga_vid=b.vid;a.ga_sid=b.sid;a.ga_hid=b.hid;a.ga_fc=b.from_cookie;a.ga_wpids=window.google_analytics_uacct},
$c=function(a,b,c){var d;if(b)if(typeof a.getBoundingClientRect=="function"){a=a.getBoundingClientRect();d={x:a.left,y:a.top}}else{d={};d.x="-252738";d.y="-252738"}else try{d=tc(a)}catch(e){d={};d.x="-252738";d.y="-252738"}if(d){c.adx=d.x;c.ady=d.y}},ad=function(a){var b=sb(document);if(b){a.biw=b.width;a.bih=b.height}};
function Yc(a,b){var c=a.split("|");a=-1;for(var d=[],e=0;e<c.length;e++){var f=c[e].split(Xc);b[e]||(b[e]={});for(var i="",m=0;m<f.length;m++){var t=f[m];if(t!="")if(b[e][t])i+="+"+t;else b[e][t]=1}i=i.slice(1);d[e]=i;if(i!="")a=e}b="";if(a>-1){for(e=0;e<a;e++)b+=d[e]+"|";b+=d[a]}return b}
function bd(){var a=Oc();Y(a,0);var b=j,c=j,d=j;switch(a){case "68120031":d=g;case "68120021":c=g;case "68120041":b=g;break;case "36812002":if(!window.google_atf_included){window.google_atf_included=g;mb("http://"+K+"/pagead/atf.js")}break}var e;if(b){var f="google_temp_span";e=window.google_container_id&&Ta(window.google_container_id)||Ta(f);if(!e&&!window.google_container_id){document.write("<span id="+f+"></span>");e=Ta(f)}}a=j;a=c?Tc(e,d):Tc();e&&e.id==f&&bb(e);if(a){c=Qc(window,document,window.google_ad_url);
hc().c(window.google_ad_output,c,j);Sc(window)}}var cd=function(a){var b;for(b in ub)a[ub[b]]=window[b];for(b in tb)a[tb[b]]=window[b];for(b in vb)a[vb[b]]=window[b]},dd=function(a){Dc(window.google_eids,1);a.eid=Cc()};function ed(a,b,c,d){a=Pb(a,b,c,d);Ec(window,document);return a}function fd(){Ub()}
function gd(a){var b={};a=a.split("?");a=a[a.length-1].split("&");for(var c=0;c<a.length;c++){var d=a[c].split("=");if(d[0])try{b[d[0].toLowerCase()]=d.length>1?window.decodeURIComponent?decodeURIComponent(d[1].replace(/\+/g," ")):unescape(d[1]):""}catch(e){}}return b}function hd(){var a=window,b=gd(document.URL);if(b.google_ad_override){a.google_ad_override=b.google_ad_override;a.google_adtest="on"}}
function Rc(a,b,c){if(a)if((a=b.getElementById(a))&&c&&c.length!=""){a.style.visibility="visible";a.innerHTML=c}}
var Wc=function(a,b){return a=N(a)?"/pagead/sdo?":b?"/pagead/render_iframe_ads.html#":"/pagead/ads?"},id=function(a,b){b.dff=vc(a);b.dfs=Ac(a)},Uc=function(){var a=window.google_prev_ad_formats_by_region,b=window.google_prev_ad_slotnames_by_region,c=window.google_ad_section;if(N(window.google_ad_format)){window.google_num_sdo_slots+=1;if(window.google_num_sdo_slots>4)return j}else if(M(window)){window.google_num_0ad_slots+=1;if(window.google_num_0ad_slots>3)return j}else{window.google_num_ad_slots+=
1;if(window.google_num_slots_to_rotate){Rb(1);a[c]=h;b[c]=h;if(window.google_num_slot_to_show==h)window.google_num_slot_to_show=(new Date).getTime()%window.google_num_slots_to_rotate+1;if(window.google_num_slot_to_show!=window.google_num_ad_slots)return j}else if(window.google_num_ad_slots>6&&c=="")return j}a=window.google_num_slots_by_client;if(a[Q])a[Q]+=1;else{a[Q]=1;a.length+=1}return g},Vc=function(a,b){var c={};cd(c);Zc(c);rb(c);if(a){id(a,c);$c(a,!!b,c)}ad(c);dd(c);c.fu=Qb;return c},Xc=/[+, ]/;
window.google_render_ad=bd;var jd=["30143070","30143071","30143075"],kd=typeof window.postMessage=="function"||typeof window.postMessage=="object"||typeof document.postMessage=="function",md=function(a,b){typeof A1_googleCreateSlot=="function"?A1_googleCreateSlot(a.google_ad_client):ld(a,b)},od=function(){if(!nd())return j;return g},pd=function(a){if(N(a.google_ad_format))return j;var b=a.google_ad_output;if(b&&b!="html")return j;a=a.google_ad_client;if(typeof a!="string"||a.substring(0,4)!="pub-"&&a.substring(0,7)!="ca-pub-")return j;
return g},rd=function(a){if(typeof a.google_a1_eid=="string")return a.google_a1_eid;var b=H("0.001",0);a.google_a1_eid=qd(jd,b);return a.google_a1_eid},ld=function(a,b){if(!a.google_included_a1_script){var c="script",d="/pagead/show_ads_sra3.js?v\x3d1";b.write("<"+c+' src="http://'+K+d+'"></'+c+">");a.google_included_a1_script=g}},nd=function(){if(z&&D("8"))return j;return kd||!C},qd=function(a,b){var c=Math.random();if(c<b){b=Math.floor(c/b*a.length);return a[b]}return""};function sd(){hd();Nb("show_ads.google_init_globals",ed,fd);Ec(window,document)}function td(){if(!od(window)||!pd(window))sd();else if(Mb&&window.google_use_a1===g)md(window,document);else{var a=rd(window);if(a){Y(a,0);window.google_allow_expandable_ads=j}!a||a=="30143070"?sd():md(window,document)}}Nb("show_ads.main",Pb,td);})()
