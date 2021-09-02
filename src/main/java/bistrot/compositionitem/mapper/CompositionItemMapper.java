package bistrot.compositionitem.mapper;

import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.entity.CompositionItem;

import java.util.List;
import java.util.stream.Collectors;

public class CompositionItemMapper {

  private CompositionItemMapper() {
    throw new AssertionError("Static composition item mapper class must not be instantiated.");
  }

  public static List<CompositionItemDto> toCompositionItemsDto(
      List<CompositionItem> compositionItems) {
    return compositionItems.stream()
        .map(CompositionItemMapper::toCompositionItemDto)
        .collect(Collectors.toList());
  }

  public static CompositionItemDto toCompositionItemDto(CompositionItem compositionItem) {
    return CompositionItemDto
            .builder()
            .id(compositionItem.getId())
            .name(compositionItem.getName())
            .price(compositionItem.getPrice())
            .quantity(compositionItem.getQuantity())
            .recipe(compositionItem.getRecipe())
            .build();
  }

  public static CompositionItem toCompositionItem(CompositionItemDto compositionItemDto) {
    return CompositionItem
            .builder()
            .id(compositionItemDto.getId())
            .name(compositionItemDto.getName())
            .price(compositionItemDto.getPrice())
            .quantity(compositionItemDto.getQuantity())
            .recipe(compositionItemDto.getRecipe())
            .build();
  }
}
