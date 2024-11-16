package synerjs.lookkit2nd.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.inquiry.entity.InquiryImage;

import java.util.List;

@Repository
public interface InquiryImageRepository extends JpaRepository<InquiryImage,Long> {
    @Query("SELECT img FROM InquiryImage img WHERE img.inquiryId = :inquiryId")
    List<InquiryImage> findInquiryByIdWithImage(@Param("inquiryId")Long inquiryId);
}
