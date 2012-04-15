<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html>
    <head>
        <title>Delete Configuration</title>
    </head>
    <body>
        <h1>Delete Configuration</h1>
        <p>Logged in as: <strong>${actionBean.user}</strong><br/>
        <a href="${actionBean.logoutURL}">Log out</a>
        </p>
            <stripes:form beanclass="ac.uk.brunel.cloudhomescreen.presentation.beans.DeleteConfigurationActionBean" focus="">
                <stripes:submit name="deleteAll" value="Delete configuration" />
            </stripes:form>
        <br/>
        <img src="http://code.google.com/appengine/images/appengine-silver-120x30.gif" alt="Powered by Google App Engine" />
    </body>
</html>