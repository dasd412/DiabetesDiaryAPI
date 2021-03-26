package com.dasd412.controller.diabetesDiary;

import com.dasd412.controller.ApiResult;
import com.dasd412.controller.diet.DietRequestDTO;
import com.dasd412.controller.diet.DietResponseDTO;
import com.dasd412.domain.commons.Id;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diet.Diet;
import com.dasd412.domain.diet.EatTime;
import com.dasd412.service.diabetesDiaryForm.DiabetesDiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "혈당 일지 REST 컨트롤러")
public class DiabetesDiaryRestController {

    /*
    컨트롤러 계층에서만 DTO를 쓴다.
     */

  private final DiabetesDiaryService diaryService;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final int MAX_DIET = 3;

  public DiabetesDiaryRestController(DiabetesDiaryService diaryService) {
    this.diaryService = diaryService;
  }

  @PostMapping("/api/diabetes/diary/post")
  @ApiOperation(value = "혈당 일지 작성")
  public ApiResult<DiaryResponseDTO> postDiary(@RequestBody DiabetesDiaryRequestDTO diaryDTO) {
    logger.info("DiabetesDiaryRestController post dto : " + diaryDTO.toString());
    return ApiResult.OK(
        new DiaryResponseDTO(diaryService.save(diaryDTO.toEntity()))
    );
  }

  @PutMapping("/api/diabetes/diary/{id}")
  @ApiOperation(value = "혈당 일지 수정")
  public ApiResult<DiaryResponseDTO> update(
      @PathVariable @ApiParam(value = "혈당 일지 PK", example = "1") Long id,
      @RequestBody DiabetesDiaryUpdateRequestDTO dto) {
    logger.info("DiabetesDiaryRestController update dto : " + id + " " + dto.toString());
    return ApiResult.OK(
        new DiaryResponseDTO(diaryService.update(Id.of(DiabetesDiary.class, id)
            , dto.getFastingPlasmaGlucose(), dto.getBreakfastBloodSugar(), dto.getLunchBloodSugar(),
            dto.getDinnerBloodSugar(), dto.getRemark()))
    );
  }

  @DeleteMapping("/api/diabetes/diary/{id}")
  @ApiOperation(value = "혈당 일지 삭제")
  public ApiResult<Long> delete(
      @PathVariable @ApiParam(value = "혈당 일지 PK", example = "1") Long id) {
    logger.info("DiabetesDiaryRestController delete id : " + id);
    diaryService.delete(id);
    return ApiResult.OK(id);
  }

  @GetMapping("/api/diabetes/diary/list")
  @ApiOperation(value = "혈당 일지 리스트 조회")
  public ApiResult<List<DiaryListResponseDTO>> getDiaryList() {
    logger.info("DiabetesDiaryRestController get all list");
    List<DiabetesDiary> list = diaryService.getDiaryList();
    List<DiaryListResponseDTO> dtoList = new ArrayList<>();
    for (DiabetesDiary diary : list) {
      dtoList.add(new DiaryListResponseDTO(diary));
    }
    return ApiResult.OK(dtoList);
  }

  @GetMapping("/api/diabetes/diary/{id}")
  @ApiOperation(value = "혈당 일지 조회")
  public ApiResult<DiaryResponseDTO> findById(
      @PathVariable @ApiParam(value = "혈당 일지 PK", example = "1") Long id) {
    DiabetesDiary diary = diaryService.findById(Id.of(DiabetesDiary.class, id));
    logger.info("DiabetesDiaryRestController find  : " + diary.toString());
    return ApiResult.OK(new DiaryResponseDTO(diary));
  }

  @PostMapping("/api/diabetes/diary/diet/post")
  public ApiResult<DiaryResponseDTO> postDiaryWithDiet(@RequestBody DiabetesDiaryRequestDTO dto) {
    logger.info("DiabetesDiaryRestController post diary with diet : " + dto.toString());

    DiabetesDiary diary = diaryService.save(dto.toEntity());

    List<DietRequestDTO> dietRequestDTOList = dto.getDietRequestDTOList();
    List<Diet> dietList = new ArrayList<>(MAX_DIET);

    for (DietRequestDTO dietRequestDTO : dietRequestDTOList) {
      dietList.add(dietRequestDTO.toEntity());
    }

    List<Diet> saved = diaryService.saveWithTags(diary, dietList);
    List<DietResponseDTO> dietResponseDTOList = new ArrayList<>(MAX_DIET);

    for (Diet diet : saved) {
      dietResponseDTOList.add(new DietResponseDTO(diet));
    }

    return ApiResult.OK(new DiaryResponseDTO(diary, dietResponseDTOList));
  }

}
