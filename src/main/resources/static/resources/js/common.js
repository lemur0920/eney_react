
function getLength(str)
{
  return(str.length+(escape(str)+"%u").match(/%u/g).length-1);
}

//usage : variable.trim
String.prototype.trim = function()
{
 return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.ltrim = function() {

  var i, j = 0;
  var objstr

  for (i = 0; i < this.length; i++) {

   if (this.charAt(i) == ' ') j = j + 1;
   else break;
  }

  return this.substr(j, this.length - j + 1);
}

String.prototype.rtrim = function() {

  var i, j = 0;

  for (i = this.length - 1; i >= 0; i--) {

   if (this.charAt(i) == ' ') j = j + 1;
   else break;
  }

  return this.substr(0, this.length - j);
 } 

//usage : variable.replaceAll
String.prototype.replaceAll = function(str1, str2)
{
 var temp_str = "";
 
 if (this.trim() != "" && str1 != str2)
 {
  temp_str = this.trim();
 
  while (temp_str.indexOf(str1) > -1)
  {
   temp_str = temp_str.replace(str1, str2);
  }
 }
 
 return temp_str;
}

function CheckValidBookID(val)
{
	for(i=0;i<val.length;i++) {
		ch = val.substring(i, i+1);
		if((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch == '_')) continue;
        else return(false);
	}
	return (true);
}

function GetStrByte(val)
{
    if(val < 1024) return val + " B";
    else if(val >= 1024 && val < 1024 * 1024) return parseInt(val/1024) + "." + parseInt((val%1024)/100) + " KB";
    else return parseInt(val/1024/1024) + "." + parseInt((val%1024)/100) + " MB";
}

function trim(s)
{
        var i, j;
        var ns;
	    var strlen = s.length;

        for (i = 0, ns = ''; i < strlen; i++) if (s.charAt(i) != ' ')  break;
        for (j = strlen - 1; j >= 0; j--) if (s.charAt(j) != ' ')  break;
	    if(i > j) return '';

        ns = s.substring(i, j+1);
        return ns;
}

function rtrim(s)
{
        var j;
        var ns;
        for (j = s.length - 1; j >= 0; j--) if (s.charAt(j) != ' ')  break;
        ns = s.substring(0, j + 1);
        return ns;
}


/* check mail format */
function CheckEmail(str)
{
	var filter=/^.+@.+\..{2,3}$/
	if (filter.test(str)) return true;
	else return false;
}

function CheckEmail3(value) 
{
	if(value == "") return false;
	var tmp_input = value;
	tmp_input = tmp_input.replace(/(,| |\t)+$/, "");
	tmp_input = tmp_input.replace(/\s+/g, "");
	var emailpattern	= /[-!#$%&'*+\/^_~{}|0-9a-zA-Z]+(\.[-!#$%&'*+\/^_~{}|0-9a-zA-Z]+)*@[-!#$%&'*+\/^_~{}|0-9a-zA-Z]+(\.[-!#$%&'*+\/^_~{}|0-9a-zA-Z]+)*/;	
	var email_array		= tmp_input.split(",");
	var email_count		= email_array.length;
	var t;
	var result;
	var b_rtn_value = true;
	for (var i = 0; i < email_count; i++)
	{
		t = str_extract_java(email_array[i], "<", ">");
		result = t.match(emailpattern);

    	if (result == null)
		b_rtn_value = false;
		else if (result[0] != t)				
		b_rtn_value = false;			

	}
    return b_rtn_value;		
}

/* 두개의 문자열 사이의 문장을 검출해 내는 함수 */
function str_extract_java(value, start, end)
{
    var tmp;
	var stStr = eval("/"+start+"/");
	var endStr = eval("/["+end+" ]+/g");

	if ((value.search(stStr)) == -1)
		return value;

	tmp = value.split(start);
	tmp = tmp[1];
	tmp = tmp.replace(endStr, "");

	return tmp;
}

/*

function CheckEmail(email)
{  
	var filter=/^.+@.+\..{3,4}$/;

	if(!filter.test(email)) return false;
	if(IsHangul(email) == true) return false;
	return true;

}
*/


function setCookie( name, value, expiredays )
{
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function getCookie( name )
{
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length )
	{
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
				endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;
		if ( x == 0 )
			break;
	}
	return "";
}





function GetCookie(sName)
{
  var aCookie = document.cookie.split(';');
  for (var i=0; i < aCookie.length; i++)
  {
	var aCrumb = aCookie[i].split('=');
	if (sName == trim(aCrumb[0])) {
	  return unescape(aCrumb[1]);
	}
  }

  return null;
}



function CheckNum()
{
	if(event.keyCode < 48 || event.keyCode > 57) event.keyCode=null;
}

function CheckSpecialChars()
{
        var ch = event.keyCode;

        if((ch >= 49 && ch <= 58) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) return;
        else event.keyCode = null;
}


function IsNumber(t) 
{
	var i,j;
	var Digit = '1234567890'
	astr = Digit;
    
    //0일때.. false가 리턴되서..
    if(t == "0") return(true);
    
	if(t.length == 0) return(false);
	if (astr.length > 1) {
		for(i=0;i<t.length;i++) {
			if(astr.indexOf(t.substring(i,i+1)) < 0)  {
				j=1;
				return(false);
			}
		}
	}
	return(t);
}


function IsContinueNo(t)
{
	var i;
	var cont_flag=false;
	var next='';

	for(i=0; i<t.length-1; i++) {
		next = parseInt(t.substring(i, i+1)) + 1;
		if(t.substring(i+1, i+2) == next) cont_flag = true;
		else return(false); 
	}
	return(cont_flag);
}

function WhiteChk(strValue) {
  var flag=true;
	if (strValue!="") {
		for (var i=0; i < strValue.length; i++) {
			if (strValue.charAt(i) == " ") {
			  flag=false;
			  break;
			}
		}
	}
	return flag;
}


function HaveSpecialChars(t)
{
        var i,j;

        for(i=0;i<t.length;i++) {
                ch = t.substring(i, i+1);
                if((ch >= ' ' && ch <= '/') || (ch >= ':' && ch <= '@') || (ch >= '[' && ch <= '`') || (ch >= '{' && ch <= '~')) return(true);
        }
        return(false);
}


function IsAlphaNumeric(t)
{
	var i,j;

	for(i=0;i<t.length;i++) {
		ch = t.substring(i, i+1);
		if((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) j=0;
                else return(false);
	}
	return(true);
}


function CheckEmail2(email)
{
        myreg = /(\w)@\w.\w.\w/;
	if(!myreg.test(email)) return false;

	if(IsHangul(email) == true) return(false); 

    	return true;
}



function IsHangul(str)
{
   
	for (i = 0; i < str.length; i++) {
		ch = str.substring(i, i+1);
		if (ch < ' ' || ch > '~') return(true);
	}
	return(false);
}



// 주민등록번호 체크 
function CheckJoomin(ssn)
{
    var key = "234567892345";
	var total = 0;
	for(var i=0; i<12; i++)
	{
		total = total + ssn.charAt(i)*key.charAt(i);
	}
	total = 11 - (total % 11);

	switch(total)
	{
		case 11:
			total=1;
			break;
		case 10:
			total=0;
			break;
		default:
			total=total;
			break;
	}

	if(ssn.charAt(12) != total)
	{
		return false;
	}
	return true;
}


// 사업자등록번호 체크 
function CheckBizNo(vencod) 
{ 
    if (vencode.length == 10)
    	return true;
    else
    	return false;
    
    if(vencod == "") return false;
    
    var sum = 0; 
    var getlist =new Array(10); 
    var chkvalue =new Array("1","3","7","1","3","7","1","3","5"); 
    
    for(var i=0; i<10; i++) { getlist[i] = vencod.substring(i, i+1); } 
    
    for(var i=0; i<9; i++) { sum += getlist[i]*chkvalue[i]; } 
    
    sum = sum + parseInt((getlist[8]*5)/10); 
    sidliy = sum % 10; 
    sidchk = 0; 
    
    if(sidliy != 0) { sidchk = 10 - sidliy; } 
    else { sidchk = 0; } 
    if(sidchk != getlist[9]) { return false; } 
    return true; 
} 


function hexa(numb){
    var k=0;
    var kk=0;
    var str = "";

    k = numb;
    while(1) {
        kk = k % 16;
        k = Math.floor(k/16);
        str = convert(kk) + str;
        if (k == 0) break;
    }

    //str = convert(kk) + str;
    return(str);
}

function convert(nr){
  var chr;
  if (nr==10){chr="A"}
  else if (nr==11){chr="B"}
  else if (nr==12){chr="C"}
  else if (nr==13){chr="D"}
  else if (nr==14){chr="E"}
  else if (nr==15){chr="F"}
  else {chr=nr;}
  return(chr);
}

/* 공통팝업 sm0321 */
var Popup=null;
function CommPopup(URL, Width, Height, ScrollBar, Resizable) {
	var iMyWidth;
	var iMyHeight;
	iMyWidth = (window.screen.width/2) - 450;
	iMyHeight = (window.screen.height/2) - 350;

	if(Popup != null && !Popup.closed) Popup.close();
	src=URL;
	if(!ScrollBar) ScrollBar = no;
	if(!Resizable) Resizable = no;
	etc = 'toolbar=no,location=no,status=no,scrollbars=' + ScrollBar + ',width=' + Width + ',height=' + Height + ',resizable=' + Resizable + ",left=" + iMyWidth + ",top=" + iMyHeight
	Popup = window.open(src,'', etc);
	Popup.window.focus();
}

function imgStrSize(val)
{
	// 첨부파일당 10M로 제한 

	
	//이미지 크기 계산
	if(val > 1024 && val < 1048576)
	{
		strFileSize = Math.round(eval(val)/1000)+"KB";
	}
	else if(val<1024)
	{
		strFileSize = eval(val)+"B";
	}
	else
	{
		intMB = (eval(val)/1000)/1000 ;
		intMB = Math.round(intMB*100)/100

		strFileSize = intMB+"MB";
	}
	
	return strFileSize;
}

function imgReSize(imgWidth, imgHeight, maxWidth, imgObj)
{
    if(imgWidth > maxWidth) {
  		heightValue = parseInt((maxWidth * imgHeight)/imgWidth);
  		widthValue = maxWidth;
    }
    else {
  		heightValue = imgHeight;
  		widthValue = imgWidth;
    }
	imgObj.style.width = widthValue;
	imgObj.style.height = heightValue;
}

function imgReSizeFixed(imgWidth,imgHeight,maxWidth,maxHeight, imgObj)
{
	if (imgWidth >  maxWidth)
	{
		widthValue = maxWidth;
	}
	else
	{
		widthValue = imgWidth;
	}
	
	WidthRate = (widthValue / imgWidth) ;

	if (imgHeight > maxHeight)
	{
		heightValue = maxHeight ;
	}
	else
	{
		heightValue = imgHeight ;
	}

	HeightRate = (heightValue / imgHeight) ;

	if (maxWidth == 0)
	{
		WidthRate = 100;
	}

	if (maxHeight == 0)
	{
		HeightRate = 100;
	}

	if (HeightRate < WidthRate)
	{
		widthValue =  imgWidth * HeightRate ;
		heightValue = imgHeight * HeightRate ;
	}
	else
	{
		widthValue  =  imgWidth * WidthRate;
		heightValue = imgHeight * WidthRate;
	}
	imgObj.style.width = widthValue;
	imgObj.style.height = heightValue;
}


function getmulticontent(source, maxlength, separate_str)
{
   
    var content_size = getLength(source);
    var multicontent = "";

    if(maxlength >= content_size) {
        return source;
    }

    
    var oldpos = 0;
    var j=0;
    var i=0;

    while(i < content_size) {
        if(source.charCodeAt(i) < 256) {
            j++;
        }
        else {
            j += 2;
        }
        i++;
        
        if(j >= maxlength) {

            if(j > maxlength) i--;
            scontent = source.substring(oldpos, i);
            if(scontent.length) multicontent += scontent + separate_str;
            oldpos = i;
            j=0;

        }
    }
   
    multicontent = multicontent.substring(0, multicontent.length - separate_str.length);
    return multicontent;
}


function emailCheck(ele)
{
         if(ele.value.length != 0)
         {
            var str = ele.value;  
            		 
  		    re=/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; 
    
            // 위의 조건을 만족하려면 최소 6자 이상이어야 함.
            if(ele.value.length <6 || !re.test(ele.value)) { 
               alert("메일형식이 맞지 않습니다. 다시 입력해주세요!!"); 
               ele.select(); 
               ele.focus(); 
               return false; 
            } else { 
               return true; 
            } 
		 }
} 


// 주민번호 체크
function prinumCheck(juminno) {
        if(juminno.length <=13) {
                alert("주민등록번호를 적어주세요.");
                return false;
        }

        //if(juminno=="" || juminno==null || juminno.length!=13) {
        //        alert("주민등록번호를 적어주세요.");
        //        return false;
        //} 

	    var jumin1 = juminno.value.substr(0,6);
        var jumin2 = juminno.value.substr(6,7);
	
		var yy     = jumin1.substr(0,2);        // 년도
        var mm = jumin1.substr(2,2);        // 월
        var dd = jumin1.substr(4,2);        // 일
        var genda = jumin2.substr(0,1);        // 성별
        var msg, ss, cc;
        // 숫자가 아닌 것을 입력한 경우
        //if (!isNumeric(jumin1)) {
        //        alert("주민등록번호 앞자리를 숫자로 입력하세요.");
        //        return false;
        //}
        // 길이가 6이 아닌 경우
        if (jumin1.length != 6) {
                alert("주민등록번호 앞자리를 다시 입력하세요.");
                return false;
        }
        // 첫번째 자료에서 연월일(YYMMDD) 형식 중 기본 구성 검사
        if (yy < "00" || yy > "99" ||
                mm < "01" || mm > "12" ||
                dd < "01" || dd > "31") {
                alert("주민등록번호 앞자리를 다시 입력하세요.");
                return false;
        }
        // 숫자가 아닌 것을 입력한 경우
        if (!isNumeric(jumin2)) {
                alert("주민등록번호 뒷자리를 숫자로 입력하세요.");
                return false;
        }
        // 길이가 7이 아닌 경우
        if (jumin2.length != 7) {
                alert("주민등록번호 뒷자리를 다시 입력하세요.");
                return false;
        }
        // 성별부분이 1 ~ 4 가 아닌 경우
        if (genda < "1" || genda > "4") {
                alert("주민등록번호 뒷자리를 다시 입력하세요.");
                return false;
        }
        // 연도 계산 - 1 또는 2: 1900년대, 3 또는 4: 2000년대
        cc = (genda == "1" || genda == "2") ? "19" : "20";
        // 첫번째 자료에서 연월일(YYMMDD) 형식 중 날짜 형식 검사
        if (isYYYYMMDD(parseInt(cc+yy), parseInt(mm), parseInt(dd)) == false) {
                alert("주민등록번호 앞자리를 다시 입력하세요.");
                return false;
        }
        // Check Digit 검사
        if (!isSSN(jumin1, jumin2)) {
                alert("입력한 주민등록번호를 검토한 후, 다시 입력하세요.");
                return false;
        }
        return true;
}

function isYYYYMMDD(y, m, d) {
        switch (m) {
        case 2:        // 2월의 경우
                if (d > 29) return false;
                if (d == 29) {
                        // 2월 29의 경우 당해가 윤년인지를 확인
                        if ((y % 4 != 0) || (y % 100 == 0) && (y % 400 != 0))
                                return false;
                }
                break;
        case 4:        // 작은 달의 경우
        case 6:
        case 9:
        case 11:
                if (d == 31) return false;
        }
        // 큰 달의 경우
        return true;
}

function isNumeric(s) {
        for (i=0; i<s.length; i++) {
                c = s.substr(i, 1);
                if (c < "0" || c > "9") return false;
        }
        return true;
}
function isLeapYear(y) {
        if (y < 100)
        y = y + 1900;
        if ( (y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0) ) {
                return true;
        } else {
                return false;
        }
}
function getNumberOfDate(yy, mm) {
        month = new Array(29,31,28,31,30,31,30,31,31,30,31,30,31);
        if (mm == 2 && isLeapYear(yy)) mm = 0;
        return month[mm];
}
function isSSN(s1, s2) {
        n = 2;
        sum = 0;
        for (i=0; i<s1.length; i++)
                sum += parseInt(s1.substr(i, 1)) * n++;
        for (i=0; i<s2.length-1; i++) {
                sum += parseInt(s2.substr(i, 1)) * n++;
                if (n == 10) n = 2;
        }
        c = 11 - sum % 11;
        if (c == 11) c = 1;
        if (c == 10) c = 0;
        if (c != parseInt(s2.substr(6, 1))) return false;
        else return true;
}






// 캐릭터 타입 검증 'H'-한글, 'E'-영문, 'N'-숫자, 'Z'-기타 
function getCharType(pValue){ 
 var bHan = false; 
 var bAlp = false; 
 var bNum = false; 
 var bEtc = false; 
  
 var retStr=""; 
  
 if(isEmpty(pValue)){ 
  return ""; 
 } 
  
 for(var idx=0; idx < pValue.length; idx++){ 
  if (isAlpha(pValue[idx])) { 
  bAlp = true; 
  } 
  else if (isNum(pValue[idx])) { 
  bNum = true; 
  } 
  else if (isHangul(pValue[idx])) { 
  bHan = true; 
  } 
  else { 
  bEtc = true; 
  } 
  
  if (bHan) retStr = retStr + "H"; 
  if (bAlp) retStr = retStr + "E"; 
  if (bNum) retStr = retStr + "N"; 
  if (bEtc) retStr = retStr + "Z"; 
 } 
  
 return retStr; 
} 
  
//새창 여는 함수 
function uf_newWin( url, winName, sizeW, sizeH) 
{ 
 var nLeft  = screen.width/2 - sizeW/2 ; 
 var nTop  = screen.height/2 - sizeH/2 ; 
  
 opt = ",toolbar=no,menubar=no,location=no,scrollbars=yes,status=no"; 
 window.open(url, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt ); 
  
} 
  

//새창 사이즈 정함 
function uf_reSize ( sizeW, sizeH) 
{ 
 window.resizeTo( sizeW, sizeH ); 
  
} 
  
//옵션이 있는경우 
  
function selDataChange(form) { 
  var DataIndex=form.url.selectedIndex; 
  if (form.url.options[DataIndex].value != null) { 
      location=form.url.options[DataIndex].value; 
  } 
} 
  
function selDataChange2(form) { 
  var DataIndex=form.url2.selectedIndex; 
  if (form.url2.options[DataIndex].value != null) { 
      location=form.url2.options[DataIndex].value; 
  } 
} 
  
/** 
 * 입력값이 NULL인지 체크 
 */ 
function isNull(input) { 
    if (input.value == null || input.value == "") { 
        return true; 
    } 
    return false; 
} 
  
/** 
 * 입력값에 스페이스 이외의 의미있는 값이 있는지 체크 
 * ex) if (isEmpty(form.keyword)) { 
 *        alert("검색조건을 입력하세요."); 
 *    } 
 */ 
function isEmpty(input) { 
    if (input.value == null || input.value.replace(/ /gi,"") == "") { 
        return true; 
    } 
    return false; 
} 
  
/** 
 * 입력값에 특정 문자(chars)가 있는지 체크 
 * 특정 문자를 허용하지 않으려 할 때 사용 
 * ex) if (containsChars(form.name,"!,*&^%$#@~;")) { 
 *        alert("이름 필드에는 특수 문자를 사용할 수 없습니다."); 
 *    } 
 */ 
function containsChars(input,chars) { 
    for (var inx = 0; inx < input.value.length; inx++) { 
      if (chars.indexOf(input.value.charAt(inx)) != -1) 
          return true; 
    } 
    return false; 
} 
  
/** 
 * 입력값이 특정 문자(chars)만으로 되어있는지 체크 
 * 특정 문자만 허용하려 할 때 사용 
 * ex) if (!containsCharsOnly(form.blood,"ABO")) { 
 *        alert("혈액형 필드에는 A,B,O 문자만 사용할 수 있습니다."); 
 *    } 
 */ 
function containsCharsOnly(input,chars) { 
    for (var inx = 0; inx < input.value.length; inx++) { 
      if (chars.indexOf(input.value.charAt(inx)) == -1) 
          return false; 
    } 
    return true; 
} 
function isStartWith(input,chars) { 
    for (var inx = 0; inx < chars.length; inx++) { 
      if (chars.indexOf(input.value.charAt(0)) == -1) 
          return false; 
    } 
    return true; 
} 
/** 
 * 입력값이 알파벳인지 체크 
 * 아래 isAlphabet() 부터 isNumComma()까지의 메소드가 
 * 자주 쓰이는 경우에는 var chars 변수를 
 * global 변수로 선언하고 사용하도록 한다. 
 * ex) var uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
 *    var lowercase = "abcdefghijklmnopqrstuvwxyz"; 
 *    var number    = "0123456789"; 
 *    function isAlphaNum(input) { 
 *        var chars = uppercase + lowercase + number; 
 *        return containsCharsOnly(input,chars); 
 *    } 
 */ 
function isAlphabet(input) { 
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz "; 
    return containsCharsOnly(input,chars); 
} 
  
/** 
 * 입력값이 알파벳 대문자인지 체크 
 */ 
function isUpperCase(input) { 
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ "; 
    return containsCharsOnly(input,chars); 
} 
  
/** 
 * 입력값이 알파벳 소문자인지 체크 
 */ 
function isLowerCase(input) { 
    var chars = "abcdefghijklmnopqrstuvwxyz "; 
    return containsCharsOnly(input,chars); 
} 
  
/** 
 * 입력값에 숫자만 있는지 체크 
 */ 
function isNumber(input) { 
    var chars = "0123456789"; 
    return containsCharsOnly(input,chars); 
} 
  
/** 
 * 입력값이 알파벳,숫자로 되어있는지 체크 
 */ 
function isAlphaNum(input) { 
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 "; 
    return containsCharsOnly(input,chars); 
} 
  
function isBigAlphaNum(input) { 
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 "; 
    return containsCharsOnly(input,chars); 
} 
/** 
 * 입력값이 숫자,대시(-)로 되어있는지 체크 
 */ 
function isNumDash(input) { 
    var chars = "-0123456789"; 
    return containsCharsOnly(input,chars); 
} 
  
/** 
 * 입력값이 숫자,콤마(,)로 되어있는지 체크 
 */ 
function isNumComma(input) { 
    var chars = ",0123456789"; 
    return containsCharsOnly(input,chars); 
} 
  
/** 
 * 입력값이 사용자가 정의한 포맷 형식인지 체크 
 * 자세한 format 형식은 자바스크립트의 'regular expression'을 참조 
 */ 
function isValidFormat(input,format) { 
    if (input.value.search(format) != -1) { 
        return true; //올바른 포맷 형식 
    } 
    return false; 
} 
  
/** 
 * 입력값이 이메일 형식인지 체크 
 * ex) if (!isValidEmail(form.email)) { 
 *        alert("올바른 이메일 주소가 아닙니다."); 
 *    } 
 */ 
function isValidEmail(input) { 
//    var format = /^(\S+)@(\S+)\.([A-Za-z]+)$/; 
    var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/; 
    return isValidFormat(input,format); 
} 
  
/** 
 * 입력값이 전화번호 형식(숫자-숫자-숫자)인지 체크 
 */ 
function isValidPhone(input) { 
    var format = /^(\d+)-(\d+)-(\d+)$/; 
    return isValidFormat(input,format); 
} 
  
/** 
 * 입력값의 바이트 길이를 리턴 
 * ex) if (getByteLength(form.title) > 100) { 
 *        alert("제목은 한글 50자(영문 100자) 이상 입력할 수 없습니다."); 
 *    } 
 */ 
function getByteLength(input) { 
    var byteLength = 0; 
    for (var inx = 0; inx < input.value.length; inx++) { 
        var oneChar = escape(input.value.charAt(inx)); 
        if ( oneChar.length == 1 ) { 
            byteLength ++; 
        } else if (oneChar.indexOf("%u") != -1) { 
            byteLength += 2; 
        } else if (oneChar.indexOf("%") != -1) { 
            byteLength += oneChar.length/3; 
        } 
    } 
    return byteLength; 
} 
  
/** 
 * 입력값에서 콤마를 없앤다. 
 */ 
function removeComma(input) { 
    return input.value.replace(/,/gi,""); 
} 
  
/** 
 * 선택된 라디오버튼이 있는지 체크 
 */ 
function hasCheckedRadio(input) { 
    if (input.length > 1) { 
        for (var inx = 0; inx < input.length; inx++) { 
            if (input[inx].checked) return true; 
        } 
    } else { 
        if (input.checked) return true; 
    } 
    return false; 
} 
  
/** 
 * 선택된 체크박스가 있는지 체크 
 */ 
function hasCheckedBox(input) { 
    return hasCheckedRadio(input); 
} 
  

/** 
 * 선택된 체크박스가  몇개인지  그 개수를 반환 
 */ 
function hasMultiCheckedRadio(input) { 
var kkkk = 0; 
    if (input.length > 1) { 
        for (var inx = 0; inx < input.length; inx++) { 
            if (input[inx].checked) { 
  kkkk++; 
  } 
        } 
    } else { 
  if (input.checked) kkkk=1; 
 } 
    return kkkk; 
} 
  
/** 
 * 유효한(존재하는) 월(月)인지 체크 
 */ 
function isValidMonth(mm) { 
    var m = parseInt(mm,10); 
    return (m >= 1 && m <= 12); 
} 
  
/** 
 * 유효한(존재하는) 일(日)인지 체크 
 */ 
function isValidDay(yyyy, mm, dd) { 
    var m = parseInt(mm,10) - 1; 
    var d = parseInt(dd,10); 
  
    var end = new Array(31,28,31,30,31,30,31,31,30,31,30,31); 
    if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) { 
        end[1] = 29; 
    } 
  
    return (d >= 1 && d <= end[m]); 
} 
  
/** 
 * 유효한(존재하는) 시(時)인지 체크 
 */ 
function isValidHour(hh) { 
    var h = parseInt(hh,10); 
    return (h >= 1 && h <= 24); 
} 
  
/** 
 * 유효한(존재하는) 분(分)인지 체크 
 */ 
function isValidMin(mi) { 
    var m = parseInt(mi,10); 
    return (m >= 1 && m <= 60); 
} 
  
/** 
 * Time 형식인지 체크(느슨한 체크) 
 */ 
function isValidTimeFormat(time) { 
    return (!isNaN(time) && time.length == 12); 
} 
  
/** 
 * 유효하는(존재하는) Time 인지 체크 
 * ex) var time = form.time.value; //'200102310000' 
 *    if (!isValidTime(time)) { 
 *        alert("올바른 날짜가 아닙니다."); 
 *    } 
 */ 
function isValidTime(time) { 
    var year  = time.substring(0,4); 
    var month = time.substring(4,6); 
    var day  = time.substring(6,8); 
    var hour  = time.substring(8,10); 
    var min  = time.substring(10,12); 
  
    if (parseInt(year,10) >= 1900  && isValidMonth(month) && 
        isValidDay(year,month,day) && isValidHour(hour)  && 
        isValidMin(min)) { 
        return true; 
    } 
    return false; 
} 
  
/** 
 * Time 스트링을 자바스크립트 Date 객체로 변환 
 * parameter time: Time 형식의 String 
 */ 
function toTimeObject(time) { //parseTime(time) 
    var year  = time.substr(0,4); 
    var month = time.substr(4,2) - 1; // 1월=0,12월=11 
    var day  = time.substr(6,2); 
    var hour  = time.substr(8,2); 
    var min  = time.substr(10,2); 
  
    return new Date(year,month,day,hour,min); 
} 
  
/** 
 * 자바스크립트 Date 객체를 Time 스트링으로 변환 
 * parameter date: JavaScript Date Object 
 */ 
function toTimeString(date) { //formatTime(date) 
    var year  = date.getFullYear(); 
    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함 
    var day  = date.getDate(); 
    var hour  = date.getHours(); 
    var min  = date.getMinutes(); 
  
    if (("" + month).length == 1) { month = "0" + month; } 
    if (("" + day).length  == 1) { day  = "0" + day;  } 
    if (("" + hour).length  == 1) { hour  = "0" + hour;  } 
    if (("" + min).length  == 1) { min  = "0" + min;  } 
  
    return ("" + year + month + day + hour + min) 
} 
  
/** 
 * Time이 현재시각 이후(미래)인지 체크 
 */ 
function isFutureTime(time) { 
    return (toTimeObject(time) > new Date()); 
} 
  
/** 
 * Time이 현재시각 이전(과거)인지 체크 
 */ 
function isPastTime(time) { 
    return (toTimeObject(time) < new Date()); 
} 
  
/** 
 * 주어진 Time 과 y년 m월 d일 h시 차이나는 Time을 리턴 
 * ex) var time = form.time.value; //'20000101000' 
 *    alert(shiftTime(time,0,0,-100,0)); 
 *    => 2000/01/01 00:00 으로부터 100일 전 Time 
 */ 
function shiftTime(time,y,m,d,h) { //moveTime(time,y,m,d,h) 
    var date = toTimeObject(time); 
  
    date.setFullYear(date.getFullYear() + y); //y년을 더함 
    date.setMonth(date.getMonth() + m);      //m월을 더함 
    date.setDate(date.getDate() + d);        //d일을 더함 
    date.setHours(date.getHours() + h);      //h시를 더함 
  
    return toTimeString(date); 
} 
  
/** 
 * 두 Time이 몇 개월 차이나는지 구함 
 * time1이 time2보다 크면(미래면) minus(-) 
 */ 
function getMonthInterval(time1,time2) { //measureMonthInterval(time1,time2) 
    var date1 = toTimeObject(time1); 
    var date2 = toTimeObject(time2); 
  
    var years  = date2.getFullYear() - date1.getFullYear(); 
    var months = date2.getMonth() - date1.getMonth(); 
    var days  = date2.getDate() - date1.getDate(); 
  
    return (years * 12 + months + (days >= 0 ? 0 : -1) ); 
} 
  
/** 
 * 두 Time이 며칠 차이나는지 구함 
 * time1이 time2보다 크면(미래면) minus(-) 
 */ 
function getDayInterval(time1,time2) { 
    var date1 = toTimeObject(time1); 
    var date2 = toTimeObject(time2); 
    var day  = 1000 * 3600 * 24; //24시간 
  
    return parseInt((date2 - date1) / day, 10); 
} 
  
/** 
 * 두 Time이 몇 시간 차이나는지 구함 
 * time1이 time2보다 크면(미래면) minus(-) 
 */ 
function getHourInterval(time1,time2) { 
    var date1 = toTimeObject(time1); 
    var date2 = toTimeObject(time2); 
    var hour  = 1000 * 3600; //1시간 
  
    return parseInt((date2 - date1) / hour, 10); 
} 
  
/** 
 * 현재 시각을 Time 형식으로 리턴 
 */ 
function getCurrentTime() { 
    return toTimeString(new Date()); 
} 
  
/** 
 * 현재 시각과 y년 m월 d일 h시 차이나는 Time을 리턴 
 */ 
function getRelativeTime(y,m,d,h) { 
  
    return shiftTime(getCurrentTime(),y,m,d,h); 
} 
  
/** 
 * 현재 年을 YYYY형식으로 리턴 
 */ 
function getYear() { 
  
    return getCurrentTime().substr(0,4); 
} 
  
/** 
 * 현재 月을 MM형식으로 리턴 
 */ 
function getMonth() { 
  
    return getCurrentTime().substr(4,2); 
} 
  
/** 
 * 현재 日을 DD형식으로 리턴 
 */ 
function getDay() { 
  
    return getCurrentTime().substr(6,2); 
} 
  
/** 
 * 현재 時를 HH형식으로 리턴 
 */ 
function getHour() { 
  
    return getCurrentTime().substr(8,2); 
} 
  
/** 
 * 오늘이 무슨 요일이야? 
 * ex) alert('오늘은 ' + getDayOfWeek() + '요일입니다.'); 
 */ 
function getDayOfWeek() { 
    var now = new Date(); 
  
    var day = now.getDay(); //일요일=0,월요일=1,...,토요일=6 
    var week = new Array('일','월','화','수','목','금','토'); 
  
    return week[day]; 
} 
  

/** 
 * 특정날짜의 요일을 구한다. 
 */ 
function getDayOfWeek(time) { 
    var now = toTimeObject(time); 
  
    var day = now.getDay(); //일요일=0,월요일=1,...,토요일=6 
    var week = new Array('일','월','화','수','목','금','토'); 
  
    return week[day]; 
} 
  
  
  
/** 
*  문자열의 오른쪽 끝에서 부터 지정된 개수만큼의 문자들을 리턴한다. 
*/ 
  
function substrInverse(str, num) 
{ 
 var len; 
  
 len = str.length; 
  
 return str.substr(len - num, num); 
} 
  
/** 
*  문자열로의 특정위치로부터 지정된 개수의 문자들을 리턴한다. 
*/ 
function substrMid(str, idx, num) 
{ 
 return str.substr( idx-1, num); 
} 
  

/** 
* Cookie설정하기 
*/ 
  
function setCookie(name, value, expire) { 
          document.cookie = name + "=" + escape(value) 
          + ( (expire) ? "; expires=" + expire.toGMTString() : "") 
} 
  
/** 
* Cookie 구하기 
*/ 
  

function getCookie(uName) { 
  
 var flag = document.cookie.indexOf(uName+'='); 
 if (flag != -1) { 
  flag += uName.length + 1 
  end = document.cookie.indexOf(';', flag) 
  
  if (end == -1) end = document.cookie.length 
  return unescape(document.cookie.substring(flag, end)) 
 } 
} 
  
    function Half2Full(HalfVal) 
 { 
        var arg; 
        arg = myHalf2Full(HalfVal); 
  return arg; 
 } 
  
 function myHalf2Full(HalfVal) 
 { 
  var FullChar = [ 
                "　", "！","＂","＃","＄","％","＆","＇","（",    //33~ 
        "）","＊","＋","，","－","．","／","０","１","２",      //41~ 
        "３","４","５","６","７","８","９","：","；","＜",      //51~ 
        "＝","＞","？","＠","Ａ","Ｂ","Ｃ","Ｄ","Ｅ","Ｆ",      //61~ 
        "Ｇ","Ｈ","Ｉ","Ｊ","Ｋ","Ｌ","Ｍ","Ｎ","Ｏ","Ｐ",      //71~ 
        "Ｑ","Ｒ","Ｓ","Ｔ","Ｕ","Ｖ","Ｗ","Ｘ","Ｙ","Ｚ",      //81~ 
        "［","￦","］","＾","＿","｀","Ａ","Ｂ","Ｃ","Ｄ",      //91~ 
        "Ｅ","Ｆ","Ｇ","Ｈ","Ｉ","Ｊ","Ｋ","Ｌ","Ｍ","Ｎ",      //101~ 
        "Ｏ","Ｐ","Ｑ","Ｒ","Ｓ","Ｔ","Ｕ","Ｖ","Ｗ","Ｘ",      //111~ 
        "Ｙ","Ｚ","｛","｜","｝","～"                        //121~ 
        ]; 
  var stFinal = ""; 
        var ascii; 
        for( i = 0; i < HalfVal.length; i++) 
        { 
                ascii = HalfVal.charCodeAt(i); 
                if( (31 < ascii && ascii < 128)) 
                { 
                  stFinal += FullChar[ascii-32]; 
                } 
                else 
                { 
                  stFinal += HalfVal.charAt(i); 
                } 
        } 
        return stFinal; 
 } 
function frmMoney(input){ 
 input.value = putComma(input); 
} 
function unFrmMoney(input){ 
 input.value = replace(input.value,",",""); 
} 
function frmDate(input){ 
 if(input.value=="") return 
 input.value = input.value.substring(0,4) + "-" + input.value.substring(4,6) + "-" + input.value.substring(6,8); 
} 
function unFrmDate(input){ 
 input.value = replace(input.value,"-",""); 
} 
function frmTime(input){ 
 input.value = input.value.substring(0,2) + ":" + input.value.substring(2,4) + ":" + input.value.substring(4,6); 
} 
function unFrmTime(input){ 
 input.value = replace(input.value,":",""); 
} 
function frmAcct(input){ 
 input.value = input.value.substring(0,3) + "-" + input.value.substring(3,9) + "-" + input.value.substring(9,14); 
} 
function unFrmAcct(input){ 
 input.value = replace(input.value,"-",""); 
} 
  
function setSelect(input,str) { 
 for(i=0;i<input.options.length;i++){ 
  if(input.options[i].value == str) 
  input.options[i].selected=true; 
 } 
} 
// 외환에서 특정 통화일때 소수점이하 금액없애기 
function Curr(str1, str2){ 
 obj1 = eval("frm."+str1+".value") 
 obj2 = eval("frm."+str2+".style") 
 if(obj1=="JPY"||obj1=="ITL"||obj1=="BEF"||obj1=="KRW"){ 
  obj2.display = "none" 
 }else{ 
  obj2.display = "" 
 } 
} 
function Curr2(str1, str2, str3){ 
 obj1 = eval("frm."+str1+".value") 
 obj2 = eval("frm."+str2+".style") 
 obj3 = eval("frm."+str3+".style") 
 if(obj1=="JPY"||obj1=="ITL"||obj1=="BEF"||obj1=="KRW"){ 
  obj2.display = "none" 
  obj3.display = "none" 
 }else{ 
  obj2.display = "" 
  obj3.display = "" 
 } 
} 
  
  //////////////////////////////////////////////////////////////// 
  // 데이터 전송형태 관련 
  //////////////////////////////////////////////////////////////// 
  
    // get 방식의 파라미터를 해당폼에 input hidden 객체로 생성한다. 
    function get2post(frm,sSearch){ 
    if (sSearch.length > 0) { 
    
      var asKeyValues = sSearch.split('&'); 
      var asKeyValue  = ''; 
        
      for (var i = 0; i < asKeyValues.length; i++) { 
      
      asKeyValue = asKeyValues[i].split('='); 
      var e = document.createElement("input"); 
      e.setAttribute("type","hidden"); 
      e.setAttribute("name",asKeyValue[0]); 
      e.setAttribute("value",asKeyValue[1]); 
      e.setAttribute("_temp","true"); 
      
    //  alert("[" + e.name +"]:[" + e.value +"]"); 
      
      frm.appendChild(e); 
      } 
      } 
    //  alert("form 객체 갯수" + frm.elements.length); 
    }  
    
    // get2post로 생성한 임시 객체를 파괴한다.  
    function removeTempAttribute(frm){ 
    var idx=0; 
    while (idx<frm.elements.length) { 
      var obj = frm.elements[idx]; 
      
      if( obj.getAttribute("_temp") != null && obj.getAttribute("_temp") == "true"){ 
      frm.removeChild(obj); 
      continue; 
      } 
      idx++; 
    } 
    }  
    
    
  
  //////////////////////////////////////////////////////////////// 
  // checkbox 관련 
  //////////////////////////////////////////////////////////////// 
  
    // check 한 개수를 리턴한다. 
    function getCheckedCount( aElem ) { 
    
    var elem = document.all; 
    var cnt = 0; 
    
    for ( var i=0; i<document.all.length; i++ ) { 
      if ( ( elem[i].type == "checkbox" ) && ( elem[i].checked ) && ( elem[i].name == aElem ) ) cnt = cnt + 1; 
    } 
    
    return cnt; 
    } 
    
    
    // 지정한 이름을 가진 모든 checkbox를 check 한다. 
    function checkAll( aElem ) { 
    
    var elem = document.all; 
    var cnt = 0; 
    
    for ( var i=0; i<document.all.length; i++ ) { 
      if ( ( elem[i].type == "checkbox" ) && ( elem[i].name == aElem ) ) elem[i].checked = true; 
    } 
    } 
    
    
    // 지정한 이름을 가진 모든 checkbox의 checked 값을 반전 한다. 
    function invertCheck( aElem ) { 
    
    var elem = document.all; 
    var cnt = 0; 
    
    for ( var i=0; i<document.all.length; i++ ) { 
      if ( ( elem[i].type == "checkbox" ) && ( elem[i].name == aElem ) ) { 
      if ( elem[i].checked ) { 
        elem[i].checked = false; 
      } 
      else{ 
        elem[i].checked = true; 
      } 
      } 
    } 
    }  
    
    
  //////////////////////////////// 
  // UTIL 함수 
  //////////////////////////////// 
  
    var isDivEvent = false; 
    
    function hideOneNav(){ 
    if (!isDivEvent) { 
      window.account.style.visibility='hidden'; 
    } 
    else{ 
      isDivEvent = false; 
    } 
    } 
    
    
    function showOneNav(obj){ 
    isDivEvent = true; 
    window.account.style.left = getLeftPos(obj); 
    window.account.style.top = getTopPos(obj) + obj.offsetHeight - 8; 
    window.account.style.visibility='visible'; 
    return false; 
    } 
    
    function getLeftPos(obj){ 
    var parentObj = null; 
    var clientObj = obj; 
    var left = obj.offsetLeft + document.body.clientLeft; 
    
    while((parentObj=clientObj.offsetParent) != null){ 
      left = left + parentObj.offsetLeft; 
      clientObj = parentObj; 
    } 
    
    return left; 
    } 
    
    function getTopPos(obj){ 
    var parentObj = null; 
    var clientObj = obj; 
    var top = obj.offsetTop + document.body.clientTop; 
    
    while((parentObj=clientObj.offsetParent) != null){ 
      top = top + parentObj.offsetTop; 
      clientObj = parentObj; 
    } 
    
    return top; 
    } 
  
    /** 
    *  문자열에 있는 특정문자패턴을 다른 문자패턴으로 바꾸는 함수. 
    */ 
    
    function replace(targetStr, searchStr, replaceStr) 
    { 
    var len, i, tmpstr; 
    
    len = targetStr.length; 
    tmpstr = ""; 
    
    for ( i = 0 ; i < len ; i++ ) { 
      if ( targetStr.charAt(i) != searchStr ) { 
      tmpstr = tmpstr + targetStr.charAt(i); 
      } 
      else { 
      tmpstr = tmpstr + replaceStr; 
      } 
    } 
    return tmpstr; 
    } 
  
    /** 
    *  문자열에서 좌우 공백제거 
    */ 
    
    function trim(str) 
    { 
             return replace(str," ",""); 
    } 
  
    /** 
    * 콤마설정. 
    */ 
    
    function putComma(input) { 
    var num = input; 
    
    if (num < 0) { 
      num *= -1; 
      var minus = true 
    }else{ 
      var minus = false 
    } 
    
    var dotPos = (num+"").split(".") 
    var dotU = dotPos[0] 
    var dotD = dotPos[1] 
    var commaFlag = dotU.length%3 
    
    if(commaFlag) { 
      var out = dotU.substring(0, commaFlag) 
      if (dotU.length > 3) out += "," 
    } 
    else var out = "" 
    
    for (var i=commaFlag; i < dotU.length; i+=3) { 
      out += dotU.substring(i, i+3) 
      if( i < dotU.length-3) out += "," 
    } 
    
    if(minus) out = "-" + out 
    if(dotD) return out + "." + dotD 
    else return out 
    } 
  
  
    //월의 끝 일자 얻기 
    function getEndDate(datestr){ 
    
    //널인지? 
    if(isEmpty(datestr)){ 
      return null; 
    } 
    
    //숫자인지? 
    if(!isNum(datestr)){ 
      return null; 
    } 
      
    //길이가 8자리? 
    if(datestr.length != 6){ 
      return null; 
    } 
    
    var yy = Number(datestr.substring(0,4)); 
    var mm = Number(datestr.substring(4,6)); 
    
    //윤년 검증 
    var boundDay = ""; 
  
    if(mm != 2){ 
      var mon=new Array(31,28,31,30,31,30,31,31,30,31,30,31); 
      boundDay = mon[mm-1]; 
    } 
    else{ 
      if (yy%4 == 0 && yy%100 != 0 || yy%400 == 0){ 
      boundDay = 29; 
      } 
      else{ 
      boundDay = 28; 
      } 
    } 
    
    return boundDay;  
    } 
  
    // Left 빈자리 만큼 padStr 을 붙인다. 
    function lpad(src, len, padStr){ 
    var retStr = ""; 
    var padCnt = Number(len) - String(src).length; 
    for(var i=0;i<padCnt;i++) retStr += String(padStr); 
    return retStr+src; 
    } 
  
    // Right 빈자리 만큼 padStr 을 붙인다. 
    function rpad(src, len, padStr){ 
    var retStr = ""; 
    var padCnt = Number(len) - String(src).length; 
    for(var i=0;i<padCnt;i++) retStr += String(padStr); 
    return src+retStr; 
    } 
  
  
    // 전화번호 국번검증 
    function isValidDDDPhoneNum(dddphonenum) 
    { 
    
    // 널인가? 
    if (isEmpty(dddphonenum)) { 
      return null; 
    } 
    
      
    if ( dddphonenum != "02" && dddphonenum != "031" && dddphonenum != "032" && dddphonenum != "033" && dddphonenum != "041" && 
          dddphonenum != "042" && dddphonenum != "043" && dddphonenum != "051" && dddphonenum != "052" && dddphonenum != "053" && 
          dddphonenum != "054" && dddphonenum != "055" && dddphonenum != "061" && dddphonenum != "062" && dddphonenum != "063" && 
          dddphonenum != "064" && dddphonenum != "011" && dddphonenum != "016" && dddphonenum != "017" && dddphonenum != "018" && dddphonenum != "019" ) 
    { 
      
      ERR_MSG = "잘못된 전화번호 국번입니다."; 
      return false; 
    } 
    
    return true; 
    
    } 
  
  
    // 대문자변환 
    function toUpperCase(str){ 
    
    if(isEmpty(str)) return str; 
    return str.toUpperCase(); 
    } 
  
    
    // 숫자검증 
    function isNum(str){ 
    
    if(isEmpty(str)) return false; 
    
    for(var idx=0;idx < str.length;idx++){ 
      if(str.charAt(idx) < '0' || str.charAt(idx) > '9'){ 
      return false; 
      } 
    } 
    return true; 
    } 
  
  
    // 영문자검증 
    function isAlpha(str){ 
    
    if(isEmpty(str)) return false; 
    
    for(var idx=0;idx < str.length;idx++){ 
      if(!((str.charAt(idx) >='a' && str <= 'z') || (str.charAt(idx) >= 'A' && str <= 'Z'))){ 
      return false; 
      } 
    } 
    return true; 
    } 
  
  
    // 한글검증 
    function isHangul(str){ 
    
    if(isEmpty(str)) return false; 
    
    for(var idx=0;idx < str.length;idx++){ 
          var c = escape(str.charAt(idx)); 
          if ( c.indexOf("%u") == -1 ) { 
      return false; 
      } 
    } 
    return true;  
    } 
  
    
    // 실제길이 반환( 한글 2byte 계산 ) 
    function getByteLength(s){ 
    
      var len = 0; 
      if ( s == null ) return 0; 
      for(var i=0;i<s.length;i++){ 
          var c = escape(s.charAt(i)); 
          if ( c.length == 1 ) len ++; 
          else if ( c.indexOf("%u") != -1 ) len += 2; 
          else if ( c.indexOf("%") != -1 ) len += c.length/3; 
      } 
      return len; 
    } 
    
    
    // 빈값인지 리턴한다. 
    function isEmpty(pValue){ 
    
    if( (pValue == "") || (pValue == null) ){ 
      return true; 
    } 
    return false; 
    } 
    
    
  
  
    //검색날짜 유효기간 
    function getBoundDate1(yy,mm,dd,stdDate) 
    { 
    var today = new Date(); 
    today.setYear(stdDate.substring(0,4)); 
    today.setMonth(stdDate.substring(4,6)-1); 
    today.setDate(stdDate.substring(6,8)); 
    today.setHours(today.getHours()); 
    today.setMinutes(today.getMinutes()); 
    today.setSeconds(today.getSeconds()); 
    
    yy = Number(yy); 
    mm = Number(mm); 
    dd = Number(dd); 
    
    var date = new Date(); 
    
    var DAY = 24 * 60 * 60 * 1000; 
    
    if ( yy != 0 ){ 
      date.setTime(today.getTime() + DAY * 365 * yy); 
    } 
  
    if ( mm != 0 ){ 
      date.setTime(today.getTime() + DAY * 30 * mm); 
    } 
    
    if ( dd != 0 ){ 
      date.setTime(today.getTime() + DAY * dd); 
    } 
    
    return lpad(new String(date.getYear()),4,'0') + lpad(new String(date.getMonth() + 1),2,'0') + lpad(new String(date.getDate()),2,'0');    
    }  
  
  
  
    function getBoundDate(yy, mm, dd) { 
    yy = Number(yy); 
    mm = Number(mm); 
    dd = Number(dd); 
    
    var date = new Date(); 
    
    var DAY = 24 * 60 * 60 * 1000; 
  
    
    if ( yy != 0 ){ 
      date.setTime(datToday.getTime() + DAY * 365 * yy); 
    } 
  
    if ( mm != 0 ){ 
      date.setTime(datToday.getTime() + DAY * 30 * mm); 
    } 
    
    if ( dd != 0 ){ 
      date.setTime(datToday.getTime() + DAY * dd); 
    } 
    
    return lpad(new String(date.getYear()),4,'0') + lpad(new String(date.getMonth() + 1),2,'0') + lpad(new String(date.getDate()),2,'0'); 
    }  
    
  
    //검색날짜 체크 
    function isVaildTerm(obj,yy,mm,dd) 
    { 
    var datestr = obj.value; 
    
    
    //널인지? 
    if(isEmpty(datestr)){ 
      return null; 
    } 
    
    // 날짜 포맷제거 
    obj_removeformat(obj); 
    
    //8자리인지? 
    if (getByteLength(datestr) != 8) { 
      alert("날짜는 '-'를 제외한 8자리 숫자로 입력하십시오."); 
      return false; 
      
    } 
  
    
    
    // yy,mm,dd,fromto가 없을 경우 
    if (yy == null) yy = 0; 
    if (mm == null) mm = 0; 
    if (dd == null) dd = 0; 
    
    // 검색날짜 유효기간 가져오기 
    var boundDate = getBoundDate(yy,mm,dd); 
    
    if (yy < 0  || mm < 0  || dd < 0) { 
      if ( boundDate > datestr) { 
      alert("유효하지 않은 검색날짜입니다.\n유효한 날짜는" + boundDate.substring(0,4) + "년 " + boundDate.substring(4,6) + "월 " + boundDate.substring(6) + "일부터 입니다."); 
      obj.select(); 
      return false; 
      } 
    } else { 
      if ( boundDate < datestr) { 
      alert("유효하지 않은 검색날짜입니다.\n유효한 날짜는" + boundDate.substring(0,4) + "년 " + boundDate.substring(4,6) + "월 " + boundDate.substring(6) + "일까지 입니다."); 
      obj.select(); 
      return false; 
      }    
    } 
      
  
    return true; 
    
    } 
  
  
  
    //오늘날짜 
    function getToDay() 
    { 
  
        var date = datToday; 
  
        var year  = date.getFullYear(); 
        var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함 
        var day  = date.getDate(); 
    
        if (("" + month).length == 1) { month = "0" + month; } 
        if (("" + day).length  == 1) { day  = "0" + day;  } 
        
        return ("" + year + month + day) 
    
    } 
    
     
    
    function selectComboBox(targt, optValue) 
    { 
    last = targt.length; 
    for(var i=0; i<last; i++){ 
      if(targt.options[i].value == optValue){ 
      targt.selectedIndex = i; 
      targt.options[i].selected; 
      } 
    } 
    } 
    
    
    function isExistsComboBoxValue(targt, optValue) 
    { 
    last = targt.length; 
    for(var i=0; i<last; i++){ 
      if(targt.options[i].value == optValue){ 
      return true; 
      } 
    } 
    return false; 
    } 
  
    
    function getCal(aFrm, aObj){ 
      window.open('/kor/ib/common/msg/cal.jsp?objName=window.opener.'+ aFrm.name +'.' + aObj.name +'&schdate='+ aFrm.name +'.' + aObj.name ,"Window2","status=no,height=150,width=120,resizable=no,left="+x+",top="+y+",scrollbars=no"); 
    } 
    
    function getCalMonth(aFrm, aObj){ 
      window.open("/kor/ib/common/msg/calendar_month.jsp?frmName="+aFrm.name + "&obj=" + aObj.name,"calMonth","status=no,height=146,width=255,resizable=no,left="+x+",top="+y+",scrollbars=no"); 
    } 
  
    
/* 
* 전화번호 
* 앞에 '0'을 채운다 
* by 황상훈 2002-10-23 11:26오후 
**/ 
  
function fill_zero(obj, is4){ 
 var temp=""; 
  
 if(obj.value == null || obj.value.length < 1 ) { 
  return false; 
 } 
  
 if (is4 == 'Y' ) { 
  return true; 
 } else { 
  if(obj.value.length != 4 ) { 
  for(i=0;i<(4-obj.value.length);i++){ 
    temp +="0"; 
  } 
  obj.value = temp+obj.value; 
  }else{ 
  obj.value = obj.value; 
  } 
  
  return true; 
 } 
} 

function openWindow(s)
{
         window.open(s,"open","scrollbars=no, resizable=no, copyhistory=no, width=337,height=100, left=50, top=50");
}


function closeWindow(ele, Check)
{
         	 if(Check == 'UY')
             {
			    ele.passwd.focus();
			    //ele.passwd.value = "";
		     } else if (Check == 'UN')
		     {
               ele.user_id.focus();
			   ele.user_id.value = "";
		     } else if (Check == 'AY')
		     {
               ele.e_mail.focus();
			   //ele.e_mail.value = "";
		     } else if (Check == 'AN')
		     {
               ele.phone.focus();
			   ele.phone.value = "";
		     }
		
		     window.close();
}





function openLoginInfoWindow(url, popupName)
{
	newWindow= window.open(url ,popupName ,"scrollbars=no, resizable=no, copyhistory=no, width=365, height=160, left=50, top=50");
}



// 숫자만을 기입받게 하는 방법

function onlyNumber()
{
		 if ((event.keyCode<48)||(event.keyCode>57)) {
              if (event.keyCode!=13) {
				  if (event.keyCode!=8)
				  {
					  alert("숫자로만 입력해 주세요!!");
                      event.returnValue=false;
				  } 
              }
         }
}


function validate_for_integers(inputfield, inputevent, zipcode) {
	var key;
	var keychar;

	if (zipcode.value.length == 5) {
		if (window.event) {
			key = window.event.keyCode;
		} else if (inputevent) {
			key = inputevent.which;
		} else {
			return true;
		}

		keychar = String.fromCharCode(key);
		// control keys
		if ((key==null) || (key==0) || (key==8) ||
    			(key==9) || (key==13) || (key==27)) {
			return true;

		// numbers
		} else if (("0123456789-").indexOf(keychar) > -1) {
			return true;
		} else {
			return false;
		}
	} else {
		if (window.event) {
			key = window.event.keyCode;
		} else if (inputevent) {
			key = inputevent.which;
		} else {
			return true;
		}

		keychar = String.fromCharCode(key);
		// control keys
		if ((key==null) || (key==0) || (key==8) || (key==9) || (key==13) || (key==27)) {
			return true;

		// numbers
		} else if (("0123456789").indexOf(keychar) > -1) {
			return true;
			
		} else {
			return false;
		}
	}
} // end of validate_for_integers()



function remarkCheck() {
	     
		 if(window.event.keyCode == 222 || window.event.keyCode == 188)
	     {
			alert(" , 와 ' 또는 \" 특수문자는 사용할 수 없습니다!");
			event.returnValue = false;
         }
}

function checkspace(str) { 
         var index,len   //index,len 두변수 선언 
         while(true) 
	     { 
            index = str.indexOf(" ") //함수내의 선언한 변수의 null을 index에 전환하는 내용 
            if (index==-1) break;     //만약 index가 -1 즉,공백이 없는경우에는 종료한다. 
            len = str.length;         //문자열의 길이를 구한다. 공백 포함하여 길이 구함 
            str = str.substring(0,index) + str.substring((index+1),len) //문자열의 길이중에 빈공간이 있으면 삭제하고 붙여놓는 역할을 한다. 
         } 
         return str; 
}

function ComSubmit(opt_formId) {
    this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
    this.url = "";
     
    if(this.formId == "commonForm"){
        $("#commonForm")[0].reset();
    }
     
    this.setUrl = function setUrl(url){
        this.url = url;
    };
     
    this.addParam = function addParam(key, value){
        $("#"+this.formId).append($("<input type='hidden' name='"+key+"' id='"+key+"' value='"+value+"' >"));
    };
     
    this.submit = function submit(){
        var frm = $("#"+this.formId)[0];
        frm.action = this.url;
        frm.method = "post";
        frm.submit();   
    };
}
function gfn_isNull(str) {
    if (str == null) return true;
    if (str == "NaN") return true;
    if (new String(str).valueOf() == "undefined") return true;    
    var chkStr = new String(str);
    if( chkStr.valueOf() == "undefined" ) return true;
    if (chkStr == null) return true;    
    if (chkStr.toString().length == 0 ) return true;   
    return false; 
}

var gfv_ajaxCallback = "";
function ComAjax(opt_formId){
    this.url = "";      
    this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
    this.param = "";
     
    if(this.formId == "commonForm"){
        var frm = $("#commonForm");
        if(frm.length > 0){
            frm.remove();
        }
        var str = "<form id='commonForm' name='commonForm'></form>";
        $('body').append(str);
    }
     
    this.setUrl = function setUrl(url){
        this.url = url;
    };
     
    this.setCallback = function setCallback(callBack){
        fv_ajaxCallback = callBack;
    };
 
    this.addParam = function addParam(key,value){ 
        this.param = this.param + "&" + key + "=" + value; 
    };
     
    this.ajax = function ajax(){
        if(this.formId != "commonForm"){
            this.param += "&" + $("#" + this.formId).serialize();
        }
        $.ajax({
            url : this.url,    
            type : "POST",   
            data : this.param,
            async : false, 
            success : function(data, status) {
                if(typeof(fv_ajaxCallback) == "function"){
                    fv_ajaxCallback(data);
                }
                else {
                    eval(fv_ajaxCallback + "(data);");
                }
            }
        });
    };
}
 