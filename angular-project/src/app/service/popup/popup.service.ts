import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeviceDTO } from 'src/app/dtos/device-dto';
import { EnergyChartComponent } from 'src/app/energy-chart/energy-chart.component';
import { EnergyService } from '../energy/energy.service';


@Injectable({
  providedIn: 'root',
})
export class PopupService {
  constructor(private dialog: MatDialog) {}
  deviceDTO: DeviceDTO;
  openPopup() {
    this.dialog.open(EnergyChartComponent);
  }

  getDevice(device:DeviceDTO){
    this.deviceDTO = device;
   
  }
}