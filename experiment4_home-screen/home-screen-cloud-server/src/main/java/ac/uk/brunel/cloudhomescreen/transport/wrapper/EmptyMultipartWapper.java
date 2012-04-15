package ac.uk.brunel.cloudhomescreen.transport.wrapper;

import net.sourceforge.stripes.config.ConfigurableComponent;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.controller.multipart.MultipartWrapper;
import net.sourceforge.stripes.controller.multipart.MultipartWrapperFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EmptyMultipartWapper implements ConfigurableComponent, MultipartWrapperFactory {

    public void init(Configuration conf) throws Exception {
    }


    public MultipartWrapper wrap(HttpServletRequest request) throws IOException, FileUploadLimitExceededException {
        return null;
    }
}