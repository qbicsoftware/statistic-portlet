package life.qbic.portal.portlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;
import com.vaadin.ui.HorizontalLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry point for portlet statistics-portlet. This class derives from {@link QBiCPortletUI}, which is found in the {@code portal-utils-lib} library.
 * 
 * @see https://github.com/qbicsoftware/portal-utils-lib
 */
@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.portal.portlet.AppWidgetSet")
public class StatisticsPortlet extends QBiCPortletUI {

    private static final Logger LOG = LogManager.getLogger(StatisticsPortlet.class);

    @Override
    protected Layout getPortletContent(final VaadinRequest request) {
        LOG.info("Generating content for {}", StatisticsPortlet.class);
        
        // TODO: generate content for your portlet
        //       this method returns any non-null layout to avoid a NullPointerException later on
        return new HorizontalLayout();
    }    
}