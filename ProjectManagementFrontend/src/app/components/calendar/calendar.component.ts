import { Component, OnInit } from '@angular/core';
import { CalendarOptions } from 'fullcalendar';
import dayGridPlugin from '@fullcalendar/daygrid';
import { Observable } from 'rxjs';
import { ITask, ITaskTypeOption } from 'src/app/interface/task.interface';
import { TaskService } from 'src/app/services/task/task.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  typeOptions: Array<ITaskTypeOption> = [];

  tasks: Observable<Array<ITask>> | undefined;
  typeData: any;
  constructor(private taskService: TaskService, public dialog: MatDialog) {}

  

  ngOnInit(): void {
    this.getTasks();
    this.typeOptions=this.taskService.getTypeOptions();

    // this.taskService.getTypePercentage().subscribe(
    //   (d) => {
    //     this.typeData = d;
    //     if (this.typeData!= null){
    //       for(let i=0; i<this.typeData.length ;i++){
    //       }  
    //     }
    //   },
    //   );
  }
  

  getTasks() {
    this.tasks = this.taskService.getTaskList();
    this.tasks.subscribe(tasks => {
      this.calendarOptions.events = this.mapTasksToEvents(tasks);
    });
  }

  // mapTasksToEvents(tasks: ITask[]): any[] {
  //   return tasks.map(task => ({
  //     title: task.title,
  //     date:task.dueDate
  //   }));
  // }

  mapTasksToEvents(tasks: ITask[]): any[] {
    return tasks.map(task => {
      const dueDate = new Date(task.dueDate);
      const year = dueDate.getFullYear();
      const month = String(dueDate.getMonth() + 1).padStart(2, '0');
      const day = String(dueDate.getDate()).padStart(2, '0');
      const formattedDate = `${year}-${month}-${day}`;
      const eventBackgroundColor = this.getEventBackgroundColor(task.type);
  
      return {
        title: task.title,
        date: formattedDate,
        backgroundColor: eventBackgroundColor,
      };
    });
  }
  
  getEventBackgroundColor(type: string): string {
    if (type === 'todo') {
      return '#ffbf3a'; 
    } else if (type === 'pending') {
      return '#4e3dc8'; 
    } else if (type === 'done') {
      return '#f68059'; 
    } else {
      return '#888'; 
    }
  }
  
  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    plugins: [dayGridPlugin],
    dateClick: this.handleDateClick.bind(this), 
    events: [], 
    eventBackgroundColor:'#b12a28'
  };

  handleDateClick(arg:any) {
    alert('date click! ' + arg.dateStr);
  }
}


