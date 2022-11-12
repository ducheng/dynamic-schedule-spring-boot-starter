/*
 * Copyright Chris2018998
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dc.dynamic.schedule.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 *  注册DynamicSchedule 的视图层到Spring的ioc
 */
public class DynamicScheduleControllerRegister implements ImportBeanDefinitionRegistrar {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public final void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        String resetControllerRegName = DynamicScheduleController.class.getName();
        // 判断是否被注册过
        if (!existsBeanDefinition(resetControllerRegName,registry)) {
            GenericBeanDefinition define = new GenericBeanDefinition();
            define.setBeanClass(DynamicScheduleController.class);
            define.setPrimary(true);
            registry.registerBeanDefinition(resetControllerRegName, define);
            log.info("Register DynamicScheduleController({}) with id:{}", define.getBeanClassName(), resetControllerRegName);
        } else {
            log.error("BeanDefinition id {} already exists in spring context", resetControllerRegName);
        }
    }

    /**
     * 判断是否注册过
     * @param beanName
     * @param registry
     * @return
     */
    private  boolean existsBeanDefinition(String beanName, BeanDefinitionRegistry registry) {
        try {
            return registry.getBeanDefinition(beanName) != null;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }
}
