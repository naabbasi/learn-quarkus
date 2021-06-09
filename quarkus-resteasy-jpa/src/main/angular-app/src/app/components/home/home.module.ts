import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { LayoutComponent } from './layout/layout.component';
import {RouterModule} from "@angular/router";
import {CanActivateTeam, Permissions, UserToken} from "../../auth/auth.guard";
import {MaterialModule} from "../../MaterialModule";


@NgModule({
  declarations: [LayoutComponent],
  imports: [
    CommonModule,
    RouterModule,
    HomeRoutingModule,
    MaterialModule
  ],
  providers: [CanActivateTeam, UserToken, Permissions],
  exports: [RouterModule]
})
export class HomeModule { }
