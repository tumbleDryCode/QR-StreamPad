// Various Scripts pertaining to the pages.
var onlyNums = new RegExp(/^\d{10}$/); // allow only numbers 
var timeout_handles = [];
var loaded_scripts = [];
var loaded_streams = [];
var arrUprefs = [];
var trgr_bclck = [];


if(!window.JSSHOP) {
var JSSHOP = new Object();
}


JSSHOP.logJSerror = function(theError, theArgs, theMsg) {
if(theArgs.length) {
fullArgs = JSON.stringify(theArgs);
} else {
fullArgs = "noQvalue";
}
// fullSError = theError.toString();
fullSError = JSON.stringify(theError);
if(theError.lineNumber) {
fullSError += "Line: " + theError.lineNumber + "\r\n";
}
if(theError.fileName) {
fullSError += "File: " + theError.fileName + "\r\n";
}
if(theError.msg) {
fullSError += "msg: " + theError.msg  + "\r\n";
}
if(theError.constructor) {
fullSError += theError.constructor;
}
if(JSSHOP.cookies.getCookie("dbug")) {}
alert("epconsole.error: " + fullSError + " :: " + fullArgs + " :: " +  theMsg);
};
 

JSSHOP.startIntervalEvent = function(theObjTag, theFunction, theInterval) {
try {
timeout_handles[theObjTag] = window.setInterval(theFunction,theInterval); 
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.startIntervalEvent");
}
};

JSSHOP.stopIntervalEvent = function(theObjTag) {
try {
window.clearInterval(timeout_handles[theObjTag]);
timeout_handles[theObjTag] = null;
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.stopIntervalEvent");
}
};



JSSHOP.getUnixTimeStamp = function() {
try {
ts = Math.round(new Date().getTime() / 1000);
return ts;
} catch(e) {
JSSHOP.logJSerror(e, arguments, "JSSHOP.getUnixTimeStamp");
}
};
 


/* cookies
*/

if (!window.JSSHOP.cookies) {
    JSSHOP.cookies = new Object();
}

JSSHOP.cookies.getCookie = function(check_name) {
	// first we'll split this cookie up into name/value pairs
	// note: document.cookie only returns name=value, not the other components
	var a_all_cookies = document.cookie.split( ';' );
	var a_temp_cookie = '';
	var cookie_name = '';
	var cookie_value = '';
	var b_cookie_found = false; // set boolean t/f default f
	for ( i = 0; i < a_all_cookies.length; i++ )
	{
		// now we'll split apart each name=value pair
		a_temp_cookie = a_all_cookies[i].split( '=' );
		// and trim left/right whitespace while we're at it
		cookie_name = a_temp_cookie[0].replace(/^\s+|\s+$/g, '');
	
		// if the extracted name matches passed check_name
		if ( cookie_name == check_name )
		{
			b_cookie_found = true;
			// we need to handle case where cookie has no value but exists (no = sign, that is):
			if ( a_temp_cookie.length > 1 )
			{
				cookie_value = unescape( a_temp_cookie[1].replace(/^\s+|\s+$/g, '') );
			}
			// note that in cases where cookie is initialized but no value, null is returned
			return cookie_value;
			break;
		}
		a_temp_cookie = null;
		cookie_name = '';
	}
	if ( !b_cookie_found )
	{
		return null;
	}
};


JSSHOP.cookies.setCookie = function(name,value,expires,path,domain,secure) 
{
// set time, it's in milliseconds
var today = new Date();
today.setTime( today.getTime() );
/*
if the expires variable is set, make the correct 
expires time, the current script below will set 
it for x number of days, to make it for hours, 
delete * 24, for minutes, delete * 60 * 24
*/
if ( expires )
{
expires = expires * 1000 * 60 * 60 * 24;
}
var expires_date = new Date( today.getTime() + (expires) );
document.cookie = name + "=" +escape( value ) +
( ( expires ) ? ";expires=" + expires_date.toGMTString() : "" ) + 
( ( path ) ? ";path=" + path : "" ) + 
( ( domain ) ? ";domain=" + domain : "" ) +
( ( secure ) ? ";secure" : "" );
};

JSSHOP.cookies.deleteCookie = function(name,path,domain) {
if(JSSHOP.cookies.getCookie(name)) document.cookie = name + "=" + ( ( path ) ? ";path=" + path : "") + ( ( domain ) ? ";domain=" + domain : "" ) + ";expires=Thu, 01-Jan-1970 00:00:01 GMT";
};

 

 

