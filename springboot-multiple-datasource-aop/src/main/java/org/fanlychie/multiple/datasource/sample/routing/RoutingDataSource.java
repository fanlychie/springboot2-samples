package org.fanlychie.multiple.datasource.sample.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

    public static final class DataSourceContextHolder {

        private static final ThreadLocal<DataSourceEnum> THREAD_LOCAL_DATA_SOURCE = new ThreadLocal<>();

        public static void setDataSource(DataSourceEnum dataSource) {
            if (dataSource == null) {
                throw new NullPointerException();
            }
            THREAD_LOCAL_DATA_SOURCE.set(dataSource);
        }

        public static DataSourceEnum getDataSource() {
            DataSourceEnum dataSource = THREAD_LOCAL_DATA_SOURCE.get();
            if (dataSource == null) {
                throw new NullPointerException();
            }
            return dataSource;
        }

        public static void clear() {
            THREAD_LOCAL_DATA_SOURCE.remove();
        }

    }

    public enum DataSourceEnum { DB1, DB2 }

}