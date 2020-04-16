import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/UserService";
import {User} from "../model/user";
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  private usuario: User;
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.usuario = new User();
    this.usuario.setId("74682540T");
    this.usuario.setName("Patricia González");
    console.log(this.userService.getUserById(this.usuario));
  }


}
