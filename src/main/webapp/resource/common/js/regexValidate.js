/**
 * 正则验证
 */


///数字范围验证
function IsLicitNumeric(en, min, max) {
    var value = en.value;
    if (value == "")
        return true;

    if (value <= max & value >= min)
    { return true; }
    else {
        alert('请输入在范围' + min + '到' + max + '之间的数字！');
        en.value = "";
        return false;
    }
}
//双精度型，保留小数点后面2位验证
function IsNumeric(en) {
    //  var re = /^([0-9]{1,3}$)/; //
    var re = /^([0-9]{1,3}$)|^(([0-9]{1,3}).([0-9]{1,2}))$/;

    var value = en.value;
    if (value == "")
        return true;

    var str = value.split('.');
    if (str[0].length > 3 || (str.length == 2 && str[1].length > 2)) {
        alert('数字范围在(0-999)之内，请重新输入！');
        en.value = "";
        en.focus();
        return false;
    }
    if (re.test(value)) {
        return true;
    }
    else {
        alert('数字范围在(0-999)之内，请重新输入！');
        en.value = "";
        en.focus();
        return false;
    }
}
//字母数字验证
function IsLicitCharacter(en) {
    var re = /^[A-Za-z0-9]+$/;
    var value = en.value;
    if (value == "")
        return true;

    if (re.test(value))
        return true;
    else {
        alert('请输入由数字和26个英文字母组成的字符串！');
        en.value = "";
        return false;
    }
}
//数字验证
function IsLicit(en) {
    var re = /^[0-9]{0,}$/;
    var value = en.value;
    if (value == "")
        return true;

    if (re.test(value))
        return true;
    else {
        alert('请输入数字！');
        en.value = "";
        return false;
    }
}
function Trim(val) {
    return val.replace(/(^\s*)|(\s*$)/g, "");
}


function IsLicitLength(en, minL, maxL, desc) {
    var value = Trim(en.value);
    if (typeof (desc) == "undefined")
        desc = "";
    if (minL != null) {
        if (value.length < minL) {
            alert(desc + "长度不得小于" + minL);
            return false;
        }
    }
    if (maxL != null) {
        if (value.length > maxL) {
            alert(desc + '长度不得大于' + maxL);
            return false;
        }
    }
    return true;
}
///汉字验证
function IsLicitCNCharacter(en, minL, maxL) {
    var re = /^[\u4e00-\u9fa5]{0,}$/;
    var value = en.value;
    if (value == "")
        return true;

    if (re.test(value)) {

        if (minL != null) {
            if (value.length < minL) {
            }
        }
        if (maxL != null) {
            if (value.length > maxL) {
                alert('长度不得大于' + maxL);
                en.value = value.substr(0, maxL);
                en.focus();
                return false;
            }
        }
    }
    else {
        alert('请输入汉字！');
        en.value = "";
        return false;
    }
}

///姓名验证
function IsLicitPersonName(en, desc) {
    var re = /^[A-Za-z\u4e00-\u9fa5]{2,10}$/; //|^[A-Za-z]{2,10}$
    var value = en.value;
    if (typeof (desc) == "undefined")
        desc = "";
    if (value == "") {

        alert(desc + '请输入2-10个汉字和英文字母！');
        return false;
    }
    if (re.test(value))
        return true;
    else {
        alert(desc + '请输入2-10个汉字和英文字母！');
        //        en.value = "";
        en.focus();
        return false;
    }
}
///汉字 字母验证
function IsLicitCnEnCharacter(en) {
    var re = /[A-Za-z0-9\u4e00-\u9fa5]$/;
    var value = en.value;
    if (value == "")
        return true;

    if (re.test(value))
        return true;
    else {
        alert('请输入由汉字、字母、数字组成的字符串！');
        en.value = "";
        en.focus();
        return false;
    }
}

