import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StompConfig, StompService } from '@stomp/ng2-stompjs';
import { DeviceDTO } from 'src/app/dtos/device-dto';
import { LoginService } from '../login/login.service';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompConfig: StompConfig;
  private stompService: StompService;
  private userId: string;

  constructor(private loginService:LoginService) {
    this.stompConfig = {
      url: 'ws://localhost:8082/energy',
      headers: {},
      heartbeat_in: 0,
      heartbeat_out: 20000,
      reconnect_delay: 5000,
      debug: true,
    };

    this.stompService = new StompService(this.stompConfig);
  }

  exeeds(userId: string): Observable<any> {
    return new Observable<any>((observer) => {
      const subscription = this.stompService.subscribe(`/topic/check/${userId}`).subscribe(
        (message) => {
          observer.next(message.body);
        },
        (error) => {
          observer.error(error);
        },
        () => {
          observer.complete();
        }
      );
  
      return () => {
        subscription.unsubscribe();
      };
    });
  }
  


  connectToWebSocket(userId: string): void {
   
    const destination = `/app/connect/${userId}`;
    this.stompService.publish({ destination ,body: userId });
  }

  disconnectWebSocket(userId: string): void {
   
    const destination = `/app/disconnect/${userId}`;
    this.stompService.publish({ destination ,body: userId});
    this.closeConnection();
  }

  
  closeConnection(): void {
    if (this.stompService && this.stompService.connected()) {
      this.stompService.disconnect();
      this.stompService = new StompService(this.stompConfig); 
    }
  }

  
  
  sendDevices(devices: DeviceDTO[]): void {
      if (this.stompService && this.stompService.connected()) {
       
          const payload = JSON.stringify(devices)
          const destination = `/app/sendDevices/${devices[0].ownerId}`;
          this.stompService.publish({ destination, body: payload });
       
      } else {
        console.error('WebSocket is not connected. Reconnecting...');
     
      }
    }

  receiveMessages(userId:String): Observable<any> {
  
    return new Observable<any>((observer) => {
      this.stompService.subscribe(`/topic/check2/${userId}`).subscribe((message) => {
        
        observer.next(message.body);
      });
    });
  }

  

      
  getStompService(): StompService | undefined {
    return this.stompService;
  }
}
