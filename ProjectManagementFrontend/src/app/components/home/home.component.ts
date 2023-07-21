import { Component, OnInit } from '@angular/core';
import { Chart,registerables, ChartConfiguration, ChartData, ChartDataset, ChartOptions, ChartType , Color,} from 'chart.js';
import { NgChartsModule } from 'ng2-charts';

import { ITask, ITypePercentage } from '../../interface/task.interface';
import { Observable } from 'rxjs';
import { TaskService } from 'src/app/services/task/task.service';
Chart.register(...registerables);



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  public doughnutChartOptions: ChartOptions = {
    responsive: false
  };

  public color: any[]=[];
  public chartType: string = 'doughnut';
  public doughnutChartLabels: any[] = [];
  public doughnutChartData: any[]= [];
  public typeData:any;
  tasks: Observable<Array<ITask>> | undefined;
  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.getTasks();

  


    this.taskService.getTypePercentage().subscribe(
      (d) => {
        this.typeData = d;
        if (this.typeData!= null){
          for(let i=0; i<this.typeData.length ;i++){
            // console.log(this.typeData[i]);
            this.doughnutChartLabels.push(this.typeData[i].type);
            this.doughnutChartData.push(this.typeData[i].count);


          }
          this.RenderChart(this.doughnutChartLabels,this.doughnutChartData);
        }

        // d.forEach((type: ITypePercentage) => {
        //   this.doughnutChartData.push([type.count]);
        //   this.doughnutChartLabels.push(type.type);
        // });
      },

    );
    }

    getTasks() {
      this.tasks = this.taskService.getTaskList();
    }

  RenderChart(doughnutChartLabels:any,doughnutChartData:any){
    const colors = this.mapColors(doughnutChartLabels);

      const myChart = new Chart('doughnut', {
        type: 'doughnut',
        data: {
          labels: doughnutChartLabels,
          datasets: [{
            label: '# of Votes',
            data: doughnutChartData,
            backgroundColor: colors,


          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });

  }
  mapColors(labels: any[]): string[] {
    return labels.map(type => {
      if (type === 'todo') {
        return '#ffbf3a'; 
      } else if (type === 'pending') {
        return '#4e3dc8'; 
      } else if (type === 'done') {
        return '#f68059'; 
      } else {
        return 'gray'; 
      }
    });
  }


}