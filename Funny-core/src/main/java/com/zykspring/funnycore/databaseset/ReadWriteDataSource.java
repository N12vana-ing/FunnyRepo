package com.zykspring.funnycore.databaseset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteDataSource extends AbstractRoutingDataSource {

    private static Logger log = LoggerFactory.getLogger(ReadWriteDataSource.class);

//    private final int dataSourceNumber;
//    private AtomicInteger count = new AtomicInteger(0);
//
//    public ReadWriteDataSource(int dataSourceNumber){ this.dataSourceNumber = dataSourceNumber; }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType = DataSourceContextHolder.getDBType();
        if(dataSourceType == null || dataSourceType.equals(DataBaseChoose.WRITE.choose())){
            log.info("$$$$ is write database ! ");
            return DataBaseChoose.WRITE.choose();
        }else{
            log.info("$$$$ is read database ! ");
            return DataBaseChoose.READ.choose();
        }

        //简单的负载均衡
//        int number = count.getAndIncrement();
//        int lookupKey = number % dataSourceNumber;
//
//        return new Integer(lookupKey);
    }

}
