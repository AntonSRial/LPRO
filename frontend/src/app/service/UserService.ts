import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
// @ts-ignore
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/users';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public insertUser(user: User) {
    this.http.post<User>(this.usersUrl, user);
  }

  public eliminateUser(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }




}
