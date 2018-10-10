package be.ordina.jworks.todoapi.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/todos", produces = "application/json")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") Long id) {
        Optional<Todo> todo = todoService.findById(id);

        if (!todo.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(todo.get());

    }

    @PostMapping()
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo savedTodo = todoService.save(todo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTodo.getId()).toUri();

        return ResponseEntity.created(location).body(savedTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @PathVariable Long id) {
        Optional<Todo> optionalTodo = todoService.findById(id);

        if (!optionalTodo.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        todo.setId(id);
        todo = todoService.save(todo);

        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable Long id) {
        todoService.delete(id);

        return ResponseEntity.ok().build();
    }
}
