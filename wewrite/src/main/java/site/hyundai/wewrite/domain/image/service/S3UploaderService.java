package site.hyundai.wewrite.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.hyundai.wewrite.domain.entity.Image;
import site.hyundai.wewrite.domain.image.repository.ImageRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 이소민
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploaderService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3 amazonS3Client;
    private final ImageRepository imageRepository;

    public List<Image> uploadFiles(String directory, List<MultipartFile> multipartFiles) {
        List<Image> uploadedImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                String keyName = directory + "/" + uploadFileName;
                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicReadWrite));
                uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("File upload failed", e);
                continue; // 실패한 파일은 건너뛰고 계속 처리합니다.
            }

            Image image = Image.builder()
                    .originalFileName(originalFileName)
                    .uploadFileName(uploadFileName)
                    .uploadFilePath(directory)
                    .uploadFileUrl(uploadFileUrl)
                    .build();

            imageRepository.save(image);
            uploadedImages.add(image);
        }

        return uploadedImages;
    }

    // 파일 이름의 중복을 막기위해 uuid 사용
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }

}