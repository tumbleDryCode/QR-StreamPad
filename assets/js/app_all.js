        function setConfValInt(theKey, theVal) {
        	app.setConfValInt(theKey, theVal);
        }

        function setConfValString(theKey, theVal) {
        	if(theKey == "confBrowBkgd") {
        		document.body.style.backgroundColor = theVal;
        	}
        	app.setConfValString(theKey, theVal);
        }

        function getConfValInt(theKey) {
        	strConf = app.fetchConfValInt(theKey);
        	return strConf;
        }

        function getConfValString(theKey) {
        	strConf = app.fetchConfValString(theKey);
        	return strConf;
        }

        function setStreamPic(thePicType) {
        	app.setStreamPic(thePicType);
        }

        function showStreamPic(strImgstr) {
        	imgStreamPic.src = "data:image/png;base64, " + strImgstr;
        }

        function setUrlSlider() {
        	app.getUrlSlider();
        }
        var decodeSMSstr = function(theStr) {
        	strPaa = theStr.split("wawa").join("");
        	strPab = strPaa.split("wawe").join("");
        	strPac = strPab.split("fe;").join("</font>");
        	strPad = strPac.split("fs;").join("<font size=\"");
        	strPc = strPad.split("fc;").join("<font color=\"#");
        	strPe = strPc.split("</font>").join("fe;");
        	strPf = strPe.split("b;").join("<b>");
        	strPg = strPf.split("be;").join("</b>");
        	strPh = strPg.split("i;").join("<i>");
        	strPi = strPh.split("ie;").join("</i>");
        	strPj = strPi.split("u;").join("<u>");
        	strPm = strPj.split("x;").join("<img src=\"");
        	strPnc = strPm.split("fs;fc;").join("ffc;");
        	strPnd = strPnc.split("fc;fs;").join("ffs;");
        	strPne = strPnd.split("ffe;").join("</font></font>");
        	strPnf = strPne.split(";;").join(" ");
        	dstrCcomment = strPnf.split("n;").join("\">");
        	return dstrCcomment;
        };
        var encodeSMSstr = function(dstrComment) {
        	/*
        	 * replacements
        	 * !! "<br>"
        	 * !a " MyShop"
        	 * !b " o "
        	 * !c " e "
        	 * !d " de "
        	 * !e " 1kg;"
        	 * !f " 2kg;"
        	 * !g " 1lt;"
        	 * !h "|50;"
        	 * !i "9-1;0"
        	 * !j "-1;0"
        	 */
        	tout = dstrComment;
        	strPb = tout.split(" MyShop").join("!a");
        	strPc = strPb.split(" o ").join("!b");
        	strPd = strPc.split(" e ").join("!c");
        	strPe = strPd.split(" de ").join("!d");
        	strPf = strPe.split(" 1kg;").join("!e");
        	strPg = strPf.split(" 2kg;").join("!f");
        	strPh = strPg.split(" 1lt;").join("!g");
        	strPi = strPh.split("|\n50;").join("!h");
        	strPj = strPi.split("9-1;0").join("!i");
        	strPnf = strPj.split("-1;0").join("!j");
        	strPk = strPnf.split("\n").join("!!");
        	return strPk;
        };

        function setCurrListIndex(theObj, indexVal) {
        	theIndexVal = "noQvalue";
        	var selection = theObj;
        	if(selection) {
        		for(var i = 0; i < selection.length; i++) {
        			var currIndexVal = selection.options[i].value;
        			if(currIndexVal == indexVal) {
        				theIndexVal = currIndexVal;
        				selection.selectedIndex = i;
        				break;
        			}
        		}
        	}
        	return theIndexVal;
        }

        function getCurrListVal(theObj) {
        	var theSelIndex = theObj.selectedIndex;
        	var theString = theObj.options[theSelIndex].text;
        	var theVal = theObj.options[theSelIndex].value;
        	// alert(theString +  " : " + theVal);
        	return theVal;
        }

        function addCurrListVal(theObj, theText, theVal) {
        	theObj.options[theObj.options.length] = new Option(theText, theVal);
        }

        function random_imglink() {
        	var myimages = new Array()
        		//specify random images below. You can have as many as you wish
        	myimages[1] = "icon1.jpg"
        	myimages[2] = "icon2.jpg"
        	myimages[3] = "icon4.jpg"
        	myimages[4] = "icon5.jpg"
        	myimages[5] = "icon6.jpg"
        	myimages[6] = "icon7.jpg"
        	myimages[7] = "icon8.jpg"
        	myimages[8] = "icon9.jpg"
        	var ry = Math.floor(Math.random() * myimages.length)
        	if(ry == 0) {
        		ry = 1
        	}
        	imgStreamPic.src = "images/" + myimages[ry];
        	try {
        		app.setAssetBmp("images/" + myimages[ry]);
        	} catch(e) {}
        	// imgStreamPic.src = "images/icon8.jpg";
        }

        function setPageLoad() {
        	try {
        		app.setPageLoad();
        	} catch(e) {
        		alert("setPageLoad.error: " + e)
        	}
        }

        function setImgLoad() {
        	try {
        		app.setImgLoad();
        	} catch(e) {
        		alert("setPageLoad.error: " + e)
        	}
        }

        function setQRscan() {
        	try {
        		app.setQRscan();
        	} catch(e) {
        		alert("setQRscan.error: " + e)
        	}
        }

        function setFileAlert(theAlert) {
        	//  setTinnerHTML("outDiv", theAlert);
        	// showHideElement("dvCanvCont", "show");
        	document.getElementById("fldChallArray").value = theAlert;
        	// playMovie();
        	OldsetFileAlert();
        }

        function OldsetFileAlert() {
        	try {
        		arrToRet = new Array();
        		arrToMidFill = new Array();
        		arrToMidEat = new Array();
        		arrToMidExplode = new Array();
        		arrToFill = new Array();
        		arrToStr = "";
        		// document.getElementById("fldChallArray").value = theAlert;
        		strFhtml = "";
        		strHtml = "";
        		strTHhtml = "";
        		strFA = document.getElementById("fldChallArray").value;
        		// strHtml = "<table class='sortable autostripe'><thead><tr>";
        		// strHtml += "<th>Desconto</th><th>Artigo</th><th>Validade</th>";
        		// strHtml += "</tr></thead><tbody>";
        		if(strFA.indexOf(";;") != -1) {
        			arrToMidFill = strFA.split(";;");
        			for(var i = 1; i < arrToMidFill.length; i++) {
        				strHtml += "<tr>";
        				if(arrToMidFill[i].indexOf(";") != -1) {
        					arrToMidExplode = arrToMidFill[i].split(";");
        					for(var ii = 0; ii < arrToMidExplode.length; ii++) {
        						if(ii == 0) {
        							strHtml += "<td><img src=\"images/startapp/discount" + arrToMidExplode[ii] + ".png\"><br><img src=\"images/startapp/discount-entrega_zero_m.jpg\" style=\"max-width: 50px\"></td>";
        						} else {
        							strHtml += "<td>" + arrToMidExplode[ii] + "</td>";
        						}
        					}
        					arrToMidExplode = null;
        					strHtml += "</tr>";
        				}
        			}
        			// strHtml += "</tbody></table>";      
        		} else {
        			strHtml += "OOOppps no ;;" + strFA;
        		}
        		arrToMidExplode = arrToMidFill[0].split(";");
        		for(var iai = 0; iai < arrToMidExplode.length; iai++) {
        			strTHhtml += "<th>" + arrToMidExplode[iai] + "</th>";
        		}
        		strFhtml = "<table id=\"tbleStbl\" class=\"sortable autostripe\" style=\"margin: 0 auto\" align=\"center\" onload=\"javascript:standardistaTableSortingInit();\">";
        		strFhtml += "<THEAD><tr>" + strTHhtml + "</tr></THEAD><TBODY>";
        		strFhtml += strHtml + "</TBODY></table>";
        		document.getElementById("tdMidContent").innerHTML = strFhtml;
        		standardistaTableSortingInit();
        	} catch(e) {
        		document.getElementById("tdMidContent").value = e;
        	}
        }

        function setAPPageLoad() {
        	try {
        		app_artpad.getAPPageLoad();
        	} catch(e) {
        		alert("setPageLoad.error: " + e)
        	}
        }
        // main function for ArtPad.java
        function setAPJSComm(effectID) {
        	try {
        		app_dlg.getAPJSComm(effectID);
        	} catch(e) {
        		alert("setAPFilterEffect: " + e)
        	}
        }

        function doPageLoad(pgbgColor, theValA, theValB) {
        	try {
        		document.body.style.backgroundColor = pgbgColor;
        	} catch(e) {
        		alert(e);
        	}
        	try {
        		document.getElementById("tblSharePop").style.backgroundColor = pgbgColor;
        	} catch(e) {
        		// alert(e);
        	}
        }

        function showHideElement(element, showHide) {
        	// alert(element);
        	//function to show or hide elements
        	//element variable can be string or object
        	if(document.getElementById(element)) {
        		element = document.getElementById(element);
        	}
        	if(element) {
        		if(showHide == "show") {
        			element.style.visibility = "visible";
        			try {
        				element.style.display = "block";
        			} catch(e) {}
        		} else {
        			element.style.visibility = "hidden";
        			try {
        				element.style.display = "none";
        			} catch(e) {}
        		}
        	}
        }

        function setTinnerHTML(theElemId, theInnerHtml) {
        	document.getElementById(theElemId).innerHTML = theInnerHtml;
        }

        function setObjBGcolor(theObj, theclr) {
        	theObj.style.backgroundColor = theclr;
        }

        function setStoryShare(sNwork) {
        	try {
        		showHideElement('tdFormControls', 'show');
        		showHideElement('epSharePop', 'hide');
        		stitle = document.getElementById("taStitle").value;
        		sdesc = document.getElementById("taSdesc").value
        		app.setStoryShare(sNwork, stitle, sdesc);
        	} catch(e) {
        		alert(e);
        	}
        }

        function setNewStoryID(sNwork) {
        	try {
        		app.setNewStoryID();
        	} catch(e) {
        		// alert(e);
        	}
        }

        function hideSSharePop() {
        	try {
        		showHideElement('tdFormControls', 'show');
        		showHideElement('epSharePop', 'hide');
        	} catch(e) {
        		alert(e);
        	}
        }

        function setSSharePop() {
        	try {
        		showHideElement('epSharePop', 'show');
        		showHideElement('tdFormControls', 'hide');
        		centerPopWin('epSharePop');
        	} catch(e) {
        		alert(e);
        	}
        }
        /**
         * Code below taken from - http://www.evolt.org/article/document_body_doctype_switching_and_more/17/30655/
         *
         * Modified 4/22/04 to work with Opera/Moz (by webmaster at subimage dot com)
         *
         * Gets the full width/height because it's different for most browsers.
         */
        function getViewportHeight() {
        	if(window.innerHeight != window.undefined) return window.innerHeight;
        	if(document.compatMode == 'CSS1Compat') return document.documentElement.clientHeight;
        	if(document.body) return document.body.clientHeight;
        	return window.undefined;
        }

        function getViewportWidth() {
        	var offset = 17;
        	var width = null;
        	if(window.innerWidth != window.undefined) return window.innerWidth;
        	if(document.compatMode == 'CSS1Compat') return document.documentElement.clientWidth;
        	if(document.body) return document.body.clientWidth;
        }
        /**
         * Gets the real scroll top
         */
        function getScrollTop() {
        	if(self.pageYOffset) // all except Explorer
        	{
        		return self.pageYOffset;
        	} else if(document.documentElement && document.documentElement.scrollTop)
        	// Explorer 6 Strict
        	{
        		return document.documentElement.scrollTop;
        	} else if(document.body) // all other Explorers
        	{
        		return document.body.scrollTop;
        	}
        }

        function getScrollLeft() {
        	if(self.pageXOffset) // all except Explorer
        	{
        		return self.pageXOffset;
        	} else if(document.documentElement && document.documentElement.scrollLeft)
        	// Explorer 6 Strict
        	{
        		return document.documentElement.scrollLeft;
        	} else if(document.body) // all other Explorers
        	{
        		return document.body.scrollLeft;
        	}
        }

        function centerPopWin(theObj) {
        	thePopUpObj = document.getElementById(theObj);
        	width = thePopUpObj.offsetWidth;
        	height = thePopUpObj.offsetHeight;
        	var theBody = document.getElementsByTagName("BODY")[0];
        	var scTop = parseInt(getScrollTop(), 10);
        	var scLeft = parseInt(theBody.scrollLeft, 10);
        	var fullHeight = getViewportHeight();
        	var fullWidth = getViewportWidth();
        	thePopUpObj.style.top = (scTop + ((fullHeight - height) / 2)) + "px";
        	thePopUpObj.style.left = (scLeft + ((fullWidth - width) / 2)) + "px";
        }

        function getUnixTimeStamp() {
        	ts = Math.round(new Date().getTime() / 1000);
        	return ts;
        }

        function doPagePopUp(theUrl, theHTML) {
        	app.doPagePopUp(theUrl, theHTML);
        }


        var getSPdbitems = function(isDistinct, strTable, strColumns, qstring, qargs, strGrpBy, strHaving, strOrderBy, strQlimit) {
        	try {
        		arrToRet = new Array();
        		arrToMidFill = new Array();
        		arrToMidEat = new Array();
        		arrToMidExplode = new Array();
        		arrToFill = new Array();
        		arrToStr = "";
        		strMA = app.getJSEpDBrecords(isDistinct, strTable, strColumns, qstring, qargs, strGrpBy, strHaving, strOrderBy, strQlimit);
        		document.getElementById("fldChallArray").value = strMA;
        		strFA = document.getElementById("fldChallArray").value;
        		if(strFA.indexOf("::") != -1) {
        			arrToMidFill = strFA.split("::");
        			for(var i = 0; i < arrToMidFill.length; i++) {
        				if(arrToMidFill[i].indexOf("||") != -1) {
        					arrToMidExplode = arrToMidFill[i].split("||");
        					arrToRet[i] = arrToMidExplode;
        				}
        				arrToMidExplode = null;
        			}
        		} else {
        			if(strFA.indexOf("||") != -1) {
        				arrToMidExplode = strFA.split("||");
        				arrToRet[0] = arrToMidExplode;
        			} else {
        				arrToRet[0] = "noQvalue";
        			}
        		}
        		// do something with arrToRet
        		document.getElementById("fldChallArray").value = arrToStr;
        		return arrToRet;
        	} catch(e) {
        		alert("function.getFormActivities.error: " + e);
        		return arrToRet;
        	}
        };
        var doPAct = function(theIg, theID, rmode) {
        	theStrDbug = "";
        	theSrcStr = theIg.src;
        	theIDFFld = "prd" + rmode + theID;
        	theFFld = document.getElementById(theIDFFld);
        	theValFFld = Math.round(theFFld.value);
        	if(theSrcStr.indexOf("favorited_g") != -1) {
        		theIg.src = "images/favorited_r.gif";
        		if(theValFFld == 0) {
        			theFFld.value = "1";
        			theStrDbug = "favorited - no cart";
        		} else {
        			theFFld.value = "2";
        			theStrDbug = "favorited - and cart";
        		}
        		// add to favorites
        	} else if(theSrcStr.indexOf("favorited_r") != -1) {
        		theIg.src = "images/favorited_g.gif";
        		if(theValFFld == 2) {
        			theFFld.value = "3";
        			theStrDbug = "unfavorited - and cart";
        		} else {
        			theFFld.value = "0";
        			theStrDbug = "unfavorited - no cart";
        		}
        	} else if(theSrcStr.indexOf("cart_g") != -1) {
        		theIg.src = "images/cart_r.gif";
        		if(theValFFld == 0) {
        			theFFld.value = "3";
        			theStrDbug = "favorited - and cart";
        		} else {
        			theFFld.value = "2";
        			theStrDbug = "unfavorited - and cart";
        		}
        	} else {
        		theIg.src = "images/cart_g.gif";
        		if(theValFFld == 2) {
        			theFFld.value = "1";
        			theStrDbug = "favorited - no cart";
        		} else {
        			theFFld.value = "0";
        			theStrDbug = "unfavorited - no cart";
        		}
        	}
        	retStr = app.doJSIRecUpdate(theID, "rec_date_modified=;" + theFFld.value);
        	/*
        	if(theSrcStr.indexOf("cart_g") != -1) {
        	app.setPageThread(50,theID,"product.html");
        	}
        	*/
        	if(theSrcStr.indexOf("delete_r.gif") != -1) {
        		app.getALocalUrl("cart_list.html");
        	}
        	// alert(retStr + " :: " + theStrDbug + " :: " + theFFld.value);
        };
        var doJISRecUpdate = function(theID, theQ) {
        	retStr = app.doJSIRecUpdate(theID, theQ);
        	return retStr;
        };
        var getProdPopPage = function(rtype, rid, rurl) {
        	try {
        		app.setPageThread(50, rid, rurl);
        	} catch(e) {
        		JSSHOP.logJSerror(e, arguments, "JSSHOP.getProdPopPage");
        	}
        };
        var getProdPopPstr = function(rtype, rid, rurl, rtitle) {
        	strTret = "<div id=\"dvidprd\"" + rid + "\" onclick=\"JSSHOP.ui.setCBBClickClr(this,'brdrClrRed','brdrNone', function(){getProdPopPage(" + rtype + "," + rid + ",'" + rurl + "');});\" class=\"brdrNone\">";
        	strTret += rtitle + "</div>";
        	return strTret;
        };
        var doProdBtnAction = function(tpid, srda) {
        	switch(srda) {
        		case 1: // favorited no cart
        			retStr = app.doJSIRecUpdate(tpid, "rec_date_modified=;2");
        			break;
        		case 2: // favorited and cart
        		case 3: // not favorited and cart
        			break;
        		default:
        			retStr = app.doJSIRecUpdate(tpid, "rec_date_modified=;3");
        	} // end switch srda
        	sendPQudate(tpid);
        };
        var doPQudate = function(tpid, tsargs) {
        	try {
        		arrPQ = new Array();
        		oldArrID = "prdinf50" + tpid;
        		oldArrstr = document.getElementById(oldArrID).value;
        		strTret = oldArrstr;
        		if(oldArrstr.indexOf("-") != -1) {
        			arrPQ = oldArrstr.split("-");
        			strTret = arrPQ[0] + "-" + arrPQ[1] + "-" + tsargs + "-" + arrPQ[3];
        			strDD = (arrPQ[1] * tsargs).toFixed(2);
        			setTinnerHTML("tdPP", "€ " + strDD);
        		}
        	} catch(e) {
        		alert(e);
        	}
        };
        var sendPQudate = function(tpid) {
        	arrPQ = new Array();
        	newStrTret = "noQvalue";
        	oldArrID = "prdinf50" + tpid;
        	oldArrstr = document.getElementById(oldArrID).value;
        	if(oldArrstr.indexOf("-") != -1) {
        		arrPQ = oldArrstr.split("-");
        		tsarg = getCurrListVal(document.getElementById("selqty"));
        		newStrTret = arrPQ[0] + "-" + arrPQ[1] + "-" + tsarg + "-" + arrPQ[3];
        	}
        	newAstr = tpid + ":" + newStrTret;
        	app_dlg.getEpMDcom('50', newAstr);
        };
        var getGoeyBtn = function(daImg, strGRecID, rGmode) {
        	strImgGHtml = "<img src=\"images/" + daImg + ".gif\" class=\"icnmnubtn brdrClrWhite\" onclick=\"doPAct(this," + strGRecID + "," + rGmode + ");\">";
        	return strImgGHtml;
        };
        var getGoeyBtnWcls = function(daImg, strGRecID, rGmode, daClsName) {
        	strImgGHtml = "<img class=\"" + daClsName + "\" src=\"images/" + daImg + ".gif\" onclick=\"doPAct(this," + strGRecID + "," + rGmode + ");\">";
        	return strImgGHtml;
        };
        var getTblSortStr = function(theTBhdr, theTBbdy) {
        	strFhtml = "<table id=\"tbleStbl\" class=\"sortable autostripe\" style=\"margin: 0 auto;\" align=\"center\" onload=\"javascript:standardistaTableSortingInit();\">";
        	strFhtml += "<THEAD><tr>" + theTBhdr + "</tr></THEAD><TBODY>";
        	strFhtml += theTBbdy + "</TBODY></table>";
        	return strFhtml;
        };
        var getNPrice = function(thID, theP) {
        	strFhtml = "<div class=\"txtBold txtClrRed\">+ " + theP + "<br>des<br>conto!</div>";
        	setTinnerHTML("tdPP" + thID, strFhtml);
        };
        /*

        					case 1: // favorited no cart
        					imgProdStat = "favorited_r";
        					imgCartStat = "cart_g";
        					break;
        					case 2: // favorited and cart
        					imgProdStat = "favorited_r";
        					imgCartStat = "cart_r";
        					break;
        					case 3: // not favorited and cart
        					imgProdStat = "favorited_g";
        					imgCartStat = "cart_r";



    public int INT_KEY_REC_ID = 0;
    public int INT_KEY_REC_TYPE = 1;
    public int INT_KEY_REC_VAL_A = 2;
    public int INT_KEY_REC_VAL_B = 3;
    public int INT_KEY_REC_VAL_C = 4;
    public int INT_KEY_DATE_ADDED = 5;
    public int INT_KEY_DATE_MODIFIED = 6;




        rectypes
        5 folheto ; id ; title; 
        10 receita ; id ; title ;
        11 entrega group : id : datemodified
        50 product | promo id | title | prici;ng | favorite
        60 entrega prod | entrega group | product id
        80 orders
        rmodes
        5 show folheto
        6 folhetos lists
        7 favorited list
        8 toship list



        */
        var doSPdbitems = function(arrToParse, rmode) {
        	// alert("rmode: " + rmode);
        	strCurrLurl = document.location.href;
        	strCBHtml = "";
        	strLkHtml = "";
        	strBtnTitle = "The Title";
        	strTFhtml = "";
        	strRetstr = "";
        	arrTmp = new Array();
        	arrProdStat = new Array();
        	strRecID = "";
        	strRecType = "";
        	strRecValA = "";
        	strRecValB = "";
        	strRecValC = "";
        	strRecDateAdded = "";
        	strRecDateModified = "";
        	strIttl = "";
        	strIurl = "";
        	strIicon = "noQvalue";
        	strIiconU = "";
        	strIRicon = "noQvalue";
        	strIRiconU = "";
        	strImsg = "";
        	strEmsg = "";
        	strRLFpref = "";
        	strRIFpref = "";
        	strRILpref = "";
        	theRetStr = "";
        	clsList = "";
        	useHR = "yes";
        	nd = "hu";
        	strHtml = "";
        	strAHtml = "";
        	cartTtl = 0;
        	imgProdStat = "favorited_g";
        	imgCartStat = "cart_g";
        	strPriceHtml = "";
        	strImgDsct = "";
        	strMjay = "";
        	currcartstr = "";
        	try {
        		for(i = 0; i < arrToParse.length; i++) {
        			for(ir = 0; ir < arrToParse[i].length; ir++) {
        				strRecID = Math.round(arrToParse[i][0]);
        				strRecType = Math.round(arrToParse[i][1]);
        				strRecValA = arrToParse[i][2];
        				strRecValB = arrToParse[i][3];
        				strRecValC = arrToParse[i][4];
        				strRecDateAdded = arrToParse[i][5];
        				strRecDateModified = arrToParse[i][6];
        			}
        			switch(rmode) { // array logic
        				case 5:
        				case 10:
        				case 11:
        				case 50:
        				case 500:
        					iProdStatus = Math.round(strRecDateModified);
        					if(isNaN(iProdStatus)) {
        						// something wrong
        					} else {
        						switch(iProdStatus) {
        							case 1: // favorited no cart
        								imgProdStat = "favorited_r";
        								imgCartStat = "cart_g";
        								break;
        							case 2: // favorited and cart
        								imgProdStat = "favorited_r";
        								imgCartStat = "cart_r";
        								break;
        							case 3: // not favorited and cart
        								imgProdStat = "favorited_g";
        								imgCartStat = "cart_r";
        								break;
        							default:
        								imgProdStat = "favorited_g";
        								imgCartStat = "cart_g";
        						} // end switch iProdStatus
        					} // end of else NaN
        					if(strRecValC.indexOf("-") != -1) {
        						arrTmp = strRecValC.split("-");
        						na = arrTmp[0] - arrTmp[1];
        						nd = Math.round((na / arrTmp[0]) * 100);
        						strPriceHtml = "";
        						// nd =  Math.round((arrTmp[1] / arrTmp[0]) * 100);
        						strImgDsct = "<img src=\"images/discount" + nd + ".png\" class=\"icnmedbtn\" onerror=\"this.src='images/trans.gif';this.width='2px';getNPrice('" + strRecID + "','" + nd + "');\">";
        						strPriceHtml = "<span class=\"txtstriked txtBig txtClrGrey\">" + arrTmp[0] + "</span> <span class=\"txtBig txtClrRed txtBold\">" + arrTmp[1] + "</span>";
        						cartTtl = cartTtl + (arrTmp[1] * arrTmp[2]);
        					} else {
        						strPriceHtml += (Math.round(strRecValC) * 1);
        						cartTtl = cartTtl + (Math.round(strRecValC) * 1);
        					}
        					currcartstr += strRecID + "-" + strRecValA + "-" + arrTmp[3] + "-" + arrTmp[2] + ";";
        					break;
        			} // switch
        			switch(rmode) { // table header and bottom
        				case 5:
        				case 10:
        				case 500:
        					strTHhtml = "<th>€</th><th>Produto</th><th>xx</th>";
        					strBHhtml = "<tr><td></td><td></td><td></td></tr>";
        					break;
        				case 11:
        					strTHhtml = "<th>X</th><th>Qtd</th><th>Produto</th><th>Valor</th>";
        					strBHhtml = "<tr><td></td><td></td><td>Total:</td><td>" + cartTtl.toFixed(2) + "</td></tr>";
        					break;
        				case 6:
        					strTHhtml = "<th>Folhetos</th>";
        					strBHhtml = "<tr><td></td></tr>";
        					break;
        				case 7:
        					strTHhtml = "<th>Encomendas</th><th></th>";
        					strBHhtml = "<tr><td></td><td></td></tr>";
        					break;
        				default:
        					strTHhtml = "<th>x</th>";
        					strBHhtml = "<tr><td></td></tr>";
        			} // switch
        			switch(rmode) { // body
        				case 5: // folheto	
        				case 10: // favorites		
        				case 500: // recipe products
        					strHtml += "<tr>";
        					strHtml += "<td id=\"tdPP" + strRecID + "\">";
        					strHtml += getProdPopPstr(strRecType, strRecID, "product.html", strImgDsct)
        						// strHtml += strPriceHtml;
        					strHtml += "</td>";
        					strHtml += "<td>";
        					strHtml += getProdPopPstr(strRecType, strRecID, "product.html", strRecValB + "    " + strPriceHtml);
        					strHtml += "<td>";
        					// strHtml += "<img src=\"images/" + imgProdStat + ".gif\" class=\"icnmnubtn brdrClrWhite\" onclick=\"doPAct(this," + strRecID + "," + rmode + ");\">";
        					// strHtml += "<br><img src=\"images/" + imgCartStat + ".gif\" class=\"icnmnubtn brdrClrWhite\" onclick=\"doPAct(this," + strRecID + "," + rmode + ");\">";
        					// strHtml +=  getGoeyBtnWcls(imgProdStat, strRecID, rmode, "icnsmlbtn brdrClrWhite");
        					// strHtml +=  getGoeyBtnWcls(imgCartStat, strRecID, rmode, "icnsmlbtn brdrClrWhite");
        					strHtml += getProdPopPstr(strRecType, strRecID, "product.html", "<img src=\"images/cart_r.gif\" class=\"icnsmlbtn brdrClrWhite\">");
        					strHtml += "<input type=\"hidden\" id=\"prd" + rmode + strRecID + "\" value=\"" + iProdStatus + "\">";
        					strHtml += "</td>";
        					strHtml += "</tr>";
        					break;
        				case 50: // product page
        					switch(iProdStatus) {
        						case 2: // favorited and cart
        						case 3: // not favorited and cart
        							btnCStat = "OK";
        							btnCNote = "<img src=\"images/cart_r.gif\" class=\"icnsmlbtn brdrClrWhite\" align=\"absmiddle\"> Editar este artigo.";
        							break;
        						default:
        							btnCNote = "<img src=\"images/cart_r.gif\" class=\"icnsmlbtn brdrClrWhite\" align=\"absmiddle\"> Adicionar a carrinha de compras.";
        							btnCStat = "Adicionar";
        					}
        					pretStr = "<table style=\"width: 100%\"><tr><td>";
        					pretStr += "<table style=\"width: 100%\"><tr><td><img src=\"images/prods_r.gif\" class=\" icnmnubtn\"></td>";
        					pretStr += "<td class\"txtClrHdr txtBold\">" + strRecValB + "</td>";
        					pretStr += "</tr></table>";
        					pretStr += "</td></tr><tr><td>";
        					pretStr += "<table align=\"right\"><tr>";
        					pretStr += "<td>" + getGoeyBtnWcls(imgProdStat, strRecID, rmode, 'icnsmlbtn') + "</td>";
        					pretStr += "<td>" + strImgDsct + "</td>";
        					pretStr += "<td>" + strPriceHtml + "</td>";
        					pretStr += "</tr></table>";
        					pretStr += "</td></tr><tr><td>";
        					pretStr += "<table style=\"width: 100%\"><tr>";
        					pretStr += "<td>";
        					// inner qty table
        					pretStr += "<table><tr>";
        					pretStr += "<td class=\"txtBold\">Quantidade: </td>";
        					pretStr += "<td><select class=\"bigtable brdrClrHdr txtBig txtBold\" name=\"selqty\" id=\"selqty\" onchange=\"doPQudate('" + strRecID + "',getCurrListVal(this));\">";
        					// pretStr += "<option value=\"0\">Escolha</option>";
        					for(si = 0; si < 10; si++) {
        						if(si == arrTmp[2]) {
        							pretStr += "<option value=\"" + si + "\" selected=\"\">" + si + "</option>";
        						} else {
        							pretStr += "<option value=\"" + si + "\">" + si + "</option>";
        						}
        					}
        					pretStr += "</select> ";
        					pretStr += "<input type=\"hidden\" name=\"prdq\"  id=\"prdq\" value=\"" + arrTmp[2] + "\">";
        					pretStr += "<input type=\"hidden\" id=\"prdinf" + rmode + strRecID + "\" value=\"" + strRecValC + "\">";
        					pretStr += "<input type=\"hidden\" id=\"prd" + rmode + strRecID + "\" value=\"" + iProdStatus + "\">";
        					pretStr += "</td></tr></table>";
        					// end inner qty table
        					pretStr += "</td><td>";
        					// total table
        					pretStr += "<table><tr>";
        					pretStr += "<td id=\"tdPP\" class=\"txtBold txtBig txtClrRed\">€ " + (arrTmp[1] * arrTmp[2]).toFixed(2) + "</td>";
        					//  pretStr += "<td>" + getGoeyBtn(imgProdStat, strRecID, rmode) + "</td>";
        					// pretStr += "<td>" + getGoeyBtn(imgProdStat, strRecID, rmode) + "</td>";
        					pretStr += "</tr></table>";
        					// end total table	
        					pretStr += "</td></tr></table>";
        					pretStr += "</td></tr><tr><td>";
        					pretStr += "</td></tr><tr><td>";
        					pretStr += "<table style=\"width: 100%\"><tr>";
        					pretStr += "<td class=\"txtClrHdr brdrClrRed txtBold txtBig\">" + btnCNote + "</td>";
        					pretStr += "<td></td>";
        					pretStr += "</tr></table>";
        					pretStr += "</td></tr><tr><td>";
        					pretStr += "<table style=\"width: 100%\"><tr>";
        					pretStr += "<td>";
        					pretStr += "<div class=\"bigtable bkgdClrRed txtClrWhite brdrClrDlg txtBold txtBig\"  onclick=\"JSSHOP.ui.setCBBClickClr(this,'bigtable bkgdClrWhite txtClrRed brdrClrDlg txtBold txtBig','bigtable bkgdClrRed txtClrWhite brdrClrDlg txtBold txtBig', function(){doProdBtnAction(" + strRecID + "," + strRecDateModified + ")});\" style=\"text-align:center\">";
        					pretStr += btnCStat;
        					pretStr += "</div>";
        					pretStr += "</td>";
        					pretStr += "<td>";
        					pretStr += "<div class=\"bigtable bkgdClrRed txtClrWhite brdrClrDlg txtBold txtBig\"  onclick=\"JSSHOP.ui.setCBBClickClr(this,'bigtable bkgdClrWhite txtClrRed brdrClrDlg txtBold txtBig','bigtable bkgdClrRed txtClrWhite brdrClrDlg txtBold txtBig', function(){app_dlg.getEpMDcom('300','noQvalue')});\" style=\"text-align:center\">";
        					pretStr += "Cancelar";
        					pretStr += "</div>";
        					pretStr += "</td>";
        					pretStr += "</tr></table>";
        					pretStr += "</td></tr></table>";
        					pretStr += "";
        					strHtml += pretStr;
        					break;
        				case 11: // cart products
        					strPriceHtml = "       <span class=\"txtstriked\">" + arrTmp[0] + "</span>    " + arrTmp[1];
        					strHtml += "<tr>";
        					strHtml += "<td>" + getGoeyBtnWcls("delete_r", strRecID, rmode, "icnsmlbtn brdrClrWhite");
        					strHtml += "</td><td>" + arrTmp[2] + "</td>";
        					strHtml += "<td>";
        					strHtml += getProdPopPstr(strRecType, strRecID, "product.html", strRecValB + strPriceHtml);
        					strHtml += "<td>";
        					// strHtml += "<img src=\"images/" + imgProdStat + ".gif\" class=\"icnmnubtn brdrClrWhite\" onclick=\"doPAct(this," + strRecID + "," + rmode + ");\">";
        					// strHtml += "<br><img src=\"images/" + imgCartStat + ".gif\" class=\"icnmnubtn brdrClrWhite\" onclick=\"doPAct(this," + strRecID + "," + rmode + ");\">";
        					strHtml += (arrTmp[1] * arrTmp[2]).toFixed(2);
        					strHtml += "<input type=\"hidden\" id=\"prdinf" + rmode + strRecID + "\" value=\"" + strRecValC + "\">";
        					strHtml += "<input type=\"hidden\" id=\"prd" + rmode + strRecID + "\" value=\"" + iProdStatus + "\">";
        					strHtml += "</td>";
        					strHtml += "</tr>";
        					break;
        				case 6: // folheto and recipe lists
        					strHtml += "<tr>";
        					// strHtml += "<td><img src=\"images/startapp/discount" + nd + ".png\"><br>" + nd + "<img src=\"images/startapp/discount-entrega_zero_m.jpg\" style=\"max-width: 50px\"></td>";
        					strHtml += "<td><div id=\"dF" + i + "\" onclick=\"JSSHOP.ui.setCBBClickClr(this,'brdrClrRed','brdrNone', function(){app.setPageThread(" + strRecType + "," + strRecID + ",'promo.html')});\" class=\"brdrNone\">";
        					strHtml += strRecValB + "</div></td>";
        					strHtml += "</tr>";
        					break;
        				case 7: // order lists
        					arrTmp = null;
        					strTcst = "0";
        					if(strRecValA.indexOf(":") != -1) {
        						arrTmp = strRecValA.split(":");
        						strTcst = arrTmp[0];
        					}
        					strHtml += "<tr>";
        					strHtml += "<td><img src=\"images/cart_r.gif\">" + strRecDateAdded + " </td>";
        					strHtml += "<td><div id=\"dF" + i + "\" onclick=\"JSSHOP.ui.setCBBClickClr(this,'rtable txtClrWhite bkgdClrNrml','rtable txtClrWhite bkgdClrHdr', function(){x='y'});\" class=\"rtable txtClrWhite bkgdClrHdr\">";
        					strHtml += strTcst + "</div></td>";
        					strHtml += "</tr>";
        					break;
        				default:
        					strHtml += "<tr><td></td></tr>";
        			} // switch
        		} // for each
        		switch(rmode) { // return string
        			case 5: // folheto	
        			case 6: // // folheto lists
        			case 10: // favorites	
        			case 11: // cart products	
        			case 7: // // folheto lists
        			case 500: // recipe products
        				fcurrcartstr = cartTtl + ":" + currcartstr.substring(0, currcartstr.length - 1);
        				npretStr = "<input type=\"hidden\" name=\"ccstr\"  id=\"ccstr\" value=\"" + fcurrcartstr + "\">";
        				npretStr += "<input type=\"hidden\" name=\"inpcttl\" id=\"inpcttl\" value=\"" + cartTtl + "\">";
        				strTFhtml = getTblSortStr(strTHhtml, strHtml + strBHhtml) + npretStr;
        				break;
        			case 50:
        				strTFhtml = strHtml;
        				break;
        			default:
        				strHtml = strHtml;
        		}
        		return strTFhtml;
        	} catch(e) {
        		alert("function.doSPdbitems.error: " + e);
        		//  return strFhtml;
        	}
        };
        var getPromoList = function(theType, thelimit, rmode) {
        	arrTmp = new Array();
        	arrTmp = getSPdbitems(false, null, "strColumns", "rec_type =?", theType, null, null, "_id DESC", thelimit);
        	arrToStr = doSPdbitems(arrTmp, rmode);
        	// setTinnerHTML("tdMidContent", rmode);
        	// standardistaTableSortingInit();
        	return arrToStr;
        };
        var doPromoList = function(theType, thelimit, rmode) {
        	hStr = "Folhetos";
        	if(theType == 500) {
        		hStr = "Receitas";
        	}
        	fullstr = "<span class=\"txtClrHdr\">" + hStr + "  : Clique sobre titulos para abrir pagina.</span>";
        	fullstr += getPromoList(theType, thelimit, rmode);
        	setTinnerHTML("tdMidContent", fullstr);
        	// setTinnerHTML("tdMidContent", arrToStr);
        	standardistaTableSortingInit();
        };
        var getFavsList = function(thelimit) {
        	arrTmp = new Array();
        	arrTmpA = new Array();
        	arrTmpB = new Array();
        	arrTmpA = getSPdbitems(false, null, "strColumns", "rec_date_modified=? and rec_type=?", "1:50", null, null, "_id DESC", thelimit);
        	arrTmpB = getSPdbitems(false, null, "strColumns", "rec_date_modified=? and rec_type=?", "2:50", null, null, "_id DESC", thelimit);
        	arrTmp = arrTmpA.concat(arrTmpB);
        	arrToStr = doSPdbitems(arrTmp, 10);
        	return arrToStr;
        };
        var doFavsList = function(thelimit) {
        	fullstr = "<div class=\"txtClrRed\">Clique sobre o titulo do produto para mais informações:</div>";
        	fullstr += getFavsList(thelimit);
        	setTinnerHTML("tdMidContent", fullstr);
        	// setTinnerHTML("tdMidContent", arrToStr);
        	standardistaTableSortingInit();
        };
        var getRProdsList = function(thelimit) {
        	arrTmp = new Array();
        	arrTmp = getSPdbitems(false, null, "strColumns", "rec_type=?", "50", null, null, "_id DESC", thelimit);
        	arrToStr = doSPdbitems(arrTmp, 10);
        	return arrToStr;
        };
        var doRProdsList = function(thelimit) {
        	fullstr = "<div class=\"txtClrRed\">Clique sobre o titulo do produto para mais informações:</div>";
        	fullstr += getRProdsList(thelimit);
        	setTinnerHTML("tdMidContent", fullstr);
        	// setTinnerHTML("tdMidContent", arrToStr);
        	standardistaTableSortingInit();
        };
        var getCartList = function(thelimit) {
        	arrTmp = new Array();
        	arrTmpA = new Array();
        	arrTmpB = new Array();
        	arrTmpA = getSPdbitems(false, null, "strColumns", "rec_date_modified=? and rec_type=?", "2:50", null, null, "_id DESC", thelimit);
        	arrTmpB = getSPdbitems(false, null, "strColumns", "rec_date_modified=? and rec_type=?", "3:50", null, null, "_id DESC", thelimit);
        	arrTmp = arrTmpA.concat(arrTmpB);
        	arrToStr = doSPdbitems(arrTmp, 11);
        	return arrToStr;
        };
        var doCartList = function(thelimit) {
        	fullstr = "<div class=\"txtClrRed\">Clique sobre o titulo do produto para editar:</div>";
        	fullstr += getCartList(thelimit);
        	setTinnerHTML("tdMidContent", fullstr);
        	standardistaTableSortingInit();
        };
        var getXPageThread = function(theID) {
        	alert(theID);
        };
        var getHdrToString = function(harr) {
        	try {
        		retHstr = "<div class=\"txtClrHdr\">";
        		strHRecID = Math.round(harr[0][0]);
        		strHRecType = Math.round(harr[0][1]);
        		strHRecValA = harr[0][2];
        		strHRecValB = harr[0][3];
        		strHRecValC = harr[0][4];
        		strHRecDateAdded = harr[0][5];
        		strHRecDateModified = harr[0][6];
        		switch(strHRecType) {
        			case 5: // promo list
        				retHstr += "<img src=\"images/folhetos_r.gif\" class=\"icnsmlbtn\">Folheto  " + strHRecValB + "<br>" + strHRecValC + "<br><br>Produtos:";
        				break;
        			case 50: // product page
        				retHstr += "";
        				break;
        			case 500: // recipe
        				retHstr += "<img src=\"images/receitas_r.gif\" class=\"icnsmlbtn\">  Receita<br><b>" + strHRecValB + "</b><br><br><b>Ingredientes:</b><br>" + strHRecValC + "<br><br><b>Preparação:</b><br>" + strHRecDateModified + "<br>Sugestões:";
        				break;
        			default:
        				retHstr += strHRecID + "<br>" + strHRecType + "<br>" + strHRecValA + "<br>" + strHRecValB + "<br>" + strHRecValC + "<br>" + strHRecDateModified;
        		}
        		return retHstr + "</div>";
        	} catch(e) {
        		alert("function.getHdrToString.error: " + e);
        		return "noQvalue";
        	}
        }
        var getPageThread = function(theType, theID) {
        	strHtml = "";
        	hdrToString = "";
        	arrToStr = "noQvalue";
        	strQ = "rec_type=? and rec_val_a =?";
        	strQargs = "50:" + theID;
        	arrTmp = new Array();
        	arrToParse = new Array();
        	// strRecType = 5;
        	arrToParse = getSPdbitems(false, null, "strColumns", "_id=?", theID, null, null, "_id DESC", "1");
        	for(i = 0; i < arrToParse.length; i++) {
        		for(ir = 0; ir < arrToParse[i].length; ir++) {
        			strRecID = Math.round(arrToParse[i][0]);
        			strRecType = Math.round(arrToParse[i][1]);
        			strRecValA = arrToParse[i][2];
        			strRecValB = arrToParse[i][3];
        			strRecValC = arrToParse[i][4];
        			strRecDateAdded = arrToParse[i][5];
        			strRecDateModified = arrToParse[i][6];
        		}
        	}
        	try {
        		switch(strRecType) {
        			case 5:
        				strQ = "rec_type=? and rec_val_a =?";
        				strQargs = "50:" + Math.round(theID);
        				break;
        			case 50:
        				strQ = "_id=?";
        				strQargs = Math.round(theID);
        				break;
        			case 500:
        				strQ = "rec_type=? and rec_val_a =?";
        				strQargs = "50:" + Math.round(theID);
        				break;
        			default:
        				strQ = "rec_type=? and rec_val_a =?";
        				strQargs = "50:" + Math.round(theID);
        		}
        		arrTmp = getSPdbitems(false, null, "strColumns", strQ, strQargs, null, null, "_id DESC", "300");
        		if(arrTmp[0] == "noQvalue") {
        			arrToStr = "no prods found";
        			//  arrToStr = getHelpString();
        		} else {
        			arrToStr = getHdrToString(arrToParse);
        			arrToStr += doSPdbitems(arrTmp, Math.round(theType));
        		}
        		setTinnerHTML("tdMidContent", arrToStr);
        		standardistaTableSortingInit();
        	} catch(e) {
        		alert("function.getChatThread.error: " + e);
        	}
        	// return arrToStr;
        };
        var doDLgCUp = function() {
        	xx = "ha";
        	// setSceneProps("15");
        };
        var getChatThread = function() {
        	strHtml = "";
        	arrToStr = "noQvalue";
        	try {
        		arrTmp = new Array();
        		arrTmp = getSPdbitems(false, null, "strColumns", "rec_type=?", "5", null, null, "_id DESC", "250");
        		if(arrTmp[0] == "noQvalue") {
        			//  arrToStr = getHelpString();
        		} else {
        			arrToStr = doSPdbitems(arrTmp, "noQvalue");
        		}
        		setTinnerHTML("tdMidContent", arrToStr);
        		standardistaTableSortingInit();
        	} catch(e) {
        		alert("function.getChatThread.error: " + e);
        	}
        	// return arrToStr;
        };
        var doSvcMsgComm = function(aID, bID) {
        	try {
        		tstrrr = aID + " :: " + bID;
        		if(bID.indexOf("sent") != -1) {
        			ds = new Date();
        			ott = "Encomenda " + ds.toDateString();
        			strct = document.getElementById("inpcttl").value;
        			strot = document.getElementById("inpotype").value;
        			app.getOrderClean(aID, ott, strct, strot)
        			setTinnerHTML("tdMidContent", "<b>Enviada! ... " + tstrrr + "</b>");
        		}
        		setTinnerHTML("tdMidContent", tstrrr);
        	} catch(e) {
        		alert(e);
        	}
        };