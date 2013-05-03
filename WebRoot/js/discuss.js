/*
 *display
 */
var prjname = {
    "1": "NET",
    "2": "PHP",
    "3": "Java",
    "4": "C/C++",
    "19": "Python",
    "20": "Ruby",
    "5": "JavaScript",
    "6": "Flash",
    "7": "Delphi",
    "8": "前端开发",
    "9": "项目经理",
    "10": "主管",
    "11": "架构",
    "12": "总监",
    "13": "测试",
    "14": "系统",
    "15": "数据库",
    "16": "售前",
    "17": "手机应用开发",
    "18": "其他"
};
var type={
		"1":"技术问答",
		"2":"技术分享",
		"3":"IT大杂烩",
		"4":"职业生涯",
		"5":"站务/建议",
};
function disprjname(a){
    document.write(prjname[a]);
}

function distype(a){
    document.write(type[a]);
}
/*
 * end dis
 */