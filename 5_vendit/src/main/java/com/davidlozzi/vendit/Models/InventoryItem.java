package com.davidlozzi.vendit.Models;

import com.davidlozzi.vendit.Interfaces.Product;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem {
  private Product product;
  private Long quantity;
  private String position;
}
