import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Results } from '../model/results';
// @ts-ignore
import { Observable } from 'rxjs/Observable';
import {User} from "../model/user";

@Injectable()
export class ResultsService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/results';
  }
/*
  public obtenerResultados(id:string): Observable<Results[]> {
    return this.http.get<Results[]>(this.usersUrl,id);
  }

  public obtenerResultadosGeneral(): Observable<Results[]> {
    return this.http.get<Results[]>(this.usersUrl);
  }

  public getUserById(id: string) {
    return this.http.get<User>(this.usersUrl);
  }

*/






}
