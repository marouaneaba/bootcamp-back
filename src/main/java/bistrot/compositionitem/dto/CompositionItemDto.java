package bistrot.compositionitem.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompositionItemDto implements Serializable {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @JsonProperty("id")
    private Long id;


    @NotBlank(message = "The name is blank")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(hidden = true)
    @JsonProperty("price")
    private Double price;

    @ApiModelProperty(hidden = true)
    @JsonProperty("recipe")
    private String recipe;

    @ApiModelProperty(hidden = true)
    @JsonProperty("quantity")
    private Integer quantity;
}
