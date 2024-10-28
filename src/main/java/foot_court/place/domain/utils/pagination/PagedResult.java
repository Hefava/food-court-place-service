package foot_court.place.domain.utils.pagination;

import java.util.List;
import java.util.Objects;

public class PagedResult<T> {
    private final List<T> content;
    private final int page;
    private final int pageSize;
    private final int totalPages;
    private final long totalCount;

    public PagedResult(List<T> content, int page, int pageSize, int totalPages, long totalCount) {
        this.content = content;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagedResult<?> that = (PagedResult<?>) o;
        return page == that.page &&
                pageSize == that.pageSize &&
                totalPages == that.totalPages &&
                totalCount == that.totalCount &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, page, pageSize, totalPages, totalCount);
    }
}