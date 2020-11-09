<!--
function goUrl(url) {
	window.location=url;
}

function openPopup(popUrl, width, height) {
	window.open(popUrl, '', 'fullscreen=no,toolbar=no,location=no,menubar=no,scrollbars=no,status=yes,width='+width+',height='+height+'');
}

function unLoadAction() {
	if( document.readyState == "complete" ) {
		//���� ��ħ �Ǵ� â �ݱ� 
		alert("������ ��� �ϼ̽��ϴ�!\n������ ó������ �ٽ� �õ��� �ּ���!");
	}
}

function processKey() 
{ 
	if( 
		(event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) 
		|| (event.keyCode >= 112 && event.keyCode <= 123) 
		|| (event.ctrlKey)  
	) 
	{ 
		event.keyCode = 505;
		alert('Ctrl/Function Ű�� ��� �� �� �����ϴ�.');
		event.returnValue = false; 
	} 
} 
document.onkeydown = processKey;
document.onmousedown=processKey; 
document.oncontextmenu = function () {
	alert("���콺 �����ʹ�ư�� �̿��� �� �����ϴ�!");
	return false;
}
//-->
