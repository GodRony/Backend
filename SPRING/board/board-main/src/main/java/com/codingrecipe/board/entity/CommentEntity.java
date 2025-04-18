package com.codingrecipe.board.entity;

import com.codingrecipe.board.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;
    // fetch는 연관 관계를 가져올 때 데이터를 어떻게 로딩할지를 결정하는 옵션입니다.
    //즉시 로딩(Eager Loading): 연관된 데이터를 즉시 가져옵니다.
    //지연 로딩(Lazy Loading): 연관된 데이터를 실제로 사용할 때 가져옴
    //연관된 엔티티를 즉시 가져오지 않고, 필요할 때 조회합니다.
    //예를 들어, 데이터베이스에서 연관된 엔티티를 사용할 때까지 SQL 쿼리가 실행되지않음.


    public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}
