package site.hyundai.wewrite.domain.bookmark.repository;

public interface BookmarkRepositoryCustom {
    boolean isBookmarked(String userId, Long boardId);
    void deleteByBoardId(Long boardId);
}
