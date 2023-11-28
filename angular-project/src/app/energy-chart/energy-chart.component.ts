import { Component, OnInit } from '@angular/core';
import { EnergyService } from '../service/energy/energy.service';
import { EnergyDTO } from '../dtos/energy-dto';
import { PopupService } from '../service/popup/popup.service';
import { ChartDataset, ChartOptions } from 'chart.js';

@Component({
  selector: 'app-energy-chart',
  templateUrl: './energy-chart.component.html',
  styleUrls: ['./energy-chart.component.css'],
})
export class EnergyChartComponent implements OnInit {
  usersDevices: EnergyDTO[] = [];
  public lineChartData: any[] = [];
  public lineChartLabels: String[] = [];
  public lineChartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
  };
  public lineChartColors: any[] = [
    {
      backgroundColor: 'rgba(105, 0, 132, .2)',
      borderColor: 'rgba(200, 99, 132, .7)',
      borderWidth: 2,
    },
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';

  constructor(private energyService: EnergyService, private popupService: PopupService) {}

  ngOnInit(): void {
    this.loadChartData();
  }

  private loadChartData(): void {
    const deviceId = this.popupService.deviceDTO.id;
    this.energyService.getConsumptionByDeviceId(deviceId).subscribe((data) => {
      this.usersDevices = data;
      console.log(this.usersDevices[0].measurementValue);
      this.lineChartData = [
        {
          data: this.usersDevices.map((item) => item.measurementValue),
          label: 'Energy Consumption',
        },
      ];
    

      this.lineChartLabels = this.usersDevices.map((item) => item.timestamp);
    });
  }
}
