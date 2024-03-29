package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.entity.GroupStatus;
import com.gmail.dlwk0807.dagachi.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupSaveRequestDTO {
    @Schema(description = "카테고리관리번호", nullable = true, type = "List", example = "[1, 2, 3]")
    private List<Long> categoryIds;
    @Schema(description = "모임명", nullable = true, example = "이것은 모임명")
    private String name;
    @Schema(description = "모임설명", nullable = false, example = "모임 설명란")
    private String description;
    @Schema(description = "시작일시", nullable = true, example = "202401181900")
    @NotBlank(message = "시작일시는 필수입니다.")
    @Pattern(regexp = "\\d{12}", message = "시작일자는 12자리 숫자입니다.")
    private String strStartDateTime;
    @Schema(description = "카테고리", nullable = true, example = "202401182200")
    @NotBlank(message = "종료일시는 필수입니다.")
    @Pattern(regexp = "\\d{12}", message = "시작일자는 12자리 숫자입니다.")
    private String strEndDateTime;
    @Schema(description = "최대인원", nullable = false, example = "7")
    private Long groupMaxNum;

    public Group toGroup(Member member) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime startDateTime = LocalDateTime.parse(strStartDateTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(strEndDateTime, formatter);

        return Group.builder()
                .name(name)
                .memberId(member.getId())
                .groupImage("https://storage.googleapis.com/dagachi-image-bucket/default/group_default.png")
                .description(description)
                .status(GroupStatus.WAITING)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .groupMaxNum(groupMaxNum)
                .company(member.getCompany())
                .build();
    }

}
