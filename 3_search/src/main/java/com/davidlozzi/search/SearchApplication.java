package com.davidlozzi.search;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Arrays;
import java.util.List;

import com.davidlozzi.search.models.ApiErrorResponse;
import com.davidlozzi.search.models.ApiException;
import com.davidlozzi.search.models.Filter;
import com.davidlozzi.search.models.ResultItem;
import com.davidlozzi.search.models.SearchResult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(SearchApplication.class, args);
  }

  @GetMapping("/search")
  @ResponseBody
  public ResponseEntity search(@RequestParam(value = "keyword", defaultValue = "") String keyword,
      @RequestParam(value = "startIndex", defaultValue = "0") Integer startIndex,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "movie", defaultValue = "") String movie,
      @RequestParam(value = "character", defaultValue = "") String character) {
    try {
      if (keyword.equals(null) || keyword.equals("")) {
        throw new ApiException("keyword is missing", HttpStatus.BAD_REQUEST);
      }
      Download.files();
      SearchResult results = new SearchResult();
      results.setKeyword(keyword);
      results.setStartIndex(startIndex);
      results.setPageSize(pageSize);

      List<ResultItem> resultsList = SearchFiles.search(keyword, movie, character);
      results.setResultCount(resultsList.size());

      results.add(linkTo(methodOn(SearchApplication.class).search(keyword, startIndex, pageSize, movie, character))
          .withSelfRel());

      if (resultsList.size() > 0) {
        ResultItem[] pageresults = Arrays.copyOfRange(resultsList.toArray(), startIndex, startIndex + pageSize,
            ResultItem[].class);
        pageresults = Arrays.stream(pageresults).filter(i -> i != null).toArray(ResultItem[]::new);
        results.setResults(Arrays.asList(pageresults));

        List<Filter> filters = SearchFiles.getFilters(resultsList);
        results.setFilters(filters);

        results.add(
            linkTo(methodOn(SearchApplication.class).search(keyword, startIndex + pageSize, pageSize, movie, character))
                .withRel("nextPage"));
        results.add(
            linkTo(methodOn(SearchApplication.class).search(keyword, 0, results.getResultCount(), movie, character))
                .withRel("allResults"));
        return new ResponseEntity<>(results, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (ApiException ex) {
      System.out.print(ex);
      ex.printStackTrace();
      ApiErrorResponse apiError = new ApiErrorResponse(ex.getMessage(), ex.getHttpStatus().value());
      return new ResponseEntity<>(apiError, ex.getHttpStatus());
    } catch (Exception ex) {
      System.out.print(ex);
      ex.printStackTrace();
      ApiErrorResponse apiError = new ApiErrorResponse(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
      return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
