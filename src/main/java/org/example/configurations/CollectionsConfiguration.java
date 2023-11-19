package org.example.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
public class CollectionsConfiguration {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CollectionsConfiguration(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void initialIndexes() {
        if (mongoTemplate.indexOps("new").getIndexInfo()
                .stream()
                        .noneMatch(indexInfo -> indexInfo.getName().equals("title_1")))
            mongoTemplate.indexOps("new").ensureIndex(new Index().on("title", Sort.Direction.ASC).named("title_1"));
        if(mongoTemplate.indexOps("new").getIndexInfo()
                .stream()
                .noneMatch(indexInfo -> indexInfo.getName().equals("timestamp_1")))
            mongoTemplate.indexOps("new").ensureIndex(new Index().on("timestamp",Sort.Direction.DESC).named("timestamp_1"));
    }
}
