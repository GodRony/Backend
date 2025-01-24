package com.codingrecipe.board.entity;

import com.codingrecipe.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Entity는 DB 테이블 관리해주는거라고 보면댐..
// DB의 테이블 역할을 하는 클래스
@Entity // 이걸 붙여줘야함.
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    // BaseEntity 상속받기 때메, createdTime, updatedTime를 사용할 수 있음.
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>(); // 글 하나에 여러개가 올 수 잇으니..
    // boardEntity를 기준으로.. 글은 한개 하지만 파일은 여러개니까
    // 글 입장에서 보면 One(글) to Many(파일)
    // 자식에서 만든  private BoardEntity boardEntity; 변수가 mappedBy에 매핑되는거임

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    // DTO -> ENTITY로 변환해서 보내줌
    // DTO에 담긴 값들을 ENTITY 객체로 옮겨담는 과정
    // 아이디가 고려되지 않음. 즉 INSERT다 하고 JPA가 알아차림.
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0); // 파일 없음.
        return boardEntity;
    }
    // 아이디가 고려됨. 즉 UPDATE다 하고 JPA가 알아차림.
    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId()); // 아이디 고려
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); // 파일 있음.
        return boardEntity;
    }
}

/*

######### 왜 BoardEntity에서 findById를 정의하지 않는가? #########
BoardEntity는 단순히 데이터베이스 테이블에 대응하는 엔티티 클래스로,
데이터의 상태를 저장하는 역할을 합니다
따라서

* */









