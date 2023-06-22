class EntryResponse {
    constructor(id, autor, titulo, contenido, descripcion, fecha) {
            this.id = id;
            this.autor = autor;
            this.titulo = titulo;
            this.contenido = contenido;
            this.descripcion = descripcion;
            this.fecha = fecha;
    }
}
module.exports = {
    EntryResponse
}