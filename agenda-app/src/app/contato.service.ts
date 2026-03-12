import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Contato } from './contato/contato';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContatoService {

  private apiUrl: string = 'http://localhost:8080/contatos';
  constructor(
    private httpClient: HttpClient
  ) { }

  public salvarContato(contato: Contato): Observable<Contato> {
    return this.httpClient.post<Contato>(`${this.apiUrl}`, contato)
  }
}
