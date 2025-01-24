package com.codingrecipe.board.controller;

import com.codingrecipe.board.dto.CommentDTO;
import com.codingrecipe.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO = " + commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
            // detail.html에서의 success: function (res)의 res 부분은
            // return으로 준 new ResponseEntity<>(commentDTOList, HttpStatus.OK); 내용임.
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
    // ajax로 하면 컨트롤러에서 detail.html을 다시 리턴하지않고, return으로 보내준 내용을 바탕으로
    // 부분적으로 동적 갱신을 함 (그 내용은 success : fuction()임)
}

// ########### 궁금했던 점##########
// 1.왜 함수 매개변수를 save(@ModelAttribute CommentDTO commentDTO)로 주는거지?
// detail.html에선 data{}로 save에게 요청해서 보내는건데,
//data: {
//        "commentWriter": writer, // 변수로 지정해 데이터를 보낼 수 있음.
//        "commentContents": contents,
//        "boardId": id
//           }
// 2. ModelAttribute은 뭐고 왜 CommentDTO로 주는걸까 ? commentWriter, commentContets , boardId로 주는게 아니라

// ########### 이유 ##############
// 1. @ModelAttribute란?
// @ModelAttribute는 HTTP 요청 파라미터를 객체에 매핑하는 역할을 합니다.
// 이 어노테이션은 주로 폼 데이터(form data) 또는 **쿼리 파라미터(query parameters)**를 DTO와 같은 객체에 매핑할 때 사용됩니다.
// 즉, HTML 폼에서 name 속성에 해당하는 필드명과 컨트롤러의 매개변수 이름이 같으면 자동으로 그 값을 매핑하여 객체에 할당해줍니다
// 2. data 객체는 어떻게 매핑되는가?
//data는 $.ajax로 전송되는 쿼리 파라미터입니다. commentWriter, commentContents, boardId는
// name 속성과 일치하는 매개변수로 전달되기 때문에,
// Spring 컨트롤러에서 @ModelAttribute를 사용하여 자동으로 CommentDTO 객체에 매핑됩니다.
//commentWriter → commentDTO.setCommentWriter(writer)
//commentContents → commentDTO.setCommentContents(contents)
//boardId → commentDTO.setBoardId(id)

// 이 방식은 detail.html(AJAX를 쓰는 방식) -> CommentController의 save(action)와 마찬가지로
// save.html(AJAX를 안쓰는 방식) -> BoardController의 save(action)에도 적용이된다.
// 이때도 save.html의 writer, pass, pass.. 등등이 BoardController의 save(action)에 data{}처럼
// 보내지지만, 그 전에 BoardDTO 객체로 매핑되어 각각이 BoardDTO의 멤버변수로 자동으로 알아서 매핑됨.