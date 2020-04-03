export class Results {

  private _IDPaciente: string;
  private _fechaHora: string;
  private _valorTemperatura: string;
  private _comentario: string;

  get IDPaciente(): string {
    return this._IDPaciente;
  }

  set IDPaciente(value: string) {
    this._IDPaciente = value;
  }

  get fechaHora(): string {
    return this._fechaHora;
  }

  set fechaHora(value: string) {
    this._fechaHora = value;
  }

  get valorTemperatura(): string {
    return this._valorTemperatura;
  }

  set valorTemperatura(value: string) {
    this._valorTemperatura = value;
  }

  get comentario(): string {
    return this._comentario;
  }

  set comentario(value: string) {
    this._comentario = value;
  }


}
