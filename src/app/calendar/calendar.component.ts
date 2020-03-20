import { Component, OnInit } from '@angular/core';
declare var $: any;
@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  constructor() { }

  ngOnInit(){
    setTimeout(() => {
     $("#calendar").fullCalendar({  
                     header: {
                         left   : 'prev,next, today',
                         center : 'tile',
                         right  : 'month,agendaWeek,agendaDay'
                     },
                     navLinks   : true,
                     editable   : true,
                     eventLimit : true,
                     events: [
                         {
                             title : 'Prueba 1',
                             start : '2020-03-04T13:00:00',
                             color : '#f9c66a' // override!
                         },
                         {
                             title : 'Cuarentena',
                             start : '2020-03-13T12:30:00',
                             end   : '2020-03-29',
                             color : "#019efb"
                         }
                     ],  // request to load current events
                    
                 });
  }, 100);
}

}
