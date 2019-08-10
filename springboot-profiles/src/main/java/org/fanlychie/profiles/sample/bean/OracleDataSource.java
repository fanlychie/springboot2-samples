package org.fanlychie.profiles.sample.bean;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by fanlychie on 2019/6/22.
 */
@Component
@Profile("oracle") // 若激活的profiles中包含oracle, 则实例化此类
public class OracleDataSource implements DataSourceConfig {

    @Override
    public Object getDataSource() {
        return "[[ Oracle ]]";
    }

}