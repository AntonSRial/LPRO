import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HolaMundo } from './componente test/component.test';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { HomeComponent } from './home/home.component';
import { CalendarComponent } from './calendar/calendar.component';
import { ResultComponent } from './result/result.component';

//Routes
import {app_routing} from "./app.routes";
import { SinginComponent } from './singin/singin.component';
import { UserComponent } from './user/user.component';
import { PatientsComponent } from './patients/patients.component';


@NgModule({
  declarations: [
    AppComponent,
    HolaMundo,
    ToolbarComponent,
    HomeComponent,
    CalendarComponent,
    ResultComponent,
    SinginComponent,
    UserComponent,
    PatientsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    app_routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
