package com.davidlozzi.search.models;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SearchResult extends RepresentationModel<SearchResult> {
  private String keyword;
  private List<ResultItem> results;
  private Integer resultCount;
  private Integer startIndex;
  private Integer pageSize;
}
