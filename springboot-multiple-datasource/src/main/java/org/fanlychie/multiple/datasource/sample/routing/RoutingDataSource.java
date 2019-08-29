package org.fanlychie.multiple.datasource.sample.routing;

import org.fanlychie.multiple.datasource.sample.context.DataSourceContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDataSource();
    }

}