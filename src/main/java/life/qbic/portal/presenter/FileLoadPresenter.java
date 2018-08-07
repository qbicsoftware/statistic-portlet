package life.qbic.portal.presenter;

import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import life.qbic.portal.Styles;
import life.qbic.portal.exceptions.DataNotFoundException;
import life.qbic.portal.io.MyReceiver;
import life.qbic.portal.io.YAMLParser;
import life.qbic.portal.presenter.tabs.DummyChartPresenter;
import life.qbic.portal.presenter.utils.CustomNotification;
import life.qbic.portal.utils.PortalUtils;
import life.qbic.portal.view.TabView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;


/**
 * @author fhanssen
 */

class FileLoadPresenter {

    private static final Logger logger = LogManager.getLogger(FileLoadPresenter.class);

    private final MainPresenter mainPresenter;
    private final MyReceiver receiver = new MyReceiver();
    private final Upload upload = new Upload("Upload file", receiver);
    private final DummyChartPresenter dummyChartPresenter;

    public FileLoadPresenter(MainPresenter mainPresenter){

        this.mainPresenter = mainPresenter;
        this.upload.setImmediate(true);
        this.dummyChartPresenter = new DummyChartPresenter(mainPresenter);
        this.dummyChartPresenter.setUp();


    }

    private void addUploadConfigFileOption(TabView tabView){

        tabView.addComponents(upload, new Label("Please select a file to upload"));
        upload.addSucceededListener((Upload.SucceededListener) succeededEvent ->
                setChartsFromConfig(receiver.getFile(), receiver.getFilename()));
    }

    private void showDummyChart(){
        dummyChartPresenter.addChart(new TabView(dummyChartPresenter.getView(),
                dummyChartPresenter.getModel()), "Dummy Chart");
    }

    void setChartsFromConfig(String filename){
        try {
            this.mainPresenter.setMainConfig(YAMLParser.parseConfig(filename));
            logger.info("Finished parsing");
            mainPresenter.addChildPresenter();
        }catch(Exception e) {
            showDummyChart();
            Styles.notification("Error", e.toString(), Styles.NotificationType.ERROR);
            logger.error("Charts could not be displayed.", e);
        }

    }

    private void setChartsFromConfig(File file, String inputFilename){

        try {
            this.mainPresenter.setMainConfig(YAMLParser.parseConfig(new FileInputStream(file), inputFilename));
            logger.info("Finished parsing.");
            mainPresenter.addChildPresenter();
            mainPresenter.addCharts();
        }catch(FileNotFoundException  e){
            handleConfigParseError(e);
            logger.error("Charts could not be displayed.", e);

        }catch (Exception e){
            showDummyChart();
            Styles.notification("Error", e.toString(), Styles.NotificationType.ERROR);
            logger.error("Charts could not be displayed.", e);

        }
    }

    private void handleConfigParseError(Exception e){

        logger.error("Parsing of YAML file failed: " + e);
        Styles.notification("Error", e.toString(), Styles.NotificationType.ERROR);

        mainPresenter.clear();
        showDummyChart();

        if(!PortalUtils.isLiferayPortlet() || mainPresenter.isAdmin()){
            addUploadConfigFileOption(dummyChartPresenter.getTabView());
        }
    }


}

