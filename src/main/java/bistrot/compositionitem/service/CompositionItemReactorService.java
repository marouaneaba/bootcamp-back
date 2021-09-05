package bistrot.compositionitem.service;

import bistrot.common.error.message.MessageError;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.common.exception.CompositionItemNotFoundException;
import bistrot.compositionitem.mapper.CompositionItemMapper;
import bistrot.compositionitem.repository.CompositionItemRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CompositionItemReactorService {

  private CompositionItemRepository compositionItemRepository;

  public Flux<CompositionItemDto> search(String compositionItemName) {
    if (!Strings.isBlank(compositionItemName)) {
      return this.searchByName(compositionItemName);
    }
    return this.getAll();
  }

  private Flux<CompositionItemDto> searchByName(String compositionItemName) {
    return Flux.fromIterable(this.compositionItemRepository.findAllByName(compositionItemName))
            .map(CompositionItemMapper::toCompositionItemDto)
            .switchIfEmpty(Flux.empty())
            .log();
  }

  private Flux<CompositionItemDto> getAll() {
    return Flux.fromIterable(this.compositionItemRepository.findAll())
        .map(CompositionItemMapper::toCompositionItemDto)
        .switchIfEmpty(Flux.empty())
        .log();
  }

  public Mono<CompositionItemDto> search(Long id) {
    return Mono.justOrEmpty(this.compositionItemRepository.findById(id))
        .map(CompositionItemMapper::toCompositionItemDto)
        .switchIfEmpty(
            Mono.error(
                new CompositionItemNotFoundException(
                    MessageError.buildMessageError(id, MessageError.COMPOSITION_ITEM_NOT_FOUND))))
        .log();
  }

  public Mono<CompositionItemDto> create(CompositionItemDto compositionItemDto) {
    if( null == compositionItemDto) {
      throw new IllegalArgumentException(MessageError.COMPOSITION_ITEM_DTO_ARGUMENT_IS_NULL);
    }
    return this.saveCompositionItem(compositionItemDto);
  }

  private Mono<CompositionItemDto> saveCompositionItem(CompositionItemDto compositionItemDto) {
    return Mono.justOrEmpty(
            this.compositionItemRepository.save(
                CompositionItemMapper.toCompositionItem(compositionItemDto)))
        .map(CompositionItemMapper::toCompositionItemDto)
        .switchIfEmpty(
            Mono.error(
                new CompositionItemNotFoundException(
                    MessageError.buildMessageError(
                        compositionItemDto.getId(), MessageError.COMPOSITION_ITEM_NOT_FOUND))))
        .log();
  }

  public Mono<Void> update(Long id, CompositionItemDto compositionItemDto) {
    if(null == compositionItemDto) {
      throw new IllegalArgumentException(MessageError.COMPOSITION_ITEM_DTO_ARGUMENT_IS_NULL);
    }

    return Mono.justOrEmpty(this.compositionItemRepository.findById(id))
        .switchIfEmpty(
            Mono.error(
                new CompositionItemNotFoundException(
                    MessageError.buildMessageError(id, MessageError.COMPOSITION_ITEM_NOT_FOUND))))
        .doOnNext(
            compositionItem -> {
              compositionItemDto.setId(id);
              this.saveCompositionItem(compositionItemDto);
            })
        .then(Mono.empty());
  }

  public Mono<Void> delete(Long id) {
    return Mono.justOrEmpty(this.compositionItemRepository.findById(id))
        .switchIfEmpty(
            Mono.error(
                new CompositionItemNotFoundException(
                    MessageError.buildMessageError(id, MessageError.COMPOSITION_ITEM_NOT_FOUND))))
        .doOnNext(
            compositionItemId ->
              this.compositionItemRepository.deleteById(compositionItemId.getId())
            )
        .then(Mono.empty());
  }

  public Mono<Void> deleteAll() {
    this.compositionItemRepository.deleteAll();
    return Mono.empty();
  }
}
