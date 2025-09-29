package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.ProfileType;
import promiseofblood.umpabackend.domain.vo.Role;

public record RegisterByLoginIdPasswordWithRoleRequest(
    @Schema(description = "사용자 닉네임", example = "홍길동") @NotBlank String username,
    @Schema(type = "enum", description = "가입하고자 하는 프로필 타입") @NotNull ProfileType profileType,
    @Schema(description = "가입하고자 하는 역할", example = "USER") Role role,
    @Schema(description = "로그인 아이디") @NotBlank String loginId,
    @Schema(description = "비밀번호") @NotBlank String password,
    @Schema(description = "성별", example = "MALE") Gender gender,
    @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
        MultipartFile profileImage) {}
