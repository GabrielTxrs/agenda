import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button'
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { ContatoComponent } from "./contato/contato.component";
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [
    MatButtonModule,
    MatSlideToggleModule,
    ContatoComponent,
    ReactiveFormsModule,
],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'agenda-app';
}
