package synerjs.lookkit2nd.common.util;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService {
    private final Storage storage;

    public FirebaseStorageService() {
        // StorageOptions를 사용하여 Storage 객체를 가져옵니다.
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        // Firebase 프로젝트 ID나 버킷 이름을 명확히 지정해야 합니다.
        BlobId blobId = BlobId.of("kosa-1d926", filename); // 버킷 이름을 확인하세요.
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        return String.format("https://storage.googleapis.com/%s/%s", blobId.getBucket(), blobId.getName());
    }
}
