import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { HomeComponent } from './home/home.component';
import { CalendarComponent } from './calendar/calendar.component';
import { ResultComponent } from './result/result.component';

//Routes
import {app_routing} from "./app_routes";
import { SinginComponent } from './singin/singin.component';
import { UserComponent } from './user/user.component';
import { PatientsComponent } from './patients/patients.component';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FlatpickrModule } from 'angularx-flatpickr';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

import { registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es';
import { EditUserComponent } from './edit-user/edit-user.component';
import { EditHomeComponent } from './edit-home/edit-home.component';
import {UserService} from "./service/UserService";
registerLocaleData(localeEs);

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    HomeComponent,
    CalendarComponent,
    ResultComponent,
    SinginComponent,
    UserComponent,
    PatientsComponent,
    EditUserComponent,
    EditHomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    FormsModule,
    NgbModalModule,
    FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    app_routing
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
