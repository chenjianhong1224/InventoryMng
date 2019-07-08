Date.prototype.format = function(fmt) { // author: meizz  
	var o = {
		"M+" : this.getMonth() + 1, // 月份  
		"d+" : this.getDate(), // 日  
		"h+" : this.getHours(), // 小时  
		"m+" : this.getMinutes(), // 分  
		"s+" : this.getSeconds(), // 秒   
		"q+" : Math.floor((this.getMonth() + 3) / 3), // q是季度
		"S" : this.getMilliseconds()
	// 毫秒  
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function lessThanCurrOrderDate(strDate) {
	return strDate < getCurrOrderDate();
}

function getCurrOrderDate() {
	var nowDate = new Date();
	var orderDate = nowDate.format("yyyy-MM-dd");
	if (nowDate.getHours() >= 15) {
		orderDate = new Date(Date.parse(nowDate) + (3600000 * 24))
				.format("yyyy-MM-dd");
	}
	return orderDate;
}

function getBillBeginDate() {
	var nowDate = new Date();
	if (nowDate.getDate() >= "11" && nowDate.getDate() <= "20") {
		return new Date(Date.parse(nowDate.getFullYear() + "/"
				+ (nowDate.getMonth() + 1) + "/01"));
	} else if (nowDate.getDate() <= "10") {
		return new Date(Date.parse(nowDate.getFullYear() + "/"
				+ (nowDate.getMonth() ) + "/21"));
	} else {
		return new Date(Date.parse(nowDate.getFullYear() + "/"
				+ (nowDate.getMonth() + 1) + "/11"));
	}
}

function getBillEndDate() {
	var nowDate = new Date();
	if (nowDate.getDate() >= "11" && nowDate.getDate() <= "20") {
		return new Date(Date.parse(nowDate.getFullYear() + "/"
				+ (nowDate.getMonth() + 1) + "/10"));
	} else if (nowDate.getDate() <= "10") {
		return new Date(nowDate.getFullYear(), (nowDate.getMonth()), 0);
	} else {
		return new Date(Date.parse(nowDate.getFullYear() + "/"
				+ (nowDate.getMonth() + 1) + "/20"));
	}
}