if (!window.JSSHOP) {
    JSSHOP = new Object();
}
if (!window.JSSHOP.shared) {
    JSSHOP.shared = new Object();
}
if (!window.JSSHOP.ajax) {
    JSSHOP.ajax = new Object();
}
if (!window.JSSHOP.hookloader) {
    JSSHOP.hookloader = new Object();
}

JSSHOP.shared.getAppReq = function(thefield) {
    try {
	app.doDB(document.getElementById(thefield).value)
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.shared.getAppReq");
    }
};
JSSHOP.shared.loadScript = function(path, callback, filetype) {

        n = path.lastIndexOf("/");
        q = path.lastIndexOf("?");
        if (filetype == "js") { //if filename is a external JavaScript file
            var scr = document.createElement('script')
            scr.setAttribute("type", "text/javascript")
            scr.src = path;
        } else if (filetype == "css") { //if filename is an external CSS file
            var scr = document.createElement("link")
            scr.setAttribute("rel", "stylesheet")
            scr.setAttribute("type", "text/css")
            scr.href = path;
        }
        var done = false;
        scr.onload = handleLoad;
        scr.onreadystatechange = handleReadyStateChange;
        scr.onerror = handleError;
        if (n >= 0) {
            if (q >= 0) {
                tid = path.substring(n + 1, q);
            } else {
                tid = path.substring(n + 1);
            }
            scr.id = tid;
        }
        document.getElementsByTagName("head")[0].appendChild(scr);
        // document.body.appendChild(scr);
        function handleLoad() {
            if (!done) {
                done = true;
                callback(path, "ok");
            }
        }

        function handleReadyStateChange() {
            var state;
            if (!done) {
                state = scr.readyState;
                if (state === "complete") {
                    handleLoad();
                }
            }
        }

        function handleError() {
            if (!done) {
                done = true;
                callback(path, "error");
            }
        }
     try {   } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.shared.loadScript");
    }
};

JSSHOP.shared.checkLoader = function(thePath, theMessage) {
    try {
        ttlLoaded = loaded_scripts.length;
        loaded_scripts[ttlLoaded] = thePath;
        //  alert(loaded_scripts[ttlLoaded] + " :: " + loaded_scripts.length + "msg: " + theMessage);
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.shared.checkLoader");
    }
};
JSSHOP.shared.endsWith = function(str,suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
};

JSSHOP.shared.urlToArray = function(url) {
    try {
        var request = {};
        var arr = [];
        var pairs = url.substring(url.indexOf('?') + 1).split('&');
        for (var i = 0; i < pairs.length; i++) {
          var pair = pairs[i].split('=');

          //check we have an array here - add array numeric indexes so the key elem[] is not identical.
          if(JSSHOP.shared.endsWith(decodeURIComponent(pair[0]), '[]') ) {
              var arrName = decodeURIComponent(pair[0]).substring(0, decodeURIComponent(pair[0]).length - 2);
              if(!(arrName in arr)) {
                  arr.push(arrName);
                  arr[arrName] = [];
              }

              arr[arrName].push(decodeURIComponent(pair[1]));
              request[arrName] = arr[arrName];
          } else {
            request[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1]);
          }
        }
        return request;
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.shared.urlToArray");
        return "NotGood";
    }

    };


JSSHOP.shared.getQryVar = function(theUrlString, theVar) {
    try {
  strQVal = null;
  var query = theUrlString;
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
    var pair = vars[i].split("=");
    if(pair[0] == theVar) {
      strQVal =  pair[1];
    }
  }
  return strQVal;
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.shared.getQryVar");
        return "NotGood";
    }
};

JSSHOP.shared.getElemDUrl = function(theElem) {
try {
if(theElem.getAttribute("data-ison") != null) {
tDb = "";
tDison = theElem.getAttribute("data-ison");
tDa = tDison.replace(/:/gi,"&");
tDb = tDa.replace(/;/gi,"=");
tDb += "&v=" + theElem.value + "&n=" + theElem.name;
return tDb;
} else {
return null;
}
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.ui.getElemDUrl");
}
};





