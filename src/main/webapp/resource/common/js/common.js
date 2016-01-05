/**
 * 公用js
 */
$(function(){
	// Tooltip
	jQuery('.tooltips').tooltip({ container: 'body'});

   // Popover
	jQuery('.popovers').popover();
	
	jQuery('.loading').click(function(){
		$(this).button('loading')
	});
	
	//$('.parent').click(function(){
		//$(this).toggleClass('parent-focus');
		//$(this).find('ul').toggle('fast');
	//});
	//左侧边栏显隐
	jQuery('.leftpanel .nav .parent > a').click(function() {

      var coll = jQuery(this).parents('.collapsed').length;

      if (!coll) {
         jQuery('.leftpanel .nav .parent-focus').each(function() {
            jQuery(this).find('.children').slideUp('fast');
            jQuery(this).removeClass('parent-focus');
         });

         var child = jQuery(this).parent().find('.children');
         if(!child.is(':visible')) {
            child.slideDown('fast');
            if(!child.parent().hasClass('active'))
               child.parent().addClass('parent-focus');
         } else {
            child.slideUp('fast');
            child.parent().removeClass('parent-focus');
         }
      }
      return false;
   });
   
   //子菜单点击后
   $('.children li').click(function(){
	   $('.children li.active').removeClass('active');
	   $(this).addClass('active');
	   $('#menu > li.active').removeClass('active');
	   $(this).parent().parent().addClass('active');
   });
   
   $('#menu > li').not('.parent').click(function(){
		 $(this).siblings('.active').removeClass('active');
		 $(this).addClass('active');
	});
   
   //操作提示
   if(flashMsg != "" && flashMsg != null){
	   artPopMsg(flashMsg);
   }
   
});

//背景高亮后减淡
function fadeColor(id){
	$('#'+id).css('backgroundColor','#FFFF96').animate({backgroundColor:'#fff'},3000);
}
//artdialog 弹窗
function dialog(url,title,width,height,lock){
	if(width == ''||width == null) width = 700;
	if(height == ''||height == null) height = 350;
	if(lock == ''||lock == null) {
		lock = true;
	}else{
		lock = false;
	}
	art.dialog.open(url,{title: title, width: width, height: height,lock:lock});
}

function closeDialog(){
	art.dialog.close();
}

//翻页
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#fenyeForm input,select").each(function(index, domEle){
		if (domEle.placeholder == domEle.value) {
			domEle.disabled = true;
		}
	});
	$("#fenyeForm").submit();
	return false;
 }

artDialog.popMsg = function (content,type) {
    return art.dialog.through({
        id: 'popMsg',
        icon: 'warning',
        fixed: true,
        top: '40%',
        lock: false,
        content: content,
        init: function () {
        	var that = this, i = 2;
            var fn = function () {
                that.title(i + '秒后关闭');
                !i && that.close();
                i --;
            };
            timer = setInterval(fn, 1000);
            fn();
        },
        close: function () {
        	clearInterval(timer);
        },
        ok: true,
        okVal: '关闭',
    });
};

/**
 * 确认
 * @param	{String}	消息内容
 * @param	{Function}	确定按钮回调函数
 * @param	{Function}	取消按钮回调函数
 */
artDialog.confirm = function (content, yes, no) {
	return art.dialog.through({
		id: 'Confirm',
		icon: 'question',
		fixed: true,
		lock: true,
		content: content,
		ok: function (here) {
			return yes.call(this, here);
		},
		cancel: function (here) {
			return no && no.call(this, here);
		}
	});
};


var artPopMsg = art.dialog.popMsg;
//artdialog confirm 效果
var artConfirm = art.dialog.confirm;
//artdialog Alert 效果
var artAlert = art.dialog.alert;

function autoMsg(){
	
}
//防止IE低版本不支持console
var alertFallback = false;//是否开启调试
if (typeof console === "undefined" || typeof console.log === "undefined") {
  console = {};
  if (alertFallback) {
      console.log = function(msg) {
           alert(msg);
      };
  } else {
      console.log = function() {};
  }
}

//无输入，下划线都为灰色
function Primary() {
	$('#pwdLevel_1').attr('class', 'ywz_zhuce_huixian');
	$('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
	$('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}
// 强度弱
function Weak() {
	$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
	$('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
	$('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}
// 强度中
function Medium() {
	$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
	$('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
	$('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}
// 强度强
function Tough() {
	$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
	$('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
	$('#pwdLevel_3').attr('class', 'ywz_zhuce_hongxian3');
}
// 检查输入密码
function checkPassword(pwdinput) {
	var maths, smalls, bigs, corps, cat, num;
	var str = $(pwdinput).val()
	var len = str.length;
	// 正则 匹配二十次输入
	var cat = /.{20}/g
	if (len == 0) return 1;
	if (len > 20) { $(pwdinput).val(str.match(cat)[0]); }
	// 正则 UNICODE CJK 统一表意符号，主要匹配中日韩文字
	cat = /.*[\u4e00-\u9fa5]+.*$/
	if (cat.test(str)) {
		return -1;
	}
	// 正则 数字
	cat = /\d/;
	var maths = cat.test(str);
	// 正则 小写
	cat = /[a-z]/;
	var smalls = cat.test(str);
	// 正则 大写
	cat = /[A-Z]/;
	var bigs = cat.test(str);
	// 其他符号
	var corps = corpses(pwdinput);
	// 数字、大小写字母、符号匹配的种类，返回1到4
	var num = maths + smalls + bigs + corps;
	if (len < 6) { return 1; }
	if (len >= 6 && len <= 8) {
		if (num == 1) return 1;
		if (num == 2 || num == 3) return 2;
		if (num == 4) return 3;
	}
	if (len > 8 && len <= 11) {
		if (num == 1) return 2;
		if (num == 2) return 3;
		if (num == 3) return 4;
		if (num == 4) return 5;
	}
	if (len > 11) {
		if (num == 1) return 3;
		if (num == 2) return 4;
		if (num > 2) return 5;
	}
}
// 匹配CJK编码、数字，大小写字母之外的输入
function corpses(pwdinput) {
	var cat = /./g
	var str = $(pwdinput).val();
	var sz = str.match(cat)
	for (var i = 0; i < sz.length; i++) {
		cat = /\d/;
		maths_01 = cat.test(sz[i]);
		cat = /[a-z]/;
		smalls_01 = cat.test(sz[i]);
		cat = /[A-Z]/;
		bigs_01 = cat.test(sz[i]);
		if (!maths_01 && !smalls_01 && !bigs_01) { return true; }
	}
	return false;
}

function backToList (url, pn, message) {
	
	window.location.href = url + "?pageNo=" + pn;
	
}


//计算年龄
function ages(str){   
      var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
      if(r==null) return 0;
      var d= new Date(r[1],r[3]-1,r[4]);
      var age = 0;
      if(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]){   
            var Y = new Date().getFullYear();   
//            return("年龄   =   "+   (Y-r[1])   +"   周岁");
            age = Y-r[1];
            if (age == 0) {
          	  age = 1;
            }
            return age;
      }
      return 0;   
}