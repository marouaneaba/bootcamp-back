package bistrot.compositionitem.service;

import bistrot.common.exception.CompositionItemNotFoundException;
import bistrot.common.error.message.MessageError;
import bistrot.compositionitem.mapper.CompositionItemMapper;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.entity.CompositionItem;
import bistrot.compositionitem.repository.CompositionItemRepository;
import bistrot.infra.kafka.consumer.Consumer;
import bistrot.infra.kafka.producer.Producer;
import bistrot.infra.kafka.topic.Topic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class CompositionItemService implements Consumer {

  private final Producer producer;

  private CompositionItemRepository compositionItemRepository;

  public CompositionItemDto search(Long id) throws CompositionItemNotFoundException {
    Optional<CompositionItem> compositionItem = this.compositionItemRepository.findById(id);
    if (!compositionItem.isPresent()) {
      throw new CompositionItemNotFoundException(MessageError.buildMessageError(id, MessageError.COMPOSITION_ITEM_NOT_FOUND));
    }
    return CompositionItemMapper.toCompositionItemDto(compositionItem.get());
  }

  public List<CompositionItemDto> search(String compositionItemName) {
    if (!Strings.isBlank(compositionItemName)) {
      return CompositionItemMapper.toCompositionItemsDto(
          this.compositionItemRepository.findAllByName(compositionItemName));
    }
    return CompositionItemMapper.toCompositionItemsDto(this.compositionItemRepository.findAll());
  }

  public CompositionItemDto create(CompositionItemDto compositionItemDto) {
    if( null == compositionItemDto) {
      throw new IllegalArgumentException(MessageError.COMPOSITION_ITEM_DTO_ARGUMENT_IS_NULL);
    }

    CompositionItem compositionItemToSave =
        CompositionItemMapper.toCompositionItem(compositionItemDto);

    return CompositionItemMapper.toCompositionItemDto(
        this.compositionItemRepository.save(compositionItemToSave));
  }

  public void update(Long id, CompositionItemDto compositionItemDto)
      throws CompositionItemNotFoundException {
    if (!this.compositionItemRepository.existsById(id)) {
      throw new CompositionItemNotFoundException(MessageError.buildMessageError(id, MessageError.COMPOSITION_ITEM_NOT_FOUND));
    }
    CompositionItem compositionItemToSave =
        CompositionItemMapper.toCompositionItem(compositionItemDto);
    compositionItemToSave.setId(id);
    this.compositionItemRepository.save(compositionItemToSave);
  }

  public void delete(Long id) throws CompositionItemNotFoundException {
    if (!this.compositionItemRepository.existsById(id)) {
      throw new CompositionItemNotFoundException(MessageError.buildMessageError(id,MessageError.COMPOSITION_ITEM_NOT_FOUND));
    }
    this.compositionItemRepository.deleteById(id);
  }

  public void deleteAll() {
    this.compositionItemRepository.deleteAll();
  }

  public void sendStream(Topic topic, CompositionItemDto compositionItemDto) {
    this.producer.sendMessage(topic, CompositionItemMapper.toCompositionItem(compositionItemDto));
  }

  @Override
  public void run(CompositionItem compositionItem, String topicName) {
    log.info("receive stream {} from topic: {}", compositionItem, topicName);
  }
}