JSSHOP.shared.nodesToString = function(node) {
    if (typeof node === "string") {
        node = document.getElementById(node);
    }

    var arrayOfText = [];
            var tmpUstring = "";
function walkTheDOM(node, func) {
    func(node);
    node = node.firstChild;
    while (node) {
        walkTheDOM(node, func);
        node = node.nextSibling;
    }
}

    function pushVal(currentNode) {
            tmpNID = "";
		tmpNVal = "";

  		if(currentNode.nodeType === 1) {
 		if((currentNode.id) || (currentNode.name)) {
       		if(currentNode.id){
                  tmpNID = currentNode.id;
			} else {
			tmpNID = currentNode.name;			
			}      
            tNodeName = currentNode.nodeName.toUpperCase();
            switch(tNodeName) {
            case "INPUT":
		tmpNVal = currentNode.value;
		break;
            case "TEXTAREA":
		tmpNVal = currentNode.value;
		break;
            case "SELECT":
		tmpNVal = currentNode.value;
		break;
		default:
		if(currentNode.firstChild.nodeValue) {
		tmpNVal = currentNode.firstChild.nodeValue;
		}
		}
		tmpUstring += "&" + tmpNID.trim() + "=" + tmpNVal.trim();		}		
//		tmpUstring += "&" + escape(JSSHOP.shared.trim(tmpNID)) + "=" + escape(JSSHOP.shared.trim(tmpNVal));		}		
		}

    }
    walkTheDOM(node, pushVal);
    return tmpUstring;
};
// end of shared funtions




//   ---- AJAX functions


JSSHOP.ajax.fnishGeneric = function(strRet, prvdr, action, ukey, id, cbElem, cbElemHTML) {
    try {
        document.getElementById(cbElem).innerHTML = cbElemHTML;
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.fnishGeneric");
    }
};


JSSHOP.ajax.procAjaxRet = function(strRet, prvdr, action, ukey, id, msg, cbElem, cbElemHTML) {
    try {
    switch (action) {
        case "parseU":
            JSSHOP.ajax.fnishGeneric(strRet, prvdr, action, ukey, id, cbElem, cbElemHTML);
            break;
        default:
            JSSHOP.ajax.fnishGeneric(strRet, prvdr, action, ukey, id, cbElem, cbElemHTML);
            break;
    }
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.procAjaxRet");
    }
};


JSSHOP.ajax.procRet = function(strRet, arrFelemnts) {
    try {

        if(arrFelemnts[0]["name"]) {
        if(arrFelemnts[0]["name"].toLowerCase() == "aaction") {
        tmpAVal = arrFelemnts[0]["value"];
    switch(tmpAVal) {
        case "CViewEditAdminSettings":
		JSSHOP.ui.popAndFillLbox("new: " + JSON.stringify(arrFelemnts) + " <br> " + strRet);
            break;
        default:
            JSSHOP.ajax.fnishGeneric(strRet, prvdr, action, ukey, id, cbElem, cbElemHTML);
            break;
    }
    }
    }
    } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.procRet");
    }
};

JSSHOP.ajax.createXMLHTTPObject = function() {
    var xmlhttp = false;
var XMLHttpFactories = [
    function() {
        return new XMLHttpRequest()
    },
    function() {
        return new ActiveXObject("Msxml2.XMLHTTP")
    },
    function() {
        return new ActiveXObject("Msxml3.XMLHTTP")
    },
    function() {
        return new ActiveXObject("Microsoft.XMLHTTP")
    }
];
    for (var i = 0; i < XMLHttpFactories.length; i++) {
        try {
            xmlhttp = XMLHttpFactories[i]();
        } catch (e) {
            continue;
        }
        break;
    }
    return xmlhttp;
};

