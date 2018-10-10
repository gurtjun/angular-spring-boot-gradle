import { Component, OnInit } from '@angular/core';
import { TodoService } from './todo.service';
import { Todo } from './todo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [TodoService]
})
export class AppComponent implements OnInit {
  todos: Todo[] = [];
  newTodo: Todo = new Todo();

  constructor(private todoService: TodoService) {

  }

  ngOnInit(): void {
    this.todoService
      .getAllTodos()
      .subscribe(
        (todos) => {
          this.todos = todos;
        }
      );
  }

  addTodo(todo) {
    this.todoService
      .addTodo(todo)
      .subscribe(
        (newTodo) => {
          this.todos = this.todos.concat(newTodo);
          this.newTodo = new Todo();
        }
      );
  }

  toggleTodoComplete(todo) {
    this.todoService
      .toggleTodoComplete(todo)
      .subscribe(
        (updatedTodo) => {
          todo = updatedTodo;
        }
      );
  }

  removeTodo(todo) {
    this.todoService
      .deleteTodoById(todo.id)
      .subscribe(
        (_) => {
          this.todos = this.todos.filter((t) => t.id !== todo.id);
        }
      );
  }
}
