package com.codingrecipe.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    // BoardFileEntity를 기준으로.. 파일은 여러개 글은 하나니까.
    // 파일 입장에서 보면 Many(파일) to One(글)
    @JoinColumn(name = "board_id") // 실제 만들어지는 컬럼 이름을 정한다. (JoinColumn은 자식에서 쓰는거임)
    private BoardEntity boardEntity; // 이때에는 부모 Entity로 해줘야함

    public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileName, String storedFileName) {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        boardFileEntity.setBoardEntity(boardEntity);
        return boardFileEntity;
    }

    // 정적(static) 메소드는 객체를 생성하지 않고도 클래스이름.메소드 이렇게 호출할 수 있음.
    // static은 클래스의 멤버가 인스턴스가 아닌 클래스 자체에 속하도록 만드는 키워드입니다.
}













