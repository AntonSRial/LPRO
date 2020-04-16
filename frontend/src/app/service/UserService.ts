import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
// @ts-ignore
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

  private usersUrl: string;
  private usersAllUrl: string;
  private userDeleteUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/users';
    this.usersAllUrl = 'http://localhost:8080/usersAll';
    this.userDeleteUrl = 'http://localhost:8080/usersDelete';
  }

  public insertUser(user: User) {
    this.http.post<User>(this.usersUrl, user);
  }

  public modifyUser(user: User) {
    this.http.put<User>(this.usersUrl, user);
  }

  public eliminateUser(user: User) {
    this.http.post<User>(this.userDeleteUrl,user.id);
  }

  public getUserById(user: User) {
    this.http.get<User>(this.usersUrl+'/${user.id}');
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersAllUrl);
  }








}
