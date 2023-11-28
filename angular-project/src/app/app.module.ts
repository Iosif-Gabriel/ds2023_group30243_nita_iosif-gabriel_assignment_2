import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DeviceListComponent } from './lists/device-list/device-list.component';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { UserListComponent } from './lists/user-list/user-list.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserDetailsFormComponent } from './user-details-form/user-details-form.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { AdminGuard } from './guard/admin.guard';
import { DeviceDetailsFormComponent } from './device-details-form/device-details-form.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserPageComponent } from './user-page/user-page.component';
import { Toastr, TOASTR_TOKEN } from './service/toastr/toastr.service';
import { EnergyChartComponent } from './energy-chart/energy-chart.component';
import { MatDialogModule } from '@angular/material/dialog';
import { NgChartsModule  } from 'ng2-charts';
import { RxStompService, StompService } from '@stomp/ng2-stompjs';
import { WebSocketService } from './service/websocket/web-socket.service';
import { StompConfig } from '@stomp/stompjs';


declare const toastr: Toastr;


@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    DeviceListComponent,
    NavbarComponent,
    LoginComponent,
    UserDetailsFormComponent,
    AdminPageComponent,
    DeviceDetailsFormComponent,
    UserPageComponent,
    EnergyChartComponent,
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    FormsModule,
    BrowserAnimationsModule, 
    ToastrModule.forRoot(),
    MatDialogModule,
    NgChartsModule,
     
  ],
  providers: [AdminGuard, RxStompService,

    {
      provide: TOASTR_TOKEN,
      useValue: toastr
    }
  ],
  bootstrap: [AppComponent],

})
export class AppModule { 
  
}
