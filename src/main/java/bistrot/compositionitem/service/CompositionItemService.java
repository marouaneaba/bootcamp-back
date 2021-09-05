package bistrot.compositionitem.service;

import bistrot.compositionitem.exception.CompositionItemNameIsBlankException;
import bistrot.compositionitem.exception.CompositionItemNotFoundException;
import bistrot.compositionitem.mapper.CompositionItemMapper;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.entity.CompositionItem;
import bistrot.compositionitem.repository.CompositionItemRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CompositionItemService {

  private static final String COMPOSITION_ITEM_NOT_FOUND = "Composition item not found";
  private static final String COMPOSITION_ITEM_NAME_IS_BLANK = "Composition item name is blank";

  private CompositionItemRepository compositionItemRepository;

  public CompositionItemDto search(Long id) throws CompositionItemNotFoundException {
    Optional<CompositionItem> compositionItem = this.compositionItemRepository.findById(id);
    if (!compositionItem.isPresent()) {
      throw new CompositionItemNotFoundException(COMPOSITION_ITEM_NOT_FOUND);
    }
    return CompositionItemMapper.toCompositionItemDto(compositionItem.get());
  }

  public List<CompositionItemDto> search(CompositionItemDto filter) {
    if (this.isFilterUsed(filter)) {
      return CompositionItemMapper.toCompositionItemsDto(
          this.compositionItemRepository.findAllByName(filter.getName()));
    }
    return CompositionItemMapper.toCompositionItemsDto(this.compositionItemRepository.findAll());
  }

  private boolean isFilterUsed(CompositionItemDto filter) {
    return null != filter && Strings.isNotBlank(filter.getName());
  }

  public CompositionItemDto create(CompositionItemDto compositionItemDto)
      throws CompositionItemNameIsBlankException {
    this.checkCompositionItem(compositionItemDto);

    CompositionItem compositionItemToSave =
        CompositionItemMapper.toCompositionItem(compositionItemDto);

    return CompositionItemMapper.toCompositionItemDto(
        this.compositionItemRepository.save(compositionItemToSave));
  }

  private void checkCompositionItem(CompositionItemDto compositionItemDto)
      throws CompositionItemNameIsBlankException {
    if (null != compositionItemDto && Strings.isBlank(compositionItemDto.getName())) {
      throw new CompositionItemNameIsBlankException(COMPOSITION_ITEM_NAME_IS_BLANK);
    }
  }

  public void update(Long id, CompositionItemDto compositionItemDto)
      throws CompositionItemNotFoundException {
    if (!this.compositionItemRepository.existsById(id)) {
      throw new CompositionItemNotFoundException(COMPOSITION_ITEM_NOT_FOUND);
    }
    CompositionItem compositionItemToSave =
        CompositionItemMapper.toCompositionItem(compositionItemDto);
    compositionItemToSave.setId(id);
    this.compositionItemRepository.save(compositionItemToSave);
  }

  public void delete(Long id) throws CompositionItemNotFoundException {
    if (!this.compositionItemRepository.existsById(id)) {
      throw new CompositionItemNotFoundException(COMPOSITION_ITEM_NOT_FOUND);
    }
    this.compositionItemRepository.deleteById(id);
  }

  public void deleteAll() {
    this.compositionItemRepository.deleteAll();
  }
}
