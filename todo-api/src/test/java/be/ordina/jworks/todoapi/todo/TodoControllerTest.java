package be.ordina.jworks.todoapi.todo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoController todoController;

    @Test
    public void shouldFindAllTodos() throws Exception {
        Todo todo = new Todo("Fix test", true);
        List<Todo> allTodos = Collections.singletonList(todo);

        given(todoController.getAllTodos()).willReturn(ResponseEntity.ok(allTodos));

        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldFindTodoById() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setText("Fix test");
        todo.setDone(true);

        given(todoController.getTodoById(todo.getId())).willReturn(ResponseEntity.ok(todo));

        mockMvc.perform(get("/api/todos/" + todo.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("text", is(todo.getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("done", is(todo.isDone())));
    }
}