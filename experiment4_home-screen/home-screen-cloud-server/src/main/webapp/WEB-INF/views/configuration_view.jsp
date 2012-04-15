<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Register Configuration</title>
<style type="text/css">
<!--
body {
	font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
	background: #ffffff;
	margin: 0;
	padding: 0;
	color: #333;
	font-size: 12px;
}

/* ~~ Element/tag selectors ~~ */
ul, ol, dl { /* Due to variations between browsers, it's best practices to zero padding and margin on lists. For consistency, you can either specify the amounts you want here, or on the list items (LI, DT, DD) they contain. Remember that what you do here will cascade to the .nav list unless you write a more specific selector. */
	padding: 0;
	margin: 0;
}
h1, h2, h3, h4, h5, h6, p {
	margin-top: 0;	 /* removing the top margin gets around an issue where margins can escape from their containing div. The remaining bottom margin will hold it away from any elements that follow. */
	padding-right: 0px;
	padding-left: 8px; /* adding the padding to the sides of the elements within the divs, instead of the divs themselves, gets rid of any box model math. A nested div with side padding can also be used as an alternate method. */
	padding-top: 10px;
}
a img { /* this selector removes the default blue border displayed in some browsers around an image when it is surrounded by a link */
	border: none;
}
/* ~~ Styling for your site's links must remain in this order - including the group of selectors that create the hover effect. ~~ */
a:link {
	color: #06F;
	text-decoration: underline; /* unless you style your links to look extremely unique, it's best to provide underlines for quick visual identification */
}
a:visited {
	color: #039;
	text-decoration: underline;
}
a:hover, a:active, a:focus { /* this group of selectors will give a keyboard navigator the same hover experience as the person using a mouse. */
	text-decoration: none;
}

/* ~~ this fixed width container surrounds all other elements ~~ */
.container {
	width: 960px;
	background: #FFF;
	margin: 0 auto; /* the auto value on the sides, coupled with the width, centers the layout */
}

/* ~~ This is the layout information. ~~

1) Padding is only placed on the top and/or bottom of the div. The elements within this div have padding on their sides. This saves you from any "box model math". Keep in mind, if you add any side padding or border to the div itself, it will be added to the width you define to create the *total* width. You may also choose to remove the padding on the element in the div and place a second div within it with no width and the padding necessary for your design.

*/
.content {
	padding: 30px 0;
}

/* ~~ miscellaneous float/clear classes ~~ */
.fltrt {  /* this class can be used to float an element right in your page. The floated element must precede the element it should be next to on the page. */
	float: right;
	margin-left: 8px;
}
.fltlft { /* this class can be used to float an element left in your page. The floated element must precede the element it should be next to on the page. */
	float: left;
	margin-right: 8px;
}
.clearfloat { /* this class can be placed on a <br /> or empty div as the final element following the last floated div (within the #container) if the overflow:hidden on the .container is removed */
	clear:both;
	height:0;
	font-size: 1px;
	line-height: 0px;
}
body,td,th {
	font-family: Arial, Helvetica, sans-serif;
	padding-left: 8px;
}
h1 {
	font-size: 36px;
	color: #2EB7E7;
}
h2 {
	font-size: 16px;
}
h3 {
	font-size: 12px;
}
h4 {
	font-size: 12px;
	color: #2EB7E7;
}
h1,h2,h3,h4,h5,h6 {
	font-weight: bold;
}
h5 {
	font-size: 12px;
}
-->
</style></head>

<body>

<div class="container">
  <div class="content">
    <blockquote>
      <blockquote>
        <blockquote>
          <blockquote>
            <table width="522" height="796" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td colspan="3"><h1>REGISTER CONFIGURATION</h1></td>
              </tr>
              <tr>
                <td colspan="2">Logged in as: <strong>${actionBean.user}</strong></td>
                <td><a href="${actionBean.logoutURL}"><img src="/images/Logout.png" /></a></td>
              </tr>
              <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
              </tr>
              <tr>
                <td height="35" colspan="2"><h2>Application name<br /></h2></td>
                <td height="35"><h2>Enabled</h2></td>
              </tr>

			<stripes:form beanclass="ac.uk.brunel.cloudhomescreen.presentation.beans.ConfigurationActionBean" focus="">

              <tr>
                <td height="35" colspan="3"><h3>Installed applications</h3></td>
              </tr>

				<c:forEach items="${actionBean.applicationSources}" var="application">
              		<tr>
                		<td width="115"><img src="/images/${application.source}.png" width="86" height="86" /></td>
                		<td width="240"><h5>${application.source}</h5></td>
                		<td><stripes:checkbox name="applications" value="${application.source}" /></td>
              		</tr>
				</c:forEach>

                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td><stripes:submit name="pushConfiguration" value="Save configuration" /></td>
                    <td>&nbsp;</td>
                </tr>
			</stripes:form>

                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

              <tr>
                <td>&nbsp;</td>
                <td height="74"><img src="http://code.google.com/appengine/images/appengine-silver-120x30.gif" alt="Powered by Google App Engine" width="120" height="30" align="left" /></td>
                <td>&nbsp;</td>
              </tr>



            </table>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
          </blockquote>
        </blockquote>
      </blockquote>
    </blockquote>
  <!-- end .content --></div>
<!-- end .container --></div>
</body>
</html>
