package bistrot.compositeitem.service;

import bistrot.common.exception.CompositionItemNotFoundException;
import bistrot.compositeitem.fixture.CompositionItemFixture;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.entity.CompositionItem;
import bistrot.compositionitem.repository.CompositionItemRepository;
import bistrot.compositionitem.service.CompositionItemService;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@Import(CompositionItemRepository.class)
class CompositionItemServiceTest {

  @InjectMocks private CompositionItemService compositionItemService;

  @Mock private CompositionItemRepository compositionItemRepository;

  @Test
  void shouldReturnCompositionItemWhenSearchCompositionItemByIdAndThisCompositionItemInDataBase() throws CompositionItemNotFoundException {
    // Given
    Optional<CompositionItem> compositionItemActual = Optional.ofNullable(CompositionItemFixture.createCompositionItemEspresso());
    Mockito.when(this.compositionItemRepository.findById(any())).thenReturn(compositionItemActual);
    // When
    CompositionItemDto compositionItemDtoByIdExpected = this.compositionItemService.search(2L);
    // Then
    assertThat(compositionItemDtoByIdExpected)
            .isNotNull()
            .extracting(CompositionItemDto::getName).isEqualTo(compositionItemActual.get().getName());
    assertThat(compositionItemDtoByIdExpected)
            .extracting(CompositionItemDto::getId).isEqualTo(compositionItemActual.get().getId());
  }

  @Test
  void shouldThrowCompositionItemNotFoundExceptionWhenSearchCompositionItemByIdAndThisCompositionItemNotInDataBase() {
    // Given
    Mockito.when(this.compositionItemRepository.findById(any())).thenReturn(Optional.empty());

    // When
    assertThrows( // Then
            CompositionItemNotFoundException.class, () -> this.compositionItemService.search(2L));
  }

  @Test
  void shouldReturnCompositionsWithTheSameNameWhenThisCompositionIsInTheDataBas() {
    // Given

    Mockito.when(this.compositionItemRepository.findAllByName("espresso"))
        .thenReturn(Lists.list(CompositionItemFixture.createCompositionItemEspresso()));

    // When
    List<CompositionItemDto> compositionItemDtoList = this.compositionItemService.search("espresso");

    // Then
    assertThat(compositionItemDtoList).isNotNull().asList();

    assertThat(compositionItemDtoList)
        .extracting(CompositionItemDto::getName)
        .isEqualTo(Lists.list("espresso"));
  }

  @Test
  void shouldReturnAllCompositionsWhenCompositionNameIsBlank() {
    // Given
    Mockito.when(this.compositionItemRepository.findAll())
        .thenReturn(CompositionItemFixture.createCompositionItems());

    // When
    List<CompositionItemDto> compositionItemDtoList =
        this.compositionItemService.search(Strings.EMPTY);

    // Then
    assertThat(compositionItemDtoList).isNotNull().asList();

    assertThat(compositionItemDtoList)
        .extracting(CompositionItemDto::getName)
        .isEqualTo(Lists.list("mocha", "espresso"));
  }

  @Test
  void shouldThrowCompositionItemNotFoundExceptionWhenCompositionItemDeleteNotFound() {
    // Given
    Mockito.when(this.compositionItemRepository.existsById(1L)).thenReturn(Boolean.FALSE);

    // When
    assertThrows(// Then
           CompositionItemNotFoundException.class,
            () -> this.compositionItemService.delete(1L)
    );
  }
}
