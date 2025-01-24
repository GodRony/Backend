package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.entity.BoardEntity;
import com.codingrecipe.board.entity.BoardFileEntity;
import com.codingrecipe.board.repository.BoardFileRepository;
import com.codingrecipe.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// ####### DTO -> Entity (Entity Class에서 한다.) 변환 #######
// ####### Entity -> DTO (DTO Class에서 한다.) 변환 #######
// Controller -> Service : DTO로 받음.
// Service -> Controller : DTO로 넘겨줌
// Service -> Repository : entity로 넘겨줌.
// 조회같은걸할땐 Repository로부터 entity로 넘겨받는다.
// 암튼 서비스클래스에선 변환하는 과정이 많이 발생.
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository; // 이것도 생성자 주입방식
    private final BoardFileRepository boardFileRepository;

    // DTOP객체를 ENTITY로 옮겨담는다
    // INSERT 로직임. 이때 toSaveEntity는 아이디가 고려되지 않음 즉, INSERT다.
    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntity);
            // boardRepository의 save 메소드를  호출해야하는데,
            // save 메소드는 기본적으로 entity 클래스 타입으로 input을 받고 return 할때도 entity 타입으로 반환
            // 따라서 DTO -> Entity or Entity -> DTO로 변환해줘야함!
        } else {
            // 첨부 파일 있음.
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름을 만듦
                // 내사진.jpg => 839798375892_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */
            // ########## 멀티 파일 ###########
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO); // 멀티
            Long savedId = boardRepository.save(boardEntity).getId(); // 멀티
            BoardEntity board = boardRepository.findById(savedId).get();

            for(MultipartFile boardFile : boardDTO.getBoardFile()){
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = "C:/springboot_img/" + storedFileName;
                boardFile.transferTo(new File(savePath));
                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }

            // ############# 단일 파일 ############ //
/*            MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
            String originalFilename = boardFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
            String savePath = "C:/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
            boardFile.transferTo(new File(savePath)); // 5.
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            // boardRepository.save(boardEntity)로 저장을 해야만, DB에 Auto Increment되는 id가 생긴다.
            BoardEntity board = boardRepository.findById(savedId).get();
            // BoardEntity에 글을 먼저 저장하고( 글에 파일을 저장하는건 BoardFileEntity인데, 얘가 자식이니까 파일처리는 나중에함)
            // BoardFileEntity처리를 위해선 사전에 BoardEntity에 실제로 데이터가 저장이 되어있어야하며
            // 그 데이터를 기반으로 BoardFileEntity(자식)이 BoardEntity(부모)를 참조해야함
            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
            // 정적(static) 메소드는 객체를 생성하지 않고도 클래스이름.메소드 이렇게 호출할 수 있음.
            boardFileRepository.save(boardFileEntity);*/
        }

    }

    // ENTITY를 DTO객체로 옮겨담는다
    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    // 별도로 추가된 메서드를 쓰는 경우엔 Transactional를 붙임 (수동적 쿼리를 쓰는 경우)
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }
    // JPA가 INSERT와 UPDATE를 구별하는 방식
    // 아이디가 있냐 없냐에 따라서 구별함

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // 페이지 처리
    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // DB로 뽑을떄도 0부터 시작.
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // DB에서 page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        // PageRequest 자체가 페이지 번호와 페이지 크기를 기반으로 데이터를 요청하기 때문에
        // DB에서 page에 따라 pageLimit 만큼 갖고옴.
        // ex page = 1, id = 1,2,3 갖고옴.  page = 2, id = 4,5,6 이렇게 자동으로 갖고옴.
        // Page<BoardEntity> Page는 기본적으로 list임. 그래서 BoardEntity로 변환하는데
        // 리스트처럼 BoardEntity를 여러개를 담겠다 .
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map
                (board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(),
                        board.getBoardHits(), board.getCreatedTime()));
        // boardEntities.map에서 map은 뭐야?? 각각의 요소를 순차적으로 뽑아서 함수의내용() 을 실행하겠다
        // 그리고 그걸 다시 Page(list)로 담겠다.
        return boardDTOS;
    }
}













