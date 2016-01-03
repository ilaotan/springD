/*
	时间条插件，显示时间刻度
	author:chenz
	date:2015/01/07
*/

(function($){
	$.fn.timeLine = function(){
		//
		var tWidth = $(this).width();
		var halfs = tWidth/48;
		var hours = tWidth/24;

		for(var i=1;i<=48;i++){
			if (i%6 == 0) {
				$(this).append("<i style='left:"+ halfs*i +"px'>"+ i/2 +"</i>");	
			}else if(i%2 == 0){
				$(this).append("<i class='high' style='left:"+ halfs*i +"px'></i>");;
			}else{
				$(this).append("<i style='left:"+ halfs*i +"px'></i>");
			}
		}
	}	
})(jQuery);
