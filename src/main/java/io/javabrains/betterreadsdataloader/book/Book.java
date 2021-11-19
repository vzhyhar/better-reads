package io.javabrains.betterreadsdataloader.book;

import java.time.LocalDate;
import java.util.List;

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
@Table(value = "book_by_id")
public class Book {

    @Id
    @PrimaryKeyColumn(name = "book_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @Column(value = "book_name")
    @CassandraType(type = Name.TEXT)
    private String name;

    @Column(value = "book_dexcription")
    @CassandraType(type = Name.TEXT)
    private String description;

    @Column(value = "published_date")
    @CassandraType(type = Name.DATE)
    private LocalDate publishedDate;

    @Column(value = "cover_ids")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> coverIds;

    @Column(value = "author_id")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> authorIds;

    @Column(value = "author_names")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> authorNames;
}
