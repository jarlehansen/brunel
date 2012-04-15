<!DOCTYPE HTML>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.Properties" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="style/style.css"/>
    <title>simple-c2dm manifest generator</title>
</head>
<body>
    <h1>simple-c2dm manifest generator</h1>
    <p>Enter the package name in your own system and generate AndroidManifest.xml and C2DMReceiver.java<br/>
    Copy this code into your own project when using the simple-c2dm library.</p>
    <ol>
        <li>Enter the package name of your application and press 'Generate'</li>
        <li>Copy the generated xml into your own AndroidManifest.xml file:
            <ul>
                <li>The permissions must be added inside the <strong>&lt;manifest&gt;</strong> tag</li>
                <li>The receiver must be added inside the <strong>&lt;application&gt;</strong> tag</li>
            </ul>
        </li>
        <li>Create a new class called C2DMReceiver.java and add the content generated</li>
    </ol>
    <br/>
    <form action="/" method="GET">
        <label for="packageName">Package name:</label>
        <input id="packageName" class="text-input" type="text" name="packageName" autofocus="autofocus" value="<c:out value="${param.packageName}" />" />
        <input type="submit" value="Generate"/>

        <div id="error"><p><c:out value="${param.errormsg}" /></p></div>
    </form>
    <br/>
    <p class="small">AndroidManifest.xml, permissions (inside manifest-tag)</p>
    <textarea id="xmlpermissions" readonly rows="9" cols="85"><c:out value="${param.xmlpermissions}" /></textarea>
    <div id="xmlpermissions_clip_button" class="clip_button">Copy to clipboard</div>
    <br/>
    <br/>
    <p class="small">AndroidManifest.xml, receiver (inside application-tag)</p>
    <textarea id="xmlreceiver" readonly rows="14" cols="85"><c:out value="${param.xmlreceiver}" /></textarea>
    <div id="xmlreceiver_clip_button" class="clip_button">Copy to clipboard</div>
    <br/>
    <br/>
    <p class="small">C2DMReceiver.java</p>
    <textarea id="class" readonly rows="7" cols="85"><c:out value="${param.class}" /></textarea>
    <div id="class_clip_button" class="clip_button">Copy to clipboard</div>
    <br/>
    <br/>
    <br/>
    <p class="footer">Built: <c:out value="${timestamp}" /></p>

    
    <script type="text/javascript" src="js/ZeroClipboard.js"></script>

    <script language="JavaScript">
        ZeroClipboard.setMoviePath('js/ZeroClipboard10.swf');

        var clipXmlPermissions = new ZeroClipboard.Client();
        clipXmlPermissions.setText('');
        clipXmlPermissions.glue('xmlpermissions_clip_button');
        clipXmlPermissions.addEventListener('mouseDown', function(client) {
            clipXmlPermissions.setText(document.getElementById('xmlpermissions').value);
        });

        var clipXmlReceiver = new ZeroClipboard.Client();
        clipXmlReceiver.setText('');
        clipXmlReceiver.glue('xmlreceiver_clip_button');
        clipXmlReceiver.addEventListener('mouseDown', function(client) {
            clipXmlReceiver.setText(document.getElementById('xmlreceiver').value);
        });

        var clipClass = new ZeroClipboard.Client();
        clipClass.setText('');
        clipClass.glue('class_clip_button');
        clipClass.addEventListener('mouseDown', function(client) {
            clipClass.setText(document.getElementById('class').value);
        });
    </script>
</body>
</html> 