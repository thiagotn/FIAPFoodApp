package cc.thiago.fiapfoodapp.realm.table;

/**
 * Created by thiagotn on 05/02/2016.
 */
public interface RealmTable {

    String ID = "id";

    interface Restaurant {
        String NAME = "name";
        String PHONE = "phone";
        String TYPE = "type";
        String AVERAGE_COST = "averageCost";
        String DESCRIPTION = "description";
        String LOCALIZATION = "localization";
    }
}
