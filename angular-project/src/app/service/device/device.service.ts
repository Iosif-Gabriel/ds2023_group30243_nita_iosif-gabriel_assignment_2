import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DeviceDTO } from 'src/app/dtos/device-dto';
import { Observable } from 'rxjs';
import { UserDetailsDTO } from 'src/app/dtos/user-details-dto';


@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private getDevicesURL="http://localhost:8081/device/getAllDevices";
  private createDeviceURL="http://localhost:8081/device/createDevice"
  private devices: DeviceDTO[] = [];
  constructor(private httpClient: HttpClient) { }
  getDeviceList(): Observable<DeviceDTO[]> {
    
    return this.httpClient.get<DeviceDTO[]>(this.getDevicesURL);
  }

  createDevice(deviceDTO:DeviceDTO):Observable<any>{
    console.log(deviceDTO);
   
    return this.httpClient.post(this.createDeviceURL, deviceDTO);;
  }
  

  deleteDevice(deviceId:String):Observable<any>{
    const deleteDeviceURL=`http://localhost:8081/device/delete/${deviceId}`;

    return this.httpClient.delete(deleteDeviceURL);
  }

  updateDevice(deviceId:String,deviceDTO:DeviceDTO):Observable<any>{
   
    const updateDeviceURL=`http://localhost:8081/device/update/${deviceId}`;

    return this.httpClient.put(updateDeviceURL,deviceDTO);
  }

  getUserDevices(userID:String):Observable<DeviceDTO[]>{
    const usersDeviceURL=`http://localhost:8081/device/getUserDevices/${userID}`;

    return this.httpClient.get<DeviceDTO[]>(usersDeviceURL);
  }

  getDevices(): DeviceDTO[] {
    return this.devices;
  }

  setDevices(devices: DeviceDTO[]): void {
    this.devices = devices;
  }
}
