import { LoginService } from '../service/login/login.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketService } from '../service/websocket/web-socket.service';
import { DeviceService } from '../service/device/device.service';
import { DeviceDTO } from '../dtos/device-dto';
import { ToastrService } from 'ngx-toastr';
import { Subscription, count } from 'rxjs';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
  template: `
    <div *ngFor="let notification of notifications">
      {{ notification }}
    </div>
  `,
})
export class UserPageComponent implements OnInit,OnDestroy {
  devices: DeviceDTO[] = [];
  message: string = '';
  greeting: string = '';
  userId: string= '';
  private subscription: Subscription;
  


  constructor(private loginService:LoginService,private webSocketService: WebSocketService,private deviceService:DeviceService,private toastr:ToastrService ) {
    
  }

  connectUser(userId: string): void {
    this.webSocketService.connectToWebSocket(userId);
  }

  disconnectUser(userId: string): void {
    
    this.webSocketService.disconnectWebSocket(userId);
  }

  
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.disconnectUser(this.userId);
  }



  ngOnInit(): void {
    const auth = localStorage.getItem('token');
    const user = this.loginService.interpretToken(auth);
    const userId = user.id;
    this.userId = userId;

    console.log(userId);
    if (userId) {
      this.connectUser(userId);
      this.subscription = this.webSocketService.exeeds(userId).subscribe((message) => {
        console.log(message);
        if (message.includes('exceeds')) {
          this.showToaster('error', message);
        } else {
          //this.showToaster('success', message);
        }
      });

      setTimeout(() => {
        this.devices = this.deviceService.getDevices();
        this.webSocketService.sendDevices(this.devices);
        console.log(this.devices);
      }, 2000);
    }
  }

  

  sendMessage(devices:DeviceDTO[]): void {
    //this.websocketService.sendDevices(devices);
    
  }



checkNotify(){

}


  logout(){
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
     this.disconnectUser(this.userId);
    this.loginService.logout();
  }


  showToaster(messageType: string,message:string) {
    switch (messageType) {
      case 'success':
        this.toastr.success(message);
        break;
      case 'info':
        this.toastr.info(message);
        break;
      case 'warning':
        this.toastr.warning(message);
        break;
      case 'error':
        this.toastr.error(message);
        break;
      default:
        this.toastr.info(message);
        break;
    }
  }

}