JSSHOP.ajax.doNuAjaxPipe = function(theElem,pUrl,tmpCB) {
    try {
       var oReq = JSSHOP.ajax.createXMLHTTPObject();
	 tUTA = JSSHOP.shared.urlToArray(pUrl);
	   if(oReq == false) {
               tmpCB(theElem,"Error",tUTA);

	  } else {
        oReq.onreadystatechange = function() {
            if (oReq.readyState == 4) {
               tmpCB(theElem,oReq.responseText,tUTA);
            }
        }
        oReq.open("GET", pUrl, true);
        oReq.send(null);
	   }
        } catch (e) {
               tmpCB(theElem,"Error: " + e,tUTA);
    	  }
};

JSSHOP.ajax.doAjaxPipe = function(pUrl,tmpCB) {
    try {
       JSSHOP.ajax.doNuAjaxPipe(null,pUrl,tmpCB);
        } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.doAjaxPipe");
    	  }
};

JSSHOP.ajax.prepAjaxSbmt = function(tmpBtn, oFormElement,tmpCB) {
	try {
       tmpBtn.className="cls_button cls_button-medium cls_button-disabled";
       tmpBtn.disabled=true;
       JSSHOP.ajax.doAjaxSbmt(oFormElement,tmpCB);
        } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.prepAjaxSbmt");
    		}
};

JSSHOP.ajax.prepAjaxPipe = function(tmpBtn,pUrl,tmpCB) {
	try {
	  if(tmpBtn != null) {
       // tmpBtn.className="cls_button cls_button-medium cls_button-disabled";
       tmpBtn.disabled=true;
	 }
       JSSHOP.ajax.doNuAjaxPipe(tmpBtn,pUrl,tmpCB);
        } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.prepAjaxPipe");
    	  }
};

JSSHOP.ajax.doAjaxSbmt = function(oFormElement,tmpCB) {
	try {
    if (!oFormElement.action) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.doAjaxSbmt: !! No form action set !!");
        return;
    }
    var oReq = JSSHOP.ajax.createXMLHTTPObject();
        var oField, sFieldType, nFile, sSearch;
        for (var nItem = 0; nItem < oFormElement.elements.length; nItem++) {
            oField = oFormElement.elements[nItem];
            if (!oField.getAttribute("name")) {
                continue;
            }
            sFieldType = oField.nodeName.toUpperCase() === "INPUT" ? oField.getAttribute("type").toUpperCase() : "TEXT";
            if (sFieldType === "FILE") {
                for (nFile = 0; nFile < oField.files.length; sSearch += "&" + escape(oField.name) + "=" + escape(oField.files[nFile++].name));
            } else if ((sFieldType !== "RADIO" && sFieldType !== "CHECKBOX") || oField.checked) {
                sSearch += "&" + escape(oField.name) + "=" + escape(oField.value);
            }
        }
        oReq.onreadystatechange = function() {
            if (oReq.readyState == 4) {
               tmpCB(oReq.responseText,oFormElement.elements);
            }
        }
        oReq.open(oFormElement.method, oFormElement.action + "?" + sSearch, true);
        oReq.send(null);
        } catch (e) {
        JSSHOP.logJSerror(e, arguments, "JSSHOP.ajax.doAjaxSbmt");
    	  }
};


/*
* hookloader functions
*/

JSSHOP.hookloader.hooks = {};
JSSHOP.hookloader.register = function(name, callback) {
    if('undefined' == typeof(JSSHOP.hookloader.hooks[name]))
    JSSHOP.hookloader.hooks[name] = [];
    JSSHOP.hookloader.hooks[name].push(callback);
  };

JSSHOP.hookloader.call = function(name, arguments) {
    if('undefined' != typeof(JSSHOP.hookloader.hooks[name]))
      for(i = 0; i < JSSHOP.hookloader.hooks[name].length; ++i)
        if( true != JSSHOP.hookloader.hooks[name][i]( arguments )) { break; }
};


