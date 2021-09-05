package bistrot.web.api;

import bistrot.common.EndPointBistrot;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.service.CompositionItemReactorService;
import bistrot.web.api.exceptionhandler.CompositionItemReactiveApi;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@javax.annotation.Generated(
        value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2021-09-1T08:55:39.049Z")
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointBistrot.VERSION_2 + EndPointBistrot.COMPOSITION_ITEM)
public class CompositionItemReactiveController implements CompositionItemReactiveApi {

  private final CompositionItemReactorService compositionItemReactorService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Flux<CompositionItemDto> get(
      @ApiParam("Composition item name") @RequestParam(required = false) String compositionName) {
    return this.compositionItemReactorService.search(compositionName);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Mono<CompositionItemDto> get(
      @ApiParam(value = "Fetch composition item by id", required = true) @PathVariable Long id) {
    return this.compositionItemReactorService.search(id);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<CompositionItemDto> create(
      @ApiParam(
              value = "Composition item contains information on the product to consume",
              required = true)
          @RequestBody
          @Valid
          CompositionItemDto compositionItemDto) {
    return this.compositionItemReactorService.create(compositionItemDto);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> update(
      @ApiParam(value = "Composition item id to update", required = true) @PathVariable Long id,
      @ApiParam(value = "Composition item", required = true) @RequestBody @Valid
          CompositionItemDto compositionItemDto) {
    return this.compositionItemReactorService.update(id, compositionItemDto);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> delete(@ApiParam(value = "Composition item id to delete", required = true)  @PathVariable Long id) {
    return this.compositionItemReactorService.delete(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAll() {
    return this.compositionItemReactorService.deleteAll();
  }
}
