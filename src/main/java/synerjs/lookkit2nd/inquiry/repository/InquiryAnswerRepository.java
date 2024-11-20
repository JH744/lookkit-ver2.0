package synerjs.lookkit2nd.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import synerjs.lookkit2nd.inquiry.entity.InquiryAnswer;
import synerjs.lookkit2nd.inquiry.entity.InquiryImage;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer,Long> {


    // 문의 답변 데이터 삽입
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO inquiries_answer (inquiry_id, answer_contents, answer_created_at) " +
            "VALUES (:inquiryId, :answerContents, CURRENT_TIMESTAMP)", nativeQuery = true)
    int insertAnswer(Long inquiryId, String answerContents);


    Optional<InquiryAnswer> findByInquiry_InquiryId(Long inquiryId);
}
