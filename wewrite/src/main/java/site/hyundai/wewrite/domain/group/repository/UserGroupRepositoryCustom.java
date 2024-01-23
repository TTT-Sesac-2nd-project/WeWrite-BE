package site.hyundai.wewrite.domain.group.repository;

import site.hyundai.wewrite.domain.entity.UserGroup;

import java.util.List;

public interface UserGroupRepositoryCustom {

    List<UserGroup> getUserGroupsById(String userId);
}
