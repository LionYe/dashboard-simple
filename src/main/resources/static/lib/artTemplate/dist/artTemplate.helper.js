
template.helper('formatPrice', function(price) {
    if(price){
    	var val = parseFloat(price);
    	return val.toFixed(2);
    }
});

template.helper('dateFormat', function (date, format) {
	date = date.replace(/\/Date(−?\d+)\//, '$1'); //标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式  
	dt = new Date();  
    dt.setTime(date);
	//date = new Date(date);
    var map = {
        "M": dt.getMonth() + 1, //月份 
        "d": dt.getDate(), //日 
        "h": dt.getHours(), //小时 
        "m": dt.getMinutes(), //分 
        "s": dt.getSeconds(), //秒 
        "q": Math.floor((dt.getMonth() + 3) / 3), //季度 
        "S": dt.getMilliseconds() //毫秒 
    };
    format = format.replace(/([yMdhmsqS])+/g, function(all, t){
        var v = map[t];
        if(v !== undefined){
            if(all.length > 1){
                v = '0' + v;
                v = v.substr(v.length-2);
            }
            return v;
        }
        else if(t === 'y'){
            return (dt.getFullYear() + '').substr(4 - all.length);
        }
        return all;
    });
    return format;
});