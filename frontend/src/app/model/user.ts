export class User {

  private _id: string;
  private _name: string;
  private _fechaNacimiento: string;
  private _contrasena: string;
  private _correo: string;
  private _medicacion: string;
  private _dolenciaEstudio: string;
  private _rol: string;

  get id(): string {
    return this._id;
  }

  setId(value: string) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  setName(value: string) {
    this._name = value;
  }

  get fechaNacimiento(): string {
    return this._fechaNacimiento;
  }

  set fechaNacimiento(value: string) {
    this._fechaNacimiento = value;
  }

  get contrasena() {
    return this._contrasena;
  }

  set contrasena(value) {
    this._contrasena = value;
  }

  get correo() {
    return this._correo;
  }

  set correo(value) {
    this._correo = value;
  }

  get medicacion() {
    return this._medicacion;
  }

  set medicacion(value) {
    this._medicacion = value;
  }

  get dolenciaEstudio() {
    return this._dolenciaEstudio;
  }

  set dolenciaEstudio(value) {
    this._dolenciaEstudio = value;
  }

  get rol() {
    return this._rol;
  }

  set rol(value) {
    this._rol = value;
  }



}
