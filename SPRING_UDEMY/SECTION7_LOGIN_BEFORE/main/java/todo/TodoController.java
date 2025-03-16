package todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;
@SessionAttributes("name")
@Controller
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // list-todos
    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model){
        String username = (String)model.get("name");
        List<Todo> todos = todoService.findByUsername("username");
        model.addAttribute("todos",todos);
        return "listTodos";
    }
    // 원래는 이건데.. RequestParam말고 다르게 주고싶으면 ModelMap을 추가해야함
//    @RequestMapping(value = "add-todo",method = RequestMethod.GET)
//    public String showNewTodoPage(){
//        return "todo";
//    }

    @RequestMapping(value = "add-todo",method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model){
        String username = (String)model.get("name");
        Todo todo = new Todo(0,username,"",LocalDate.now().plusYears(1),false);
        model.put("todo",todo);
        return "todo";
    }
//    @RequestMapping(value = "add-todo",method = RequestMethod.POST)
//    public String addNewTodo(@RequestParam String description,ModelMap model){
//        String username = (String)model.get("name");
//        todoService.addTodo(username,description, LocalDate.now().plusYears(1),false);
//        return "redirect:list-todos";
//    }

    // 다시 list_todos를 보여주고싶어 그러면
//    List<Todo> todos = todoService.findByUsername("heeyoung");
//        model.addAttribute("todos",todos);
    // 이내용이 있어야하니까 다시 작성하면 중복이돼..ㅠㅠ 그러면? redirect하는거야

    @RequestMapping(value = "add-todo",method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }

        String username = (String)model.get("name");
        todoService.addTodo(username,todo.getDescription(), todo.getTargetDate(),false);
        return "redirect:list-todos";
    }
    // requestParam(jsp에서 submit으로넘겨주는것) 안쓰고 해보기..
    // 왜냐하면 Description 말고도 만약 넘겨줄게 10개면 10개의 param을 다 넣어줘야하잖아?
    // 그냥 커맨드 빈 방식을 쓰자 (직접적으로 객체 연결시키기)
    // ModelMap에 바인딩하는 대신에,Todo에 직접 바인딩하기
    // 10문자 아래면 추가안되게하고싶음 :
    // Todo에 @Size 추가하고 여기에서는 @Valid를 사용 다만, 이렇게 하면 whieteLabel error 뜨니까..
    // BindingResult result와 코드를 추가하는 방식으로 해야된다.

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id){
        // id별로 삭제하고 list-todos로 리다이렉트 하자
        todoService.deleteById(id);

        return  "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo",method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id,ModelMap model){
        Todo todo = todoService.findById(id);
        model.addAttribute("todo",todo);
        // 참고로 todo.jsp에서 정한 이름과 매칭해야함
        return  "todo";
        // todo.jsp를 사용하려면 ModelMap model이 있어야함!
    }

    @RequestMapping(value = "update-todo",method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }

        String username = (String)model.get("name");
        todo.setUsername(username);
        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }

    private static String getLoggedinUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
