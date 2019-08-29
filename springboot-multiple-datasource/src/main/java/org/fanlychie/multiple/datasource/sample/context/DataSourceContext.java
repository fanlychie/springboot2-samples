package org.fanlychie.multiple.datasource.sample.context;

import org.fanlychie.multiple.datasource.sample.enums.DataSourceEnum;

public final class DataSourceContext {

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

}