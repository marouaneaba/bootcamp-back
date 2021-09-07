package bistrot.web.api;

import bistrot.common.EndPointBistrot;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.common.exception.CompositionItemNotFoundException;
import bistrot.compositionitem.service.CompositionItemService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(
    value = "io.swagger.codegen.languages.SpringCodegen",
    date = "2021-09-1T08:55:39.049Z")
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointBistrot.VERSION_1 + EndPointBistrot.COMPOSITION_ITEM)
public class CompositionItemController implements CompositionItemApi {

  private final CompositionItemService compositionItemService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompositionItemDto> create(
      @ApiParam(
              value = "Composition item contains information on the product to consume",
              required = true)
          @RequestBody
          @Valid
          CompositionItemDto compositionItem) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.compositionItemService.create(compositionItem));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CompositionItemDto>> get(
      @ApiParam("Composition item name") String compositionItemName) {
    return ResponseEntity.ok(this.compositionItemService.search(compositionItemName));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompositionItemDto> get(
      @ApiParam(value = "Fetch composition item by id", required = true) @PathVariable Long id)
      throws CompositionItemNotFoundException {
    return ResponseEntity.ok(this.compositionItemService.search(id));
  }

  @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(
      @ApiParam(value = "Composition item id to update", required = true) @PathVariable Long id,
      @ApiParam(value = "Composition item", required = true) @RequestBody
          CompositionItemDto compositionItemDto)
      throws CompositionItemNotFoundException {
    this.compositionItemService.update(id, compositionItemDto);
  }

  @DeleteMapping(value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @ApiParam(value = "Composition item id to delete", required = true) @PathVariable Long id)
      throws CompositionItemNotFoundException {
    this.compositionItemService.delete(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAll() {
    this.compositionItemService.deleteAll();
  }
}
