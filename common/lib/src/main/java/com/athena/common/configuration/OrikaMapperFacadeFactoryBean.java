package com.athena.common.configuration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by brook.xi on 12/26/2015.
 */
@Component
public class OrikaMapperFacadeFactoryBean implements FactoryBean<MapperFacade> {
    private DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Override
    public MapperFacade getObject() throws Exception {
        return mapperFactory.getMapperFacade();
    }

    @Override
    public Class<?> getObjectType() {
        return MapperFacade.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
