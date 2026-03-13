import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormField, MatInputModule } from '@angular/material/input';
import { MatToolbar } from '@angular/material/toolbar';
import { ContatoService } from '../contato.service';
import { Contato } from './contato';
import { FormBuilder, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatTab, MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-contato',
  imports: [
    MatToolbar,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatFormField,
    ɵInternalFormsSharedModule,
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
    MatTabsModule,
    MatTableModule,
    MatIconModule,
    MatCardModule
  ],
  templateUrl: './contato.component.html',
  styleUrl: './contato.component.css'
})
export class ContatoComponent implements OnInit {

  formGroup!: FormGroup;

  contatos: Contato[] = [];
  colunas: string[] = ['id', 'nome', 'email', 'favorito']

  constructor(
    private contatoService: ContatoService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
    this.getCliente(-1);
  }

  submit() {
    const formValues = this.formGroup.value
    const contatoSalvo: Contato = new Contato(formValues['nome'], formValues['email'])

    this.salvarContato(contatoSalvo);
  }

  salvarContato(contato: Contato) {
    console.log(contato)
    this.contatoService.salvarContato(contato)
      .subscribe({
        next: (res) => {
          this.contatos.push(res);
          console.log(this.contatos)
        },
        error: (errorResponse) =>
          console.error(errorResponse)
      })
  }

  getCliente(id: number) {
    this.contatoService.getCliente(id).subscribe({
      next: (res) => {
        this.contatos = res
      }
    })
  }

  
}
