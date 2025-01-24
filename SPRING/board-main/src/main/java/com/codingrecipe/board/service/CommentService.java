package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.CommentDTO;
import com.codingrecipe.board.entity.BoardEntity;
import com.codingrecipe.board.entity.CommentEntity;
import com.codingrecipe.board.repository.BoardRepository;
import com.codingrecipe.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        // boardEntity를 조회하는 부분
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        // ########### comment를 기준으로 BoardEntity(DB)를 조회하고싶을때, ###########
        // 1)) select * from comment_table where board_id=? order by id desc;
        // 이거라서 comment에 해당하는 boardID만 알고있으면 될것같지만.. JPA는 엔터티로 조회해야해서 엔터티가 필요함.
        // JPA에서는 **엔티티(Entity)**를 기준으로 데이터베이스를 조회하니까!
        // 2)) 연관 관계 매핑
        //BoardEntity와 CommentEntity는 보통 연관 관계(예: @ManyToOne, @OneToMany 등)로 매핑됩니다.
        // 이 연관 관계를 활용하여 BoardEntity 객체를 통해 그에 속한 CommentEntity들을 조회하는 것이기 때문에,
        // 단순히 boardId만 전달하는 방식은 적합하지 않음.

        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

}
