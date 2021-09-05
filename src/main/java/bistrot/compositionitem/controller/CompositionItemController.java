package bistrot.compositionitem.controller;

import bistrot.compositionitem.common.EndPointBistrot;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.exception.CompositionItemNameIsBlankException;
import bistrot.compositionitem.exception.CompositionItemNotFoundException;
import bistrot.compositionitem.service.CompositionItemService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(
        value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2021-09-1T08:55:39.049Z")
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointBistrot.VERSION + EndPointBistrot.COMPOSITION_ITEM)
public class CompositionItemController implements CompositionItemApi {

  private final CompositionItemService compositionItemService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompositionItemDto> create( @ApiParam(value = "Composition item contains information on the product to consume" ,required=true )@RequestBody CompositionItemDto compositionItem)
          throws CompositionItemNameIsBlankException {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.compositionItemService.create(compositionItem));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CompositionItemDto>> get(@ApiParam("Filter composition item") CompositionItemDto filter) {
    return ResponseEntity.ok(this.compositionItemService.search(filter));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompositionItemDto> get(@ApiParam(value = "Fetch composition item by id", required = true) @PathVariable("id") String id) throws CompositionItemNotFoundException {
    return ResponseEntity.ok(this.compositionItemService.search(Long.valueOf(id)));
  }

  @PutMapping(
      value = "{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@ApiParam(value = "Composition item id to update", required = true) @PathVariable String id, @ApiParam(value = "Composition item", required = true) @RequestBody CompositionItemDto compositionItemDto) throws CompositionItemNotFoundException {
    this.compositionItemService.update(Long.valueOf(id), compositionItemDto);
  }

  @DeleteMapping(value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@ApiParam(value = "Composition item id to delete", required = true) @PathVariable String id) throws CompositionItemNotFoundException {
    this.compositionItemService.delete(Long.valueOf(id));
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteAll() {
    this.compositionItemService.deleteAll();
  }

}
