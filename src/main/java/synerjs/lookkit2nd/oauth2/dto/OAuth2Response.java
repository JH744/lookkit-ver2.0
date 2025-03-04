package synerjs.lookkit2nd.oauth2.dto;

import java.time.LocalDate;

public interface OAuth2Response {
    //제공자 (kakao,google,naver)
    String getProvider();
    //제공자에서 발급해주는 아이디(번호)
    String getProviderId();
    // 유저아이디
    String getUserUuid();
    // 성별
    String getGender();
    // 생년월일
    LocalDate getBirthDate();
    // 전화번호
    String getPhone();
    //이메일
    String getEmail();
    //사용자 실명 (설정한 이름)
    String getName();
}
