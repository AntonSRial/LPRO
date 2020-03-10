import { RouterModule, Routes } from "@angular/router";

import {HomeComponent} from "./home/home.component";
import {CalendarComponent} from "./calendar/calendar.component";

const app_routes: Routes = [
    {path: 'home', component: HomeComponent},
    {path: 'calendar', component: CalendarComponent},
    {path: '**', pathMatch:'full',redirectTo:'home'}
];

export const app_routing = RouterModule.forRoot(app_routes);


