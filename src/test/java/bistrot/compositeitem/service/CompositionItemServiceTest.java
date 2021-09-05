package bistrot.compositeitem.service;

import bistrot.compositeitem.fixture.CompositionItemFixture;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.entity.CompositionItem;
import bistrot.compositionitem.exception.CompositionItemNameIsBlankException;
import bistrot.compositionitem.mapper.CompositionItemMapper;
import bistrot.compositionitem.repository.CompositionItemRepository;
import bistrot.compositionitem.service.CompositionItemService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Import(CompositionItemRepository.class)
class CompositionItemServiceTest {

  @InjectMocks private CompositionItemService compositionItemService;

  @Mock private CompositionItemRepository compositionItemRepository;

  @Test
  void shouldReturnCompositionsWithTheSameNameWhenThisCompositionIsInTheDataBas() {
    // Given
    CompositionItemDto filter = CompositionItemDto.builder().name("espresso").build();

    Mockito.when(this.compositionItemRepository.findAllByName("espresso"))
        .thenReturn(Lists.list(CompositionItemFixture.createCompositionItemEspresso()));

    // When
    List<CompositionItemDto> compositionItemDtoList = this.compositionItemService.search(filter);

    // Then
    assertThat(compositionItemDtoList).isNotNull().asList();

    assertThat(compositionItemDtoList)
        .extracting(CompositionItemDto::getName)
        .isEqualTo(Lists.list("espresso"));
  }

  @Test
  void shouldReturnAllCompositionsWhenCompositionNameIsBlank() {
    // Given
    // todo centralise duplication when
    Mockito.when(this.compositionItemRepository.findAll())
        .thenReturn(CompositionItemFixture.createCompositionItems());
    CompositionItemDto filter = CompositionItemDto.builder().name("").build();

    // When
    List<CompositionItemDto> compositionItemDtoList = this.compositionItemService.search(filter);

    // Then
    assertThat(compositionItemDtoList).isNotNull().asList();

    assertThat(compositionItemDtoList)
        .extracting(CompositionItemDto::getName)
        .isEqualTo(Lists.list("mocha", "espresso"));
  }

  @Test
  void shouldThrowCompositionItemNameIsBlankExceptionWhenCompositionItemNameIsBlank() {
    // Given
    CompositionItem compositionItemNameEmpty =
        CompositionItemFixture.createCompositionItemNameEmpty();

    // When
    assertThrows( // Then
        CompositionItemNameIsBlankException.class,
        () ->
            this.compositionItemService.create(
                CompositionItemMapper.toCompositionItemDto(compositionItemNameEmpty)));

  }
}
