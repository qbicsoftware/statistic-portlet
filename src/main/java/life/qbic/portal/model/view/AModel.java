package life.qbic.portal.model.view;

/**
 * @author fhanssen
 * This interface class declares a methods needed for a general visulaization, mainly the adding data.
 */
public interface AModel<T> {

    void addData(T... dataItem);

    String getTitle();

    String getSubTitle();

    String getTabTitle();

}