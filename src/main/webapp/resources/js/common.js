

//分页查询
function page(n,s){
	$("#pageNum").val(n);
	$("#pageSize").val(s);
	$("#search_form").submit();
	return false;
}

//新建web客户端聊天窗口
function newClient(url,name,iWidth,iHeight){
	var url;                                					//转向网页的地址;
	var name;                           						//网页名称，可为空;
	var iWidth;                         						//弹出窗口的宽度;
	var iHeight;                        						//弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
	window.open(url,name,'height='+iHeight+',width='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no,resizeable=no,location=no,status=no');
}