package com.appabc.cms.web;

import com.appabc.bean.bo.ProductPropertyContentBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zouxifeng on 1/23/15.
 */
public class ProductUtils {

    private final static Logger logger = LoggerFactory.getLogger(ProductUtils.class);

    private final static String MUD_CONTENT_FIELD = "mudContent";
    private final static String CLAY_CONTENT_FIELD = "clayContent";
    private final static String APPARENT_DENSITY_FIELD = "apparentDensity";
    private final static String CONSISTENCY_INDEX_FIELD = "consistencyIndex";
    private final static String MOISTURE_CONTENT_FIELD = "moistureContent";
    private final static String BULK_DENSITY_FIELD = "bulkDensity";
    private final static String CRUSHING_VALUE_INDEX = "crushingValueIndex";
    private final static String ELONGATED_PARTICLES = "elongatedParticles";
    private final static String PRODUCT_ADDRESS = "paddress";
    private final static String PRODUCT_COLOR = "pcolor";

    private final static String[] fields = new String[] {MUD_CONTENT_FIELD, CLAY_CONTENT_FIELD, APPARENT_DENSITY_FIELD,
        CONSISTENCY_INDEX_FIELD, MOISTURE_CONTENT_FIELD, BULK_DENSITY_FIELD, CRUSHING_VALUE_INDEX, ELONGATED_PARTICLES
    };


    public static List<ProductPropertyContentBean> collectProductProperties(HttpServletRequest req) {
        List<ProductPropertyContentBean> productProperties = new LinkedList<>();
        ProductPropertyContentBean ppc = null;

        for (String f : fields) {
            ppc = createProductProperty(req, f);
            if (ppc != null) {
                productProperties.add(ppc);
            }
        }

        return productProperties;
    }

    private static ProductPropertyContentBean createProductProperty(HttpServletRequest req, String fieldName) {
        ProductPropertyContentBean ppc = null;
        try {
            String ppid = ServletRequestUtils.getStringParameter(req, fieldName + "Id");
            if (StringUtils.isNotEmpty(ppid)) {
                ppc = new ProductPropertyContentBean();
                ppc.setPpid(ppid);
                String content = ServletRequestUtils.getStringParameter(req, fieldName);
                ppc.setContent(content);
            }
        } catch (ServletRequestBindingException e) {
            logger.error("Get product property content failed.", e);
        }
        return ppc;
    }
}
