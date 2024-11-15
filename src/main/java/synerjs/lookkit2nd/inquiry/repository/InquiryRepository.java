package synerjs.lookkit2nd.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
    List<Inquiry> findByUserIdOrderByInquiryCreatedAtDesc(Long userId);
}
