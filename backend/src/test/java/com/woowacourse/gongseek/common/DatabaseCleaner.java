package com.woowacourse.gongseek.common;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(entityType -> entityType.getJavaType().getAnnotation(Entity.class) != null)
                .map(entityType -> changeNaming(entityType.getName()))
                .collect(Collectors.toList());
    }

    private String changeNaming(String entityName) {
        StringBuilder tableName = new StringBuilder();
        for (int index = 0; index < entityName.length(); index++) {
            char ch = entityName.charAt(index);
            if (index > 0 && Character.isUpperCase(ch)) {
                tableName.append("_");
            }
            tableName.append(Character.toLowerCase(ch));
        }
        return tableName.toString();
    }

    @Transactional
    public void tableClear() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
