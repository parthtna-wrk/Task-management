import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { map } from 'rxjs/operators';
import { IUser } from 'src/app/interface/task.interface';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpclient: HttpClient) { }

  getUserDetails(): Observable<Array<IUser>> {
    const token = localStorage.getItem('token');
    return this.httpclient
      .get('http://localhost:8080/api/v1/userDetails', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(map((d: Object) => d as Array<IUser>));
  }
}
