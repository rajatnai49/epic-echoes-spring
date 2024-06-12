package com.epic_echoes.epic_echoes.repositories;


import com.epic_echoes.epic_echoes.entities.Storybook;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.helpers.RefreshableCRUDRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, UUID> {

    UserInfo findByUsername(String username);

    UserInfo findFirstById(UUID id);

    @Query("SELECT u FROM UserInfo u WHERE :storybookId IN (SELECT p.storybook.id FROM StorybookUserPermission p WHERE p.user.id = u.id AND p.storybook.privacy = :privacy) OR :storybookId IN (SELECT p.storybook.id FROM StorybookUserPermission p WHERE p.user.id = u.id AND (p.storybook.privacy = 'EVERYONE_VIEW' OR p.storybook.privacy = 'EVERYONE_EDIT'))")
    List<UserInfo> findUsersByStorybookAndPrivacy(@Param("storybookId") UUID storybookId, @Param("privacy") Storybook.Privacy privacy);

    @Query("SELECT u FROM UserInfo u WHERE :storybookId IN (SELECT p.storybook.id FROM StorybookUserPermission p WHERE p.user.id = u.id AND (p.storybook.privacy = 'EVERYONE_VIEW' OR p.storybook.privacy = 'EVERYONE_EDIT'))")
    List<UserInfo> findUsersByStorybook(@Param("storybookId") UUID storybookId);
}