import {Component} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import {DeviceDTO} from 'src/app/dtos/device-dto';
import {UserDetailsDTO} from 'src/app/dtos/user-details-dto';
import {DeviceService} from 'src/app/service/device/device.service';
import { LoginService } from 'src/app/service/login/login.service';
import { PopupService } from 'src/app/service/popup/popup.service';
import {UserService} from 'src/app/service/user/user.service';

@Component({selector: 'app-device-list', templateUrl: './device-list.component.html', styleUrls: ['./device-list.component.css']})
export class DeviceListComponent {
    deviceList : DeviceDTO[] = [];
    deviceWithUsername : DeviceDTO[];
    deviceDTO : DeviceDTO;
    usersDevices : DeviceDTO[] = [];

   
    userList : UserDetailsDTO[] = [];
    
    selectedUserIds : {
    [key: string]: string
    } = {};

    deviceToUserMap = new Map();
    isEditing = false;
    isDeviceDetailsFormOpen = false;
    isUser=false;

    constructor(private popupService: PopupService,private deviceService : DeviceService, private userService : UserService, private login:LoginService, private toastr:ToastrService ) {}


    openDeviceDetailsForm() {

        this.isDeviceDetailsFormOpen = true;
    }

    closeDeviceDetailsForm() {
        this.isDeviceDetailsFormOpen = false;
    }

    ngOnInit(): void {

        this.getDevices();
        this.loadUserList();
        this.matchUsernames();

        if(this.checkUserRole()){
            this.getUserDevices();
        }
       
       

    }

    private getDevices() {

        this.deviceService.getDeviceList().subscribe(data => {
            this.deviceList = data;
            this.matchUsernames();

        });
    }

    loadUserList() {
        this.userService.getUserList().subscribe((data) => {
            this.userList = data;
            this.matchUsernames();
        });
    }

    editDevice(device : DeviceDTO) {

        this.deviceDTO = device;
        this.isEditing = true;
    }

    saveDevice(device : DeviceDTO) {

        console.log(device);
        this.isEditing = false;

        const userId = this.selectedUserIds[device.id];

        if (! userId) {
            this.showToaster('error',"No user selected for device!");
            console.error('Niciun utilizator selectat pentru acest dispozitiv.');
            return;
        }

        this.userService.getUserByUsername(userId).subscribe((userData : any) => {

            device.ownerId = userData;


            this.deviceService.updateDevice(device.id, device).subscribe(() => {
                this.showToaster('success',"Device updated succesfully!");
                console.log("Dispozitivul a fost actualizat cu succes");
                setTimeout(() => {
            
                    location.reload();
                  }, 2000);
            }, (error) => {
                this.showToaster('error',"Error updating the device!");
                console.error('Eroare la actualizarea dispozitivului:', error);
            });
        });
    }


    resetDevice(device : DeviceDTO) {
        this.isEditing = false;
    }

    deleteDevice(device : DeviceDTO) {
        this.isEditing = false;
        this.deviceService.deleteDevice(device.id).subscribe(() => {
            this.showToaster('success',"Device deleted succesfully!");
            console.log("yeyeDEL");
            setTimeout(() => {
            
                location.reload();
              }, 2000);
        }, (error) => {
            this.showToaster('error',"Error updating the device!");
            console.error('Error deleting user:', error);
        });
    }

    matchUsernames() {
        if (this.deviceList.length === 0 || this.userList.length === 0) {
            return;
        }

        this.deviceList.forEach((device) => {
            const matchingUser = this.userList.find((user) => user.id === device.ownerId);
            if (matchingUser) {
                this.deviceToUserMap.set(device, matchingUser);
            }
        });
    }

    getUserDevices(){
      let userID=this.login.getUser().id;
      this.deviceService.getUserDevices(userID).subscribe(data => {
        this.usersDevices = data;
        this.deviceService.setDevices(this.usersDevices);
       
    });
    }

 

    checkUserRole(): boolean {
        let userRole = this.login.getUser().userRole;
    
        if (userRole === "USER") {
            this.isUser = true;
            return true;
        } else {
            return false;
        }
    }
  
    selectedDevice: DeviceDTO | null = null;
    chartView= false;
    viewChart(device:DeviceDTO){
        this.popupService.openPopup();
        this.popupService.getDevice(device);
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
