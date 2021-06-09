import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UsersComponent } from './users.component';
import {MaterialModule} from "../../MaterialModule";


@NgModule({
  declarations: [UsersComponent],
  imports: [
      CommonModule,
      UsersRoutingModule,
      MaterialModule
  ],
  providers: [],
  exports: []
})
export class UsersModule { }
