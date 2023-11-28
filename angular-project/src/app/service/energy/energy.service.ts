import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EnergyDTO } from 'src/app/dtos/energy-dto';

@Injectable({
  providedIn: 'root'
})
export class EnergyService {

  constructor(private httpClient: HttpClient) { }


  
  getConsumptionByDeviceId(deviceId:String):Observable<EnergyDTO[]>{
    const getDeviceConsumptionById=`http://localhost:8082/energy/getConsumptionByDeviceId/${deviceId}`;

    return this.httpClient.get<EnergyDTO[]>(getDeviceConsumptionById);
  }

}
