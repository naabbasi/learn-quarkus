import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LayoutComponent} from "./layout/layout.component";
import {CanActivateTeam} from "../../auth/auth.guard";


const routes: Routes = [
  {
    path: '', component: LayoutComponent, canActivate: [CanActivateTeam],
    children: [
      {
        path: 'users', loadChildren: () => import('../users/users.module').then(m => m.UsersModule), canActivate: [CanActivateTeam]
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
