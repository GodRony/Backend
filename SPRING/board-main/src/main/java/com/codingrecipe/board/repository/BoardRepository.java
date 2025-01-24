package com.codingrecipe.board.repository;

import com.codingrecipe.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Repository는 기본적으로 entity 클래스만 받아준다.
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    // 업데이트를 할때에는 Modifying를 붙여줘야함
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:_id")
    // nativeQuery = true 옵션을 주면 실제 DB에서 사용되는 쿼리를 쓸 수 있음.
    // 근데 지금은 엔터티를 기준으로 쓸것이기 때문에 BoardEntity처럼 엔터티 이름이 옴.
    // 또 엔터티의 정의한 컬럼이 옴.
    void updateHits(@Param("_id") Long id);
}

// ########## 비지니스 로직 VS 데이터 액세스 분리 ##########
// 1. 비지니스 로직
// Service 계층에서 처리됨
// 애플리케이션의 핵심 기능, ex 게시글 작성, 조회수 업데이트 등
// 비지니스 로직은 DB와 직접적인 상호작용 처리 X
// 2. 데이터 액세스
// Repository 계층에서 처리됨
// DB와 상호작용 하는 부분임. 데이터의 CRUD 담당 ex. 데이터 조회 저장 삭제 등

// ########## JpaRepository란 ##########
// Repository는 기본적으로 JpaRepository의 상속을 받는데,
// 기본적인 CRUD 메소드를 제공함 save(),findAll(),findById(),delete(),deleteById()등
// 데이터베이스에 대한 기본적인 작업을 자동으로 처리해 주므로, 코드 작성이 매우 간단해짐

// ########## Service findAll() vs Repository findAll() 차이 ##########
//  서비스의 findAll이 리포지스토리의 findAll 내용이 이미 포함되어있되 추가적으로 작성한것





/*

JpaRepository 얘가 다루는건 엔터티가 아니라 DB에 실제로 저장된 데이터들임
하지만, DB에 저장된 데이터를 엔터티로도 만들어줘야하는데 이 기능도 수행을 함.
그래서 기본적으로 이미 내장된 findById 같은 메서드를 제공하여 DB에서 데이터를 조회함.
이때, DB에서 가져온 데이터는 BoardEntity 객체로 변환되어 반환됨.

Ex.
1. boardRepository.findById(id)는 데이터베이스에서 id에 해당하는 데이터를 찾아 BoardEntity 객체로 변환하여 반환
2. JpaRepository는 DB에 저장된 데이터를 엔티티 객체로 변환하고, 엔티티 객체를 DB에 저장하는 기능을 제공합니다.
예를 들어:
DB에서 데이터를 조회하면 JpaRepository는 해당 데이터를 BoardEntity 객체로 변환하여 반환합니다.
엔티티를 저장하려면, JpaRepository는 BoardEntity 객체를 받아서 이를 DB에 저장합니다.

아무튼!
BoardEntity는 테이블의 각 열(row)과 1:1로 대응되는 자바 클래스임.
그래서 BoardEntity이 테이블로써 따로 존재하는 줄 알았는데 이건 틀려.
BoardEntity는 1:1로 매핑되는 객체 그 자체를 말하는거니까

* */






