import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClientXsrfModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { XhrInterceptor } from './interceptor/app.request.interceptor';
import { AuthActivateRouteGuard } from './routeguards/auth.routeguard';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { LogoutComponent } from './components/logout/logout.component';
import { JwtModule } from '@auth0/angular-jwt';
import { DogComponent } from './components/dog/dog.component';
import { HamsterComponent } from './components/hamster/hamster.component';
import { CatComponent } from './components/cat/cat.component';
import { RegisterComponent } from './components/register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatStepperModule} from '@angular/material/stepper';
import { MatInputModule } from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatDialogModule } from '@angular/material/dialog';
import { NeedsDialogComponent } from './components/needs-dialog/needs-dialog.component';
import { DogUpdateDialogComponent } from './components/dog-update-dialog/dog-update-dialog.component';
import { CatUpdateDialogComponent } from './components/cat-update-dialog/cat-update-dialog.component';
import { HamsterUpdateDialogComponent } from './components/hamster-update-dialog/hamster-update-dialog.component';
import { AdminComponent } from './components/admin/admin.component';
import { UserUpdateDialogComponent } from './user-update-dialog/user-update-dialog.component';
import {MatBadgeModule} from '@angular/material/badge';
import { MatSnackBar, MatSnackBarModule  } from '@angular/material/snack-bar';
export function tokenGetter()
{
  return sessionStorage.getItem("Authorization");
}

@NgModule({
  declarations: [
    NeedsDialogComponent,
    LoginComponent,
    AppComponent,
    HeaderComponent,
    HomeComponent,
    LogoutComponent,
    DogComponent,
    HamsterComponent,
    CatComponent,
    RegisterComponent,
    NeedsDialogComponent,
    DogUpdateDialogComponent,
    CatUpdateDialogComponent,
    HamsterUpdateDialogComponent,
    AdminComponent,
    UserUpdateDialogComponent,
  ],
  imports: [
    MatSnackBarModule,
    MatBadgeModule,
    FlexLayoutModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatStepperModule,
    MatFormFieldModule,
    MatTabsModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    MatDialogModule,
    ReactiveFormsModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN',
    }),
    JwtModule.forRoot({
      config:{
        tokenGetter: tokenGetter,
        allowedDomains: ["http://localhost:8080","http://localhost:4200"]
      }
    }),
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide : HTTP_INTERCEPTORS,
      useClass : XhrInterceptor,
      multi : true
    },AuthActivateRouteGuard
  ],
  bootstrap: [AppComponent],
})
export class AppModule {

}
