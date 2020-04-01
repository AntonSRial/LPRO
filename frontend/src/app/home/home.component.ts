import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../service/UserService';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  users: User[];
  fechaNacimiento: String;
  nombre: String;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
      console.log("Esta es la respuesta del back: El nombre de la paciente con id 1 es: "+this.users[0].name
        + " y su fecha de nacimiento es: "+ this.users[0].fechaNacimiento);
    });
    this.nombre = this.users[0].name;
    this.fechaNacimiento = this.users[0].fechaNacimiento;
  }


}
