package todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<>();

    private static int todosCount = 0;

    static {
        todos.add(new Todo(++todosCount,"heeyoung", "she is cute 1",
                LocalDate.now().plusYears(1),false ));
        todos.add(new Todo(++todosCount,"heeyoung", "she is cute 2",
                LocalDate.now().plusYears(2),false ));
        todos.add(new Todo(++todosCount,"heeyoung", "she is cute 3",
                LocalDate.now().plusYears(3),false ));
    }
    // static으로 추가하는 방법은 이렇고.. 왜 굳이 static으로 했을까?
    // 그리고 static으로 하면 서버가 재실행될때마다 초기화되고 날아감. 그래서 MySQL에 저장할거임
    public List<Todo> findByUsername(String username){
        Predicate<? super Todo> predicate =
                todo->todo.getUsername().equalsIgnoreCase(username);
        return todos.stream().filter(predicate).toList();
    }
    public void addTodo(String username,String description,LocalDate targetDate, boolean done){
        Todo todo = new Todo(++todosCount,username,description,targetDate,done);
        todos.add(todo);
    }

    public void deleteById(int id){
        // Predicate 언제 todo를 삭제할건지 WHEN에 해당 : todo.getId() == id
        // 람다 사용하기 todo -> todo.getId() == id
        Predicate<? super Todo> predicate
                =todo -> todo.getId() == id;
        todos.removeIf(predicate);

    }

    public Todo findById(int id) {
        Predicate<? super Todo> predicate
                =todo -> todo.getId() == id;
        Todo todo = todos.stream().filter(predicate).findFirst().get();
        // private static List<Todo> todos = new ArrayList<>(); 여기에 담겨져있는 todos에서
        // id와 매칭되는 todo를 찾고싶음.
        // 그래서 for 반복문을 써서 Todo를 반복하고 올바른것을 찾아서 되돌려줄 수도 있고,
        // 아니면 함수형프로그래밍 stream을 쓸 수 있음 (나중에 공부할것)

        return todo;
    }

    public void updateTodo(@Valid Todo todo) {
        deleteById(todo.getId());
        todos.add(todo);
    }


}
