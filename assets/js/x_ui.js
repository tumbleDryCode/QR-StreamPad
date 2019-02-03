if (!window.JSSHOP) {
    JSSHOP = new Object();
}
if (!window.JSSHOP.ui) {
    JSSHOP.ui = new Object();
}


JSSHOP.ui.showHideElement = function(element, showHide) {
    try {
        theElement = document.getElementById(rowname);
        if (showHide == "show") {
            theElement.style.visibility = "visible";
            theElement.style.display = "block";
        } else {
            theElement.style.visibility = "hidden";
            theElement.style.display = "none";
        }
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ui.showHideElement");
    }
};

JSSHOP.ui.toggleVisibility = function(rowname) {
    try {
        theRow = document.getElementById(rowname);
        if (theRow.style.display == "none") {
            theRow.style.display = "block";
            theRow.style.visibility = "visible";
        } else {
            theRow.style.display = "none";
            theRow.style.visibility = "hidden";
        }
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ui.toggleVisibility");
    }
};

JSSHOP.ui.toggleModule = function(linkElem, tglElemID, showMore, showLess) {
    try {
        caption = document.getElementById(linkElem).innerHTML;
        JSSHOP.ui.toggleVisibility(tglElemID);
        if (showMore == "noQvalue") { // dont change inner HTML
        } else {
            if (caption == showMore) {
                document.getElementById(linkElem).innerHTML = showLess;
            } else {
                document.getElementById(linkElem).innerHTML = showMore;
            }
        }
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ui.toggleModule");
    }
};



JSSHOP.ui.setCBBClickEnd = function(theElem, theID, theCBclss, theCB) {
    try {
   } catch(e) {
	JSSHOP.logJSerror(e, arguments, "JSSHOP.ui.setCBBClickEnd");
    }
      theElem.className = theCBclss;
      JSSHOP.stopIntervalEvent(trgr_bclck[theID]);
      theCB();
      return;
};


// setCBBClickClr(dvBtnSTImg, 'rtable bkgdClrHdr', 'rtable bkgdClrNrml crsrPointer', function() { y = 'y'; });
JSSHOP.ui.setCBBClickClr = function(theElem, theCclss, theCBclss, theCB) {
    try {
   } catch(e) {
	JSSHOP.logJSerror(e, arguments, "JSSHOP.ui.setCBBClickClr");
    }
    		strJobThread = JSSHOP.getUnixTimeStamp();
   		//  theMElem = document.getElementById(theElem);
    		theElem.className = theCclss;
            if (theElem.getAttribute("data-dblclick") == null) { // preventing double clicks
            theElem.setAttribute("data-dblclick", 1);
            setTimeout(function () {
            if (theElem.getAttribute("data-dblclick") == 1) {
		JSSHOP.startIntervalEvent(trgr_bclck[strJobThread], function() { JSSHOP.ui.setCBBClickEnd(theElem, strJobThread, theCBclss, theCB);}, 280);
            }
            theElem.removeAttribute("data-dblclick");
            }, 250);
            } else {
            theElem.removeAttribute("data-dblclick");
            }
 
};


JSSHOP.ui.popLmenu = function() {
try {
timerID = setInterval("JSFX.MakeFloatingLayer.animate()", 30);
setTimeout('clearInterval(timerID)', 2000);
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.popLmenu");
}
};

JSSHOP.ui.deleteRow = function(r) {
try {
    tmpTRowI = r.parentNode.parentNode.rowIndex;
    // document.getElementById("myTable").deleteRow(tmpTRowI);
    r.parentNode.parentNode.deleteRow(tmpTRowI);	
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.ui.deleteRow");
}
};

JSSHOP.ui.popAndFillLbox = function(theFill) {
try {
tmpLbox = document.getElementById('lightbox');
tmpLCbox = document.getElementById('lightbox_content');
tmpLbox.style.display='inline';
if(theFill == "noQvalue") {
} else {
tmpLCbox.innerHTML=theFill;
}
tmpVheight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
if(tmpLCbox.clientHeight >= tmpVheight) {
tmpLbox.style.position="absolute";
newel = document.createElement('div');
newel.innerHTML = "New Inserted";
tmpLCbox.appendChild(newel);
tmpLbox.style.height = tmpLCbox.clientHeight + 180;
}
scroll(0,0);
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.popAndFillLbox");
}
};

JSSHOP.ui.popLbox = function() {
try {
document.getElementById('lightbox').style.display='inline';
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.popLbox");
}
};

JSSHOP.ui.closeLbox = function() {
try {
document.getElementById('lightbox').style.display='none';
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.closeLbox");
}
};

JSSHOP.ui.setffval = function(fname, ffldname, ffldval)
{
try {
document[fname][ffldname].value = ffldval;
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.setffval");
}
};

JSSHOP.ui.setfaction = function(fname, sumbmitbool, alertstring)
{
try {
if(alertstring.length > 1){
if((confirm(alertstring)) && (sumbmitbool)) {
document[fname].submit();
} else {
alertstring = "";
}
}
if(sumbmitbool) {
document[fname].submit();
}
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.setfaction");
}
};


 

function procUIitem(theElem) {
ts = JSSHOP.shared.getElemDUrl(theElem);
if(theElem.getAttribute("data-prval") != null) {
totw = theElem.getAttribute("data-prval");
if(totw != null){
if(totw == theElem.value) {
} else {
theElem.setAttribute("data-prval", theElem.value);
JSADMSHOP.prepAdmPipe(theElem,ts,JSADMSHOP.procNuAdmPipe)
}
}
}
}

 