package io.javabrains.betterreadsdataloader.author;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "author_by_id")
public class Author {

    @Id
    @PrimaryKeyColumn(name = "author_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @Column(value = "author_name")
    @CassandraType(type = Name.TEXT)
    private String name;

    @Column(value = "personal_name")
    @CassandraType(type = Name.TEXT)
    private String personalName;
}
