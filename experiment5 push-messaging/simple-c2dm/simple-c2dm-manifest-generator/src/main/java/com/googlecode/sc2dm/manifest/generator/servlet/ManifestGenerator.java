package com.googlecode.sc2dm.manifest.generator.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.googlecode.sc2dm.manifest.generator.service.GeneratedXml;
import com.googlecode.sc2dm.manifest.generator.service.ManifestGeneratorService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 4:24 PM - 9/24/11
 */
@Singleton
public class ManifestGenerator extends HttpServlet {
    private static final GeneratedXml EMPTY_XML = new GeneratedXml("", "", "");

    private final ManifestGeneratorService manifestGeneratorService;
    private final String buildTimestamp;

    @Inject
    public ManifestGenerator(ManifestGeneratorService manifestGeneratorService,
                             @Named("BuildTimestamp") String buildTimestamp) {
        this.manifestGeneratorService = manifestGeneratorService;
        this.buildTimestamp = buildTimestamp;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("timestamp", buildTimestamp);

        String packageName = request.getParameter("packageName");

        GeneratedXml generatedXml = EMPTY_XML;
        String errormsg = "";

        if (packageName != null && packageName.length() > 0 && packageName.length() < 500) {
            if (InputValidator.validPackageName(packageName)) {
                generatedXml = manifestGeneratorService.generate(packageName);
            } else {
                errormsg = "Invalid package name: " + packageName;
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/ManifestGenerator.jsp"
                + buildParams(generatedXml, errormsg));
        rd.forward(request, response);
    }

    private String buildParams(GeneratedXml generatedXml, String errormsg) {
        StringBuilder sb = new StringBuilder();
        sb.append("?class=").append(generatedXml.getClassReceiver());
        sb.append("&xmlpermissions=").append(generatedXml.getXmlPermissions());
        sb.append("&xmlreceiver=").append(generatedXml.getXmlReceiver());
        sb.append("&errormsg=").append(errormsg);

        return sb.toString();
    }
}
