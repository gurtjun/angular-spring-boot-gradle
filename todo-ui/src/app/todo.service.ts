import { Injectable } from '@angular/core';
import { Todo } from './todo';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  constructor(private http: HttpClient) {
  }

  getAllTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>('/api/todos', httpOptions).pipe(
      tap(todos => console.log(todos)),
      catchError(this.handleError([]))
    );
  }

  getTodoById(id: number): Observable<Todo> {
    return this.http.get<Todo>('/api/todos/' + id, httpOptions).pipe(
      tap(todo => console.log(todo)),
      catchError(this.handleError<Todo>())
    );
  }

  addTodo(todo: Todo): Observable<Todo> {
    return this.http.post<Todo>('/api/todos', todo, httpOptions).pipe(
      tap(todo => console.log(todo)),
      catchError(this.handleError<Todo>())
    );
  }

  deleteTodoById(id: number): Observable<null> {
    return this.http.delete<null>('/api/todos/' + id, httpOptions).pipe(
      catchError(this.handleError<null>())
    );
  }

  updateTodo(todo: Todo): Observable<Todo> {
    return this.http.put<Todo>('/api/todos/' + todo.id, todo, httpOptions).pipe(
      tap(todo => console.log(todo)),
      catchError(this.handleError<Todo>())
    );
  }

  toggleTodoComplete(todo: Todo) {
    todo.done = !todo.done;
    return this.updateTodo(todo);
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
