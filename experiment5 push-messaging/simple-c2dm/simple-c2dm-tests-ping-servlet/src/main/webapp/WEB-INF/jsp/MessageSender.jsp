<!DOCTYPE HTML>
<html>
<head>
    <title>Simple-C2DM message sender</title>
</head>
<body>
<h1>Message sender</h1>
<table border="0">
    <form action="/" method="GET">
        <tr>
            <td><label for="regId">Registration id:</label></td>
            <td><input type="text" id="regId" name="regId" value="${param.regId}"/></td>
        </tr>
        <tr>
            <td><label for="email">E-mail:</label></td>
            <td><input type="text" id="email" name="email" value="${param.email}"/></td>
        </tr>
        <tr>
            <td><label for="message">Message:</label></td>
            <td><input type="text" id="message" name="message" value="${param.message}"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Send message"/></td>
        </tr>
    </form>
</table>
</body>
</html>