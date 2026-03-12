import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormField, MatInputModule } from '@angular/material/input';
import { MatToolbar } from '@angular/material/toolbar';
import { ContatoService } from '../contato.service';
import { Contato } from './contato';
import { FormBuilder, FormGroup, Validators, ɵInternalFormsSharedModule } from '@angular/forms';

@Component({
  selector: 'app-contato',
  imports: [
    MatToolbar,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatFormField,
    ɵInternalFormsSharedModule
],
  templateUrl: './contato.component.html',
  styleUrl: './contato.component.css'
})
export class ContatoComponent implements OnInit {

  formGroup!: FormGroup;

  contato: Contato = new Contato();
  constructor(
    private contatoService: ContatoService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      nome:['', Validators.required],
      email:['', Validators.email]
    });

  }

  submit() {
    console.log(this.formGroup.value)
    this.salvarContato(this.contato);
  }

  salvarContato(contato: Contato) {
    this.contatoService.salvarContato(contato)
      .subscribe({
        next: (res) => {
          this.contato = res;
        },
        error: (errorResponse) =>
          console.error(errorResponse)
      })
  }
}
