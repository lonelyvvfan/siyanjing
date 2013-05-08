/*
 *display
 */
var type = {
		"1":"NET",
		"2":"PHP",
		"3":"Java",
		"4":"C/C++",
		"5":"JavaScript",
		"6":"Flash", 
		"7":"Delphi ",
		"8":"前端开发", 
		"9":"项目", 
		"10":"主管", 
		"11":"架构",
		"12":"总监",
		"13":"测试",
		"14":"系统", 
		"15":"数据库",
		"16":"售前",
		"17":"手机应用开发",
		"18":"其他",
		"19":"Python",
		"20":"Ruby"
			
};

var prjname={
		"1":"技术问答",
		"2":"技术分享",
		"3":"IT大杂烩",
		"4":"职业生涯",
		"5":"站务/建议"
};
function distype(a){
    document.write(type[a]);
}

function disprjname(a){
    document.write(prjname[a]);
}


/*
 * end dis
 */


/*
 *post
*/


function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}


//end

//select selected option
function select(selectid,value){
	var opt=document.getElementById(selectid);
	var i;
	for(i=0;i<opt.options.length;i++){
		if(value==opt.options[i].value){
	        opt.options[i].selected = 'selected';
	   }
	}
	}
//end