///联系电话验证
function IsLicitPhone(en, desc) {
    var re = /(^\d{11}$|^((\d{7,8})|(\d{3,4})(-)?(\d{7,8})|(\d{3,4})(-)?(\d{7,8})(-)?(\d{1,4})|(\d{7,8})(-)?(\d{1,4}))$|^((\+86)|(86))?(1)\d{10})$|^(\d{3})-(\d{3})-(\d{4})$|^(\+)?((\d{2,4})-(\d{3,4})-(\d{7,8})$|^(\d{2,4})-(\d{3,4})-(\d{7,8})-(\d{1,4}))$|^((\d{3,4})-(\d{10}))$/;
    //支持的格式:11位数字|1234567[8]|3-4位数字-7-8位数字   |3-4位数字-7-8位数字-1-4位分机号                  |7-8位数字-1-4位分机号                |+86或86-1开头+10位数字   |3位数字-3位数字-4位数字
    var value = en.value;
    if (typeof (desc) == "undefined")
        desc = "";
    if (value == "") {

        alert(desc + '不能为空！');
        return false;
    }
    if (re.test(value))
        return true;
    else {
        alert(desc + '请输入合法的电话号码！');
        en.value = "";
        en.focus();
        return false;
    }
}
function IsLicitPhoneEx(en) {
    //var re = /((^\d{11}$)|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})))/;
    var re = /(^(1)\d{10}$)/;
    var value = en.value;
    if (value == "")
        return true;

    if (re.test(value))
        return true;
    else {
        alert("电话号码输入不正确");
        en.value = "";
        en.focus();
        return false;
    }
}
///身份证号码验证
function IsLicitCID(en) {
    if (en.value.length == 18) {
        return check18Identify(en);
    }
    else {
        var re = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; //|^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$
        var value = en.value;
        if (value == "")
            return true;

        if (re.test(value))
            return true;
        else {
            alert('请输入合法的身份证号码！');
            en.value = "";
            en.focus();
            return false;
        }
    }
}
function check18Identify(en) {
    var strId = en.value.toString();
    /*if(strId.length==15){
    strId=strId.substring(0,6)+"19"+strId.substring(6)
    }*/
    if (strId.length != 18)
        return;
    //∑(ai×Wi)(mod 11)
    //Wi=2^(n-1)(mod 11)
    //18 17 16 15 14 13 12 11 10 9 8 7 6  5 4 3 2 x [n]
    //3  7  0  7  8  4  1  9  8  5 1 2 0  3 7 0 1 4 [ai]
    //7  9  10 5  8  4  2  1  6  3 7 9 10 5 8 4 2 1 [Wi]

    //0 1 2 3 4 5 6 7 8 9 10 
    //1 0 X 9 8 7 6 5 4 3 2
    strCheck = strId.substring(17);
    strId = strId.substring(0, 17);
    var wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];
    var check = "10X98765432";
    var sum = 0;
    for (var i = 0; i <= 16; i++) {
        sum += parseInt(strId.substring(i, i + 1)) * wi[i];
    }
    //alert(sum % 11);//340524198001010011
    var mod = sum % 11;
    if (check.substring(mod, mod + 1) == strCheck) {
        return true;
    }
    else {
        alert('请输入合法的身份证号码！');
        en.value = "";
        en.focus();
        return false;
    }
}
/*

新加方法(只有返回值，无弹出框)

*/
//非空验证
function IsEmpty(en) {
    var value = en.value;
    if (value.length < 1) {
        en.focus();
        return true;
    }
    else {
        return false;
    }
}
//长度验证
function IsLengthValidate(en, minL, maxL) {
    var value = Trim(en.value);
    if (minL != null) {
        if (value.length < minL) {
            return false;
        }
    }
    if (maxL != null) {
        if (value.length > maxL) {
            return false;
        }
    }
    return true;
}
function IsIDValidate(en) {
    if (en.value.length == 18) {
        return Is18Identify(en);
    }
    else {
        var re = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; //|^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$
        var value = en.value;
        if (value == "")
            return true;

        if (re.test(value))
            return true;
        else {
            //alert('请输入合法的身份证号码！');
            en.value = "";
            en.focus();
            return false;
        }
    }
}
function Is18Identify(en) {
    var strId = en.value.toString();
    /*if(strId.length==15){
    strId=strId.substring(0,6)+"19"+strId.substring(6)
    }*/
    if (strId.length != 18)
        return;
    //∑(ai×Wi)(mod 11)
    //Wi=2^(n-1)(mod 11)
    //18 17 16 15 14 13 12 11 10 9 8 7 6  5 4 3 2 x [n]
    //3  7  0  7  8  4  1  9  8  5 1 2 0  3 7 0 1 4 [ai]
    //7  9  10 5  8  4  2  1  6  3 7 9 10 5 8 4 2 1 [Wi]

    //0 1 2 3 4 5 6 7 8 9 10 
    //1 0 X 9 8 7 6 5 4 3 2
    strCheck = strId.substring(17);
    strId = strId.substring(0, 17);
    var wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];
    var check = "10X98765432";
    var sum = 0;
    for (var i = 0; i <= 16; i++) {
        sum += parseInt(strId.substring(i, i + 1)) * wi[i];
    }
    //alert(sum % 11);//340524198001010011
    var mod = sum % 11;
    if (check.substring(mod, mod + 1) == strCheck) {
        return true;
    }
    else {
        //alert('请输入合法的身份证号码！');
        en.value = "";
        en.focus();
        return false;
    }
}
//电话号码验证
function IsPhoneValidate(en) {
    var re = /(^\d{11}$|^((\d{7,8})|(\d{3,4})(-)?(\d{7,8})|(\d{3,4})(-)?(\d{7,8})(-)?(\d{1,4})|(\d{7,8})(-)?(\d{1,4}))$|^((\+86)|(86))?(1)\d{10})$|^(\d{3})-(\d{3})-(\d{4})$|^(\+)?((\d{2,4})-(\d{3,4})-(\d{7,8})$|^(\d{2,4})-(\d{3,4})-(\d{7,8})-(\d{1,4}))$|^((\d{3,4})-(\d{10}))$/;
    //支持的格式:11位数字|1234567[8]|3-4位数字-7-8位数字   |3-4位数字-7-8位数字-1-4位分机号                  |7-8位数字-1-4位分机号                |+86或86-1开头+10位数字   |3位数字-3位数字-4位数字
    var value = en.value;
    if (value == "")
        return true;
    if (re.test(value))
        return true;
    else {
        en.value = "";
        en.focus();
        return false;
    }
}
///姓名验证
function IsNameValidate(en) {
    var re = /^[A-Za-z\u4e00-\u9fa5]{2,10}$/; //|^[A-Za-z]{2,10}$
    var value = en.value;
    if (value == "")
        return true;
    if (re.test(value))
        return true;
    else {
        //en.value = "";
        //en.focus();
        return false;
    }
}
function IsAroundValidate(en, min, max) {
    var value = en.value;
    if (value == "")
        return true;

    if (value <= max & value >= min)
    { return true; }
    else {
        //alert('请输入在范围' + min + '到' + max + '之间的数字！');
        en.value = "";
        return false;
    }
}
//验证手机号
function Ismobile(mobile) {  
    if( mobile == "") {  
     return false;  
    } else {  
      if( ! /^0{0,1}(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$/.test(mobile) ) {  
       return false;  
     }  
     return true;  
   }  
}  