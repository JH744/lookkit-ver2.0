package synerjs.lookkit2nd.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.common.dto.InquiryUserDTO;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
    List<Inquiry> findByUserIdOrderByInquiryCreatedAtDesc(Long userId);





    @Query(value = "SELECT i.inquiry_id AS inquiryId, " +
            "u.user_uuid AS userUuid, " +
            "i.inquiry_title AS inquiryTitle, " +
            "i.inquiry_contents AS inquiryContents, " +
            "i.inquiry_created_at AS inquiryCreatedAt, " +
            "i.answer_state AS answerState " +
            "FROM inquiries i " +
            "JOIN users u ON i.user_id = u.user_id " +
            "ORDER BY i.inquiry_created_at DESC",
            nativeQuery = true)
    List<Object[]> getAllInquiriesWithUserUuid();

}
