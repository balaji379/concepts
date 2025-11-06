package com.spring_batch.nvd_task.cofig;

import com.spring_batch.nvd_task.entity.NvdEntity;
import org.springframework.batch.item.ItemProcessor;


public class NvdProcessor implements ItemProcessor<NvdEntity,NvdEntity> {
    @Override
    public NvdEntity process(NvdEntity item) throws Exception {
        return item;
    }
}
