package com.davidlozzi.vendit.Models;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends RepresentationModel<Inventory> {
  private List<InventoryItem> itemsForSale;
}
