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
import { TestComponent } from './test/test.component';

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
registerLocaleData(localeEs);

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
    PatientsComponent,
    TestComponent,
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
