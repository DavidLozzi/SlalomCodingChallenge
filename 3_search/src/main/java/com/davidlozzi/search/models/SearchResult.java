package com.davidlozzi.search.models;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.*;

// lombok does work here
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SearchResult extends RepresentationModel<SearchResult> {
  private String keyword;
  private List<ResultItem> results;
  private Integer resultCount;
  private Integer startIndex;
  private Integer pageSize;
}
