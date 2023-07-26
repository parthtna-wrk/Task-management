import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AddComponent } from './components/task/add/add.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { AuthGuard } from './guard/auth.guard';
import { UserComponent } from './components/user/user.component';
import { TermsComponent } from './components/terms/terms.component';
import { CalendarComponent } from './components/calendar/calendar.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  
  {
    path: 'home',
    component: HomeComponent,canActivate:[AuthGuard]
  },
  {
    path: 'task',
    children: [
      {
        path: 'add',
        component: AddComponent,canActivate:[AuthGuard]
      },
    ],
  },
  { path: 'calendar', component: CalendarComponent,canActivate:[AuthGuard] },
  { path: 'terms', component: TermsComponent, },
  { path: 'profile', component: UserComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}