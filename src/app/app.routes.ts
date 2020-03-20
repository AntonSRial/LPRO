import { RouterModule, Routes } from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {CalendarComponent} from "./calendar/calendar.component";
import {ResultComponent} from "./result/result.component";
import {SinginComponent} from "./singin/singin.component";
import {UserComponent} from "./user/user.component";
import {PatientsComponent} from "./patients/patients.component";

const app_routes: Routes = [
    {path: 'home', component: HomeComponent},
    {path: 'calendar', component: CalendarComponent},
    {path: 'results', component: ResultComponent},
    {path: 'singin', component: SinginComponent},
    {path: 'user', component: UserComponent},
    {path: 'patients', component: PatientsComponent},
    {path: '**', pathMatch:'full',redirectTo:'home'} //Esto hace que cualquier ruta desconocida sea redirigida a /home
];

export const app_routing = RouterModule.forRoot(app_routes);


