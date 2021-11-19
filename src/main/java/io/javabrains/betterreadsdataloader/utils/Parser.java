// package io.javabrains.betterreadsdataloader.utils;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import io.javabrains.betterreadsdataloader.author.Author;
// import io.javabrains.betterreadsdataloader.author.AuthorRepository;
// import io.javabrains.betterreadsdataloader.book.Book;
// import io.javabrains.betterreadsdataloader.book.BookRepository;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// // @Component
// public class Parser {

// @Autowired
// private AuthorRepository authorRepository;

// @Autowired
// private BookRepository bookRepository;

// @Value("${datadump.location.authors}")
// private String authorsDumpLocation;

// @Value("${datadump.location.works}")
// private String worksDumpLocation;

// public void initAuthors() {
// Path path = Paths.get(authorsDumpLocation);
// try (Stream<String> lines = Files.lines(path)) {
// lines.forEach(line -> {
// String strObj = line.substring(line.indexOf("{"));
// JSONObject jsonObject;
// try {
// jsonObject = new JSONObject(strObj);
// Author author = Author.builder().name(jsonObject.optString("name"))
// .personalName(jsonObject.optString("personal_name"))
// .id(jsonObject.optString("key").replace("/authors/", "")).build();
// log.info("prsed line: {}", author);
// authorRepository.save(author);
// } catch (Exception e) {
// log.error("Error parsing line {}", strObj);
// }

// });
// } catch (IOException e) {
// log.error("Error parsing file {}", path);
// }
// }

// public void initWorks() {
// Path path = Paths.get(worksDumpLocation);
// DateTimeFormatter formatter =
// DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
// try (Stream<String> lines = Files.lines(path)) {
// lines.forEach(line -> {
// String strObj = line.substring(line.indexOf("{"));
// JSONObject jsonObject;
// try {
// jsonObject = new JSONObject(strObj);
// Book book = Book.builder().id(jsonObject.optString("key").replace("/works/",
// ""))
// .name(jsonObject.optString("title")).authorNames(new ArrayList<>()).build();
// JSONObject description = jsonObject.optJSONObject("description");
// if (description != null) {
// book.setDescription(description.optString("value"));
// }
// JSONObject publishedDate = jsonObject.optJSONObject("created");
// if (publishedDate != null) {
// String dateStr = publishedDate.optString("value");
// if (dateStr != null) {
// book.setPublishedDate(LocalDate.parse(dateStr, formatter));
// }
// }
// JSONArray coversArr = jsonObject.optJSONArray("covers");
// if (coversArr != null) {
// List<String> coverIds = new ArrayList<>();
// for (int i = 0; i < coversArr.length(); i++) {
// coverIds.add(coversArr.getString(i));
// }
// book.setCoverIds(coverIds);
// }

// JSONArray authorsArr = jsonObject.optJSONArray("authors");
// if (authorsArr != null) {
// List<String> authorIds = new ArrayList<>();
// for (int i = 0; i < authorsArr.length(); i++) {
// JSONObject authorObj = authorsArr.getJSONObject(i);
// if (authorObj != null) {
// String authorIdStr = authorObj.getJSONObject("author").optString("key");
// if (authorIdStr != null) {
// authorIds.add(authorIdStr.replace("/authors/", ""));
// }
// }
// }
// book.setAuthorIds(authorIds);
// List<String> authorNames = authorIds.stream().map(id ->
// authorRepository.findById(id))
// .map(author -> {
// if (!author.isPresent()) {
// return "Unknown author";
// }
// return author.get().getName();
// }).collect(Collectors.toList());
// book.setAuthorNames(authorNames);
// log.info("prsed line: {}", book);
// bookRepository.save(book);
// }
// } catch (Exception e) {
// log.error("Error parsing line {}", strObj);
// }

// });
// } catch (IOException e) {
// log.error("Error parsing file {}", path);
// }
// }
// }
