package bistrot.web.api.exceptionhandler;

import bistrot.common.exception.CompositionItemNotFoundException;
import bistrot.compositionitem.dto.CompositionItemDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Api(
    value = "Composition item",
    tags = {"composition item"})
public interface CompositionItemReactiveApi {

  @ApiOperation(
      value = "Create composition item",
      notes = "Create composition item",
      response = CompositionItemDto.class,
      tags = {
        "composition item",
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 201,
            message = "Successful operation",
            response = CompositionItemDto.class),
        @ApiResponse(code = 400, message = "Malformed request the composition item name is blank")
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  Mono<CompositionItemDto> create(
      @ApiParam(
              value = "Composition item contains information on the product to create",
              required = true)
          @RequestBody
          CompositionItemDto compositionItem);


  @ApiOperation(
      value = "Fetch composition items or composition item by name",
      notes = "Fetch composition items or composition item by name",
      response = CompositionItemDto.class,
      tags = {
        "composition item",
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = CompositionItemDto.class)
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  Flux<CompositionItemDto> get(
      @ApiParam("Composition item name") String compositionItemName);

  @ApiOperation(
      value = "Fetch the composition item by id",
      notes = "Fetch the composition item by id",
      response = CompositionItemDto.class,
      tags = {
        "composition item",
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = CompositionItemDto.class),
        @ApiResponse(code = 404, message = "Not found"),
      })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  Mono<CompositionItemDto> get(
      @ApiParam(value = "Fetch composition item by id", required = true) @PathVariable Long id)
      throws CompositionItemNotFoundException;

  @ApiOperation(
      value = "Update the composition item by id",
      notes = "Update the composition item by id",
      response = CompositionItemDto.class,
      tags = {
        "composition item",
      })
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "No content", response = CompositionItemDto.class),
        @ApiResponse(code = 404, message = "Not found"),
      })
  @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Mono<Void> update(
      @ApiParam(value = "Composition item id to update", required = true) @PathVariable Long id,
      @ApiParam(value = "Composition item", required = true) @RequestBody
          CompositionItemDto compositionItemDto)
      throws CompositionItemNotFoundException;

  @ApiOperation(
      value = "Delete the composition item by id",
      notes = "Delete the composition item by id",
      response = CompositionItemDto.class,
      tags = {
        "composition item",
      })
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "No content", response = CompositionItemDto.class),
        @ApiResponse(code = 404, message = "Malformed request the composition item not found"),
      })
  @DeleteMapping(value = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Mono<Void> delete(
      @ApiParam(value = "Composition item id to delete", required = true) @PathVariable Long id)
      throws CompositionItemNotFoundException;

  @ApiOperation(
      value = "Delete all the composition item",
      notes = "Delete all the composition item",
      response = CompositionItemDto.class,
      tags = {
        "composition item",
      })
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "No content", response = CompositionItemDto.class)
      })
  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  Mono<Void> deleteAll();
}
