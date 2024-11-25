package synerjs.lookkit2nd.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import synerjs.lookkit2nd.common.dto.InquiryUserDTO;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;
import synerjs.lookkit2nd.inquiry.entity.InquiryAnswer;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
    List<Inquiry> findByUserIdOrderByInquiryCreatedAtDesc(Long userId);




 // 모든 문의 가져오기 *유저Pkid 아닌 유저아이디로 가져옴
    @Query(value = "SELECT i.inquiry_id AS inquiryId, " +
            "u.user_uuid AS userUuid, " +
            "i.inquiry_title AS inquiryTitle, " +
            "i.inquiry_contents AS inquiryContents, " +
            "i.inquiry_created_at AS inquiryCreatedAt, " +
            "i.answer_state AS answerState " +
            "FROM inquiries i " +
            "JOIN users u ON i.user_id = u.user_id " +
            "ORDER BY i.inquiry_id DESC",
            nativeQuery = true)
    List<Object[]> getAllInquiriesWithUserUuid();


// 모든 문의 목록 가져오기 * Uid->UUid
   @Query(value = "SELECT i.INQUIRY_ID AS inquiryId, " +
           "u.USER_UUID AS userUuid, " +
           "i.INQUIRY_TITLE AS inquiryTitle, " +
           "i.INQUIRY_CONTENTS AS inquiryContents, " +
           "i.INQUIRY_CREATED_AT AS inquiryCreatedAt, " +
           "i.ANSWER_STATE AS answerState " +
           "FROM inquiries i " +
           "JOIN users u ON i.USER_ID = u.USER_ID " +
           "WHERE i.INQUIRY_ID = :inquiryId",
           nativeQuery = true)
   Optional<Object[]> findInquiryDetailsById(@Param("inquiryId") Long inquiryId);


    // 문의 상태를 답변 완료로 업데이트
    @Transactional
    @Modifying
    @Query("UPDATE Inquiry i SET i.answerState = 'Y' WHERE i.inquiryId = :inquiryId")
    int updateAnswerStateToCompleted(Long inquiryId);



}
