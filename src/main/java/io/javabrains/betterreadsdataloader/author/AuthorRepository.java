package io.javabrains.betterreadsdataloader.author;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface AuthorRepository extends CassandraRepository<Author, String> {

}
