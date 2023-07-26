import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import {
  ITask,
  ITaskTypeOption,
  ITypePercentage,
} from '../../interface/task.interface';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  constructor(private httpclient: HttpClient) {}

  getTaskList(): Observable<Array<ITask>> {
    const token = localStorage.getItem('token');
    return this.httpclient
      .get('http://localhost:8080/api/v1/task', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(map((d: Object) => d as Array<ITask>));
  }
  postTaskList(task: ITask): Observable<ITask> {
    const token = localStorage.getItem('token');
    return this.httpclient
      .post('http://localhost:8080/api/v1/task', task, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(map((d: Object) => d as ITask) );
  }

  updateTask(task: ITask, id: string): Observable<ITask> {
    const token = localStorage.getItem('token');
    return this.httpclient
      .put(`http://localhost:8080/api/v1/task/${id}`, task, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(map((d: Object) => d as ITask));
  }
  deleteTask(id: string) {
    const token = localStorage.getItem('token');
    return this.httpclient.delete(`http://localhost:8080/api/v1/task/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  getTaskById(id: string): Observable<ITask> {
    const token = localStorage.getItem('token');
    return this.httpclient
      .get(`http://localhost:8080/api/v1/task/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(map((d: Object) => d as ITask));
  }

  getTypePercentage(): Observable<Array<ITypePercentage>> {
    const token = localStorage.getItem('token');
    return this.httpclient
      .get(`http://localhost:8080/api/v1/task/vData/percentcounttype`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(map((d: Object) => d as Array<ITypePercentage>));
  }

  getTypeOptions(): Array<ITaskTypeOption> {
    return [{ type: 'done' }, { type: 'todo' }, { type: 'pending' }];
  }
}