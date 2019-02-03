(function($){
	jLoadContent = function(_msg)
    {
    $('#includedContent').load(_msg);
    }
    jLoadNav = function(_msg)
    {
    $('#includedNav').load(_msg);
    }
    jLoadHeader = function(_msg)
    {
    $('#includedHeader').load(_msg);
    }
    jLoadFooter = function(_msg)
    {
    $('#includedFooter').load(_msg);
    }

	jLoadModal = function(_msg)
    {
    $('.modal').css('padding-left', '12px');
    $('.modal').css('top', $(document).scrollTop() + 20);
    if(_msg.indexOf("images/") != -1) {
    imgmodbox.src = _msg;  
    $('#modalImgBox').modal('show');  
    } else {
    $('#modalBox').load(_msg);
    $('#modalBox').modal('show');
    }
    }
 


})(jQuery)
var pfRet = function(theElem, theResp, marble) {
document.getElementById(theElem).innerHTML = theResp;
}
var loadIHModal = function (theMinc) {
jLoadIHModal(theMinc);
};
var loadImgModal = function (theMinc) {
jLoadImgModal(theMinc);
//modalBox.innerHTML = window.frames[0].document.body.innerHTML;
// modFrame.src = theMinc;
};
var loadModal = function (theMinc) {
jLoadModal(theMinc);
//modalBox.innerHTML = window.frames[0].document.body.innerHTML;
// modFrame.src = theMinc;
};
var loadMContent = function (theMinc) {
jLoadContent(theMinc);
};
var loadMHeader = function (theMinc) {
// JSSHOP.ajax.doNuAjaxPipe("includedHeader", theMinc, pfRet);
jLoadHeader(theMinc);
};
var loadMNav = function (theMinc) {
jLoadNav(theMinc);
};
var loadMFooter = function (theMinc) {
jLoadFooter(theMinc);
};


var fillDivs = function() {
}


var doBtmJSLoad = function(thef) {
try {

// JSSHOP.shared.loadScript(thef + "js/jquery.min.js", JSSHOP.shared.checkLoader,"js");

(function($) {
	$(window).load(function(){
		$(".loading").addClass("fade-out");
		$(".loading .table .inner").addClass("fade-out-inner"); 		
 		// $("#my-thumbs-list").mThumbnailScroller({ axis:"x", });
	});
})(jQuery)

} catch(e) {
alert(e);
// JSSHOP.logJSerror(e, arguments, "doBootLoad");
}
};


var doBootLoad = function() {
// JSSHOP.shared.loadScript("js/x_ui.js", JSSHOP.shared.checkLoader,"js");
try {} catch(e) {
JSSHOP.logJSerror(e, arguments, "doBootLoad");
}
};


var doJSload = function() {
loadMHeader(getMHeader());
loadMNav(getMNav());
loadMContent(getMContent());
loadMFooter("tplates/index_footer.html");
// doBootLoad();
try {} catch(e) {
// alert(e);
JSSHOP.logJSerror(e, arguments, "doJSload");
}
};

 

try {
if (window.addEventListener){
window.addEventListener("load", doJSload, false);
} else if (window.attachEvent) {
window.attachEvent("onload", doJSload);
} else {
window.onload = doJSload;
}
} catch(e) {
alert("window.addEventListener: " + e);
}

  
