class EntryResponse {
    constructor(id, autor, contenido, descripcion, fecha, titulo) {
            this.id = id;
            this.autor = autor;
            this.contenido = contenido;
            this.descripcion = descripcion;
            this.fecha = fecha;
            this.titulo = titulo;
    }
}
module.exports = {
    EntryResponse
}