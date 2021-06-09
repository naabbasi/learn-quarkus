import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {CanActivateTeam} from "./auth/auth.guard";
import {LayoutComponent} from "./components/home/layout/layout.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'layout', loadChildren: () => import('./components/home/home.module').then(m => m.HomeModule), canActivate: [CanActivateTeam],
  },
  /*{
    path: 'users', loadChildren: () => import('./components/users/users.module').then(m => m.UsersModule), canActivate: [CanActivateTeam]
  },*/
  { path: '', component: LoginComponent},
  { path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
