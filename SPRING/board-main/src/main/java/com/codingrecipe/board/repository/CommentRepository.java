package com.codingrecipe.board.repository;

import com.codingrecipe.board.entity.BoardEntity;
import com.codingrecipe.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
    // 쿼리를 대소문자 그대로 잘 지켜줘야한다.
    // findAllByBoardEntity = select * from comment_table
    // BoardEntity boardEntity = where board_id
    // OrderByIdDesc = order by id desc
}
