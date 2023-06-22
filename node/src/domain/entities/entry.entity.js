class Entry{
    constructor(id, autor, titulo, contenido, descripcion, fecha, image_id,  user_entry_id){
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.contenido = contenido;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.image_id = image_id;
        this.user_entry_id = user_entry_id;
    } 
}
module.exports = {
    Entry
}