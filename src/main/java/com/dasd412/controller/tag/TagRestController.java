package com.dasd412.controller.tag;

import com.dasd412.controller.ApiResult;
import com.dasd412.service.tag.DietTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagRestController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final DietTagService dietTagService;

  public TagRestController(DietTagService dietTagService) {
    this.dietTagService = dietTagService;
  }

  @PostMapping("/api/diabetes/tag/post")
  public ApiResult<TagResponseDTO> postTag(@RequestBody TagRequestDTO dto) {

    boolean saveSuccess = dietTagService.save(dto.getFoodName());

    logger.info("TagRestController post tag dto : " + dto.toString());
    logger.info("is Tag not duplicated ? : " + saveSuccess);

    return ApiResult.OK(new TagResponseDTO(dto.getFoodName(), saveSuccess));
  }
}
