package com.codingrecipe.board.controller;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.CommentDTO;
import com.codingrecipe.board.service.BoardService;
import com.codingrecipe.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
// mapping이 /board/save, /board/{id}.. 이런식으로 항상 상위에 board가 있다면 매번 쓰는거보다
// @RequestMapping("/board")을 써줘서 /board를 생략해주는게 좋다.
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    // 생성자 주입방식으로 의존성 주입.

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }
    // return으로 준것은 html임. 그래서 /borad/save로 매핑이 되면 save.html을 반환한다.

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }
    // save(@RequestParam("boardWriter") String boardWriter) 로 매개변수를 하나씩 줄 수 있지만,
    // 우리가 원하는건 save.html의 변수 세트기 때문에 이것을 편하게 하려면
    // dto 폴더 안에서 BoardDTO 클래스를 만들고 save(@ModelAttribute BoardDTO boardDTO) 로 매개변수를 받는게 좋다.

    @GetMapping("/")
    public String findAll(Model model) {
        // 전체 데이터를 DB목록에서 가져오는 부분이기 때메 매개변수를 Model model로 줌
        // **Model model**은 Spring에서 자동으로 주입되는 객체로,
        // 컨트롤러 메소드에서 데이터를 뷰로 전달합니다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        // Spring MVC에서 모델에 데이터를 추가
        // ######## Bean vs addAttribute #######
        // Bean : 애플리케이션의 전체 생애주기 동안 존재하며, 여러 클래스에서 공유하고 주입받을 수 있는 객체
        // addAttribute :  HTTP 요청-응답 주기 동안에만 데이터를 전달하는 역할을 하며,
        // 다른 컨트롤러나 클래스에서 사용할 수 없음
        // 뷰에 데이터를 전달하는 용도로 사용되고, 빈은 애플리케이션의 전역적 상태나 서비스 로직을 처리하는 용
        // boardDTOList 데이터를 "boardList"라는 이름으로 모델에 담아 뷰에 전달하는 역할을 합니다.
        return "list";
        // return값에 list.html을 줄거지만 기본적으로 list.html에 필요한 정보로 model을 줄것.
        // 뷰가 html임
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        // 여기서 페이지 번호를 넘겨줌.
        // 하지만 처음 board/ -> board/id -> "목록"을 누르면 처음에는 page = 1임.
        // 그러나 board/paging?page=2 -> board/id?page=2 -> "목록" 으로 가면 page = 2임.
        // page에 대한 정보가 있냐 없냐에 따라서 "목록"으로 갈 페이지가 달라짐.
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
//        return "redirect:/board/" + boardDTO.getId();
        // 리다이렉트 하는법임 .. 하지만 이럴경우 조회수가 또 올라가버려서 비추
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
//        pageable.getPageNumber();
        // pageable 변수가 있으면, /board/paging?page=2 이런식으로감
        // 없으면 /board/paging 이런식으로 1페이지로 감 (?page=1)
        // 요청 controller에서 매개변수는 ?매개변수이름=값 이런식으로 쿼리값이 됨.
        Page<BoardDTO> boardList = boardService.paging(pageable);
        //  boardService.paging(pageable);에서 알아서 페이지에 맞는 데이터를 DB에서 3개씩 갖고옴.
        // 하지만 boardlist는 0부터 시작임.
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        // pageable = 1일땐 startPage = 1, ceil은 무조건 올림임.
        // 1/3 -> 0.333 -> 올림하면 1임. 그래서 1 - 1 = 0 그리고 0*3 = 0 그리고 0+1하면 1

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        // boardList로 넘겨준것은 0부터 시작임
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";

    }

}


/*
        ######## Entity VS DTO 왜 나뉘며 어떤상황에서 무엇을 써야하는지? #########

1. Entity : 데이터베이스와 밀접하게 연결되어 있어 DB 작업에 사용됩니다.
            또 Entity는 데이터베이스 테이블에 직접 매핑되는 객체임.

2. DTO : 클라이언트로 데이터를 전송할 때 필요한 데이터만 포함하여 전달하는 객체입니다.
         애플리케이션 내에서 데이터를 전송하는 객체임.  일반적으로 Entity를 클라이언트로 전달할 때 사용함

                                    <   결론   >
    Entity는 서버 측에서만 사용하고, 클라이언트로 보내는 데이터는 DTO로 변환하여 전송하는게 일반적임.
    자바스크립트에서는 DTO 객체만 사용하여 필요한 정보만 클라이언트에 전달하는 것이 더 효율적이고 안전

*/









