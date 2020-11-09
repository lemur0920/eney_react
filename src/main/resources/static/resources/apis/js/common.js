MADEWEB.UI = {
	domtree : function(){
		var node = this.dom = {};
		node.tabmenu = $("[data-ui-tab]");
		node.rolling_type1 = $("[data-ui-type='img_rotation_translate'], [data-ui-type='img_rotation_static']");

		for(var i in node){
			if(node[i].size() > 0){
				this["func_" + i]();
			}else{
				continue;
			}
		}
	},
	selectbox : function(){
		var that = this
		, obj = $("[data-ui-type=selectbox]")
		, defaultText = $("p", obj)
		, option = $("li", obj)
		, mywrap = null
		, before = null;

		defaultText.click(function(event){
			reset();
			mywrap = $(this).parents(obj);
			mywrap.toggleClass("on");
			return false;
		});

		function reset(){
			$(document.body).bind("click",function(e){
				obj.removeClass("on");
			});
		}
	},
	arcodian : function(){
		var obj = $("[data-ui-type=arcodian]")
		, link = $(" > ul > li > dl > dt > a", obj)
		, before = null;
		link.click(function(){
			var dd = $(this).parents("dl").find(" > dd");
			var wrap = $(this).parents("dl").find(" > dd .wrap");

			if(before == null){
				slide(dd,wrap[0].offsetHeight);
				$(this).parents("li").addClass("on");
			}else{
				slide(before,0);
				before.parents("li").removeClass("on");

				if(dd.height() == 0){
					slide(dd,wrap[0].offsetHeight);
					$(this).parents("li").addClass("on");
				}else{
					slide(dd,0);
					$(this).parents("li").removeClass("on");
				}
			}
			before = dd;
			return false;
		});

		function slide(dd,h){
			dd.stop().animate({
				"height" : h
			},300);
		}
	},
	func_rolling_type1 : function(){
		var i = 0, element;
		element = $("[data-ui-type='img_rotation_translate'], [data-ui-type='img_rotation_static']");
		for(; i < element.size(); i++){
			new translate(element.eq(i));
		}
		function translate(a){
			var obj = $(a)
				, list = $("ul:eq(0) li", obj)
				, ctrl = $(".ctrl", obj)
				, prev = $(".prev", ctrl)
				, stop = $(".stop", ctrl)
				, play = $(".play", ctrl)
				, next = $(".next", ctrl)
				, bullet = $(".nav button", obj)
				, state = true
				, before = list.eq(0)
				, cnt = 0
				, locked = "on"
				, power = "on"
				, delay = obj.attr("data-cycle") !== undefined ? obj.attr("data-cycle") * 1000 : 3000;
			function barrier(){if(power == "off") return false;if(locked == "off") return false;}

			ctrl.mouseenter(function(){locked = "off";});
			ctrl.mouseleave(function(){locked = "on";});
			bullet.bind("focusin mouseenter",function(event){
				barrier();
				locked = "off";
				list.removeClass("on");
				bullet.removeClass("on");
				cnt = $(this).index();
				list.eq(cnt).addClass("on");
				bullet.eq(cnt).addClass("on");
			});
			bullet.bind("focusout mouseleave",function(){
				locked = "on";
				func();
			});
			stop.click(function(){
				if(power == "off"){
					power = "on";
					$(this).removeClass("play");
				}else{
					power = "off";
					$(this).addClass("play");
				}
				return false;
			});

			prev.click(function(){
				if(state == false) return false;
				state = false;
				barrier();
				if(cnt > 0){
					cnt--;
				}else{
					cnt = list.size() - 1;
				}
				func();
				return false;
			});

			next.click(function(){
				if(state == false) return false;
				state = false;
				barrier();
				if(cnt < list.size() - 1){
					cnt++;
				}else{
					cnt = 0;
				}
				func();
				return false;
			});

			var auto = setInterval(function(){
				if(power == "off") return false;
				if(locked == "off") return false;
				next.click();
			},delay);

			function func(){

				bullet.removeClass("on");
				before.removeClass("on");
				if(obj.attr("data-ui-type") == "img_rotation_translate"){//translate
					list.eq(cnt).addClass("on").css({
						"opacity" : 0,
						"z-index" : list.size()
					}).stop().animate({
						"opacity" : 1
					},{
						duration : 500,
						complete : function(){
							before = list.eq(cnt);
							state = true;
						}
					});
				}else if(obj.attr("data-ui-type") == "img_rotation_static"){//static
					bullet.eq(cnt).addClass("on");
					list.eq(cnt).addClass("on").css({
						"opacity" : 1
					});
					before = list.eq(cnt);
					state = true;
				}
			}
		}
	},
	func_tabmenu : function(){
		var that = this
		, dom = that.dom;
		//탭메뉴 프로토타입 클래스
		function Tabmenu(dom){//호출시 dom요소를 넘겨서 정보를 저장
			this.prototype = {};
			this.name = dom;
		}
		Tabmenu.prototype.classify = function(){
			var This = this, state = true;
			this.parent = this.name.children[0];//ul 또는 ol
			this.list = $(" > li", this.parent);//li만 저장
			this.before = $(" > li.on", this.parent);
			this.before_cont = $("#" + getTarget(this.before[0]));

			//탭이 하나만 있을때... 0821추가
			if(this.list.size() == 1){
				$(this.list.eq(0).find("a").attr("href")).css("height","auto");
			}

			this.list.click(function(event){
				var e = event || window.event;
				if(e.stopPropagation) event.stopPropagation();
				else e.cancelBubble = true;//버블링취소

				if(This.name.getAttribute("data-ui-tab") == "fade"){
					if(state == false) return false;
					state = false;
					if(This.before[0] === this){
						state = true;
					}
					var nowCont = $("#" + getTarget(this)), h;
					if(getTarget(this) == This.before_cont.attr("id")) return false;
					This.before.removeClass("on");
					This.before_cont.css({
						"height" : 0,
						"display" : "none"
					});
					$(this).addClass("on");
					nowCont.css({
						"height" : "auto",
						"display" : "block"
					});
					h = nowCont.height();

					nowCont.css("height",0).animate({
						"height" : h + "px"
					},{
						duration : 500,
						complete : function(){
							nowCont.css("height","auto");
							state = true;
						}
					});
					This.before_cont = nowCont;
					This.before = $(this);

					var wrap = $("#product_detailview3 .vod_summary .vod")
					, str = function(url){
						var arr = ['<object>'
						, '<param name="movie" value="' + url + '"></param>'
						, '<param name="allowFullScreen" value="true"></param>'
						, '<param name="allowscriptaccess" value="always"></param>'
						, '<param name="wmode" value="transparent"></param>'
						, '<embed src="' + url + '" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" wmode="transparent"></embed>'
						, '</object>'].join("");
						return arr;
					};

					if($(this).find("> a").attr("id") == "brand_vod_story"){
						wrap.html(str($(this).find("> a:eq(0)").attr("data-vod-url")));
					}else{
						wrap.html("");
					}

				}else if(This.name.getAttribute("data-ui-tab") == ""){
					This.before.removeClass("on");
					This.before_cont.css("display","none");
					$(this).addClass("on");
					$("#" + getTarget(this)).css("display","block");
					This.before_cont = $("#" + getTarget(this));
					This.before = $(this);
					if(this.querySelectorAll("[data-ui-tab]").length > 0) return false;

					switch(This.name.getAttribute("data-ui-tab")){
						case "page_hold" : return false;
						break;
						default : "";
					}
				}

			});
		}
		dom.tabmenu.each(function(){
			var instance = new Tabmenu(this);//this는 dom객체
			instance.classify();
			delete instance;
		});
	}
};