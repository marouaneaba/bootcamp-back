package bistrot.compositeitem.service;

import bistrot.common.exception.CompositionItemNotFoundException;
import bistrot.compositeitem.fixture.CompositionItemFixture;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.entity.CompositionItem;
import bistrot.compositionitem.repository.CompositionItemRepository;
import bistrot.compositionitem.service.CompositionItemReactorService;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@Import(CompositionItemRepository.class)
class CompositionItemServiceReactorTest {

  @InjectMocks private CompositionItemReactorService compositionItemReactorService;

  @Mock private CompositionItemRepository compositionItemRepository;

  @Test
  void shouldReturnCompositionItemWhenSearchCompositionItemByIdAndThisCompositionItemInDataBase() throws CompositionItemNotFoundException {

    Optional<CompositionItem> compositionItemActual = Optional.ofNullable(CompositionItemFixture.createCompositionItemEspresso());
    Mockito.when(this.compositionItemRepository.findById(any())).thenReturn(compositionItemActual);

    StepVerifier.create(this.compositionItemReactorService.search(2L))
            .expectNextCount(1)
            .expectNextMatches(compositionItemDto -> compositionItemDto.getName().equals(compositionItemActual.get().getName()))
            .expectNextMatches(compositionItemDto -> compositionItemDto.getId().equals(compositionItemActual.get().getId()));
  }

  @Test
  void shouldThrowCompositionItemNotFoundExceptionWhenSearchCompositionItemByIdAndThisCompositionItemNotInDataBase() {

    Mockito.when(this.compositionItemRepository.findById(any())).thenReturn(Optional.empty());

    StepVerifier.create(this.compositionItemReactorService.search(2L))
            .expectError(CompositionItemNotFoundException.class)
                    .verify();
  }

  @Test
  void shouldReturnCompositionsWithTheSameNameWhenThisCompositionIsInTheDataBas() {

    Mockito.when(this.compositionItemRepository.findAllByName("espresso"))
        .thenReturn(Lists.list(CompositionItemFixture.createCompositionItemEspresso()));

    StepVerifier.create(this.compositionItemReactorService.search("espresso"))
            .expectNextMatches(compositionItemDto -> "espresso".equals(compositionItemDto.getName()))
            .verifyComplete();
  }

  @Test
  void shouldThrowCompositionItemNotFoundExceptionWhenCompositionItemDeleteNotFound() {

    Mockito.when(this.compositionItemRepository.findById(1L)).thenReturn(Optional.empty());

    StepVerifier.create(this.compositionItemReactorService.delete(1L))
            .expectError(CompositionItemNotFoundException.class)
                    .verify();
  }
}
